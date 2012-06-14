/*
 * Copyright 2002-2012 SCOOP Software GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.scoopgmbh.copper.persistent;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.Collection;

import de.scoopgmbh.copper.batcher.AbstractBatchCommand;
import de.scoopgmbh.copper.batcher.BatchCommand;
import de.scoopgmbh.copper.batcher.BatchExecutor;
import de.scoopgmbh.copper.batcher.NullCallback;

class MySqlSetToError {

	static final class Command extends AbstractBatchCommand<Executor, Command> {

		private final PersistentWorkflow<?> wf;
		private final Throwable error;

		@SuppressWarnings("unchecked")
		public Command(PersistentWorkflow<?> wf, Throwable error) {
			super(NullCallback.instance,250);
			this.wf = wf;
			this.error = error;
		}

		@Override
		public Executor executor() {
			return Executor.INSTANCE;
		}

	}

	static final class Executor extends BatchExecutor<Executor, Command> {

		private static final Executor INSTANCE = new Executor();

		@Override
		public void doExec(final Collection<BatchCommand<Executor, Command>> commands, final Connection con) throws Exception {
			final PreparedStatement stmtDelQueue = con.prepareStatement("DELETE FROM COP_QUEUE WHERE WORKFLOW_INSTANCE_ID=? AND PPOOL_ID=? AND PRIORITY=?");
			final PreparedStatement stmtUpdateState = con.prepareStatement("UPDATE COP_WORKFLOW_INSTANCE SET STATE=?, LAST_MOD_TS=? WHERE ID=?");
			final PreparedStatement stmtInsertError = con.prepareStatement("INSERT INTO COP_WORKFLOW_INSTANCE_ERROR (WORKFLOW_INSTANCE_ID, EXCEPTION, ERROR_TS) VALUES (?,?,?)");
			for (BatchCommand<Executor, Command> _cmd : commands) {
				final Timestamp NOW = new Timestamp(System.currentTimeMillis());
				Command cmd = (Command)_cmd;
				stmtUpdateState.setInt(1, DBProcessingState.ERROR.ordinal());
				stmtUpdateState.setTimestamp(2, NOW);
				stmtUpdateState.setString(3, cmd.wf.getId());
				stmtUpdateState.addBatch();

				stmtInsertError.setString(1, cmd.wf.getId());
				stmtInsertError.setString(2, convert2String(cmd.error));
				stmtInsertError.setTimestamp(3, NOW);
				stmtInsertError.addBatch();

				stmtDelQueue.setString(1, cmd.wf.getId());
				stmtDelQueue.setString(2, cmd.wf.oldProcessorPoolId);
				stmtDelQueue.setInt(3, cmd.wf.oldPrio);
				stmtDelQueue.addBatch();
			}
			stmtUpdateState.executeBatch();
			stmtInsertError.executeBatch();
			stmtDelQueue.executeBatch();
		}

		@Override
		public int maximumBatchSize() {
			return 100;
		}

		@Override
		public int preferredBatchSize() {
			return 50;
		}

	}

	private static final String convert2String(Throwable t)  {
		StringWriter sw = new StringWriter(2048);
		t.printStackTrace(new PrintWriter(sw));
		return sw.toString();
	}
}
