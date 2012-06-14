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
package de.scoopgmbh.copper.batcher;

import java.sql.Connection;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Abstract base implementation of the {@link BatchExecutorBase} interface.
 * 
 * @author austermann
 *
 * @param <E>
 * @param <T>
 */
public abstract class BatchExecutor<E extends BatchExecutor<E,T>, T extends BatchCommand<E,T>> implements BatchExecutorBase<E,T> {

	private static final Logger logger = LoggerFactory.getLogger(BatchExecutor.class);

	private final String id = this.getClass().getName();

	@Override
	public abstract void doExec(Collection<BatchCommand<E,T>> commands, Connection connection) throws Exception;

	public boolean prioritize() {
		return false;
	}

	@Override
	public String id() {
		return id;
	}

}
