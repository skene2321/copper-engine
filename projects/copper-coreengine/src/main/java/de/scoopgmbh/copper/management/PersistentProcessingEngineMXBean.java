/*
 * Copyright 2002-2011 SCOOP Software GmbH
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
package de.scoopgmbh.copper.management;


public interface PersistentProcessingEngineMXBean extends ProcessingEngineMXBean {
	
	/**
	 * Trigger restart a workflow instance that is in the error state.
	 * 
	 * @param workflowInstanceId
	 * @throws Exception
	 */
	public void restart(String idworkflowInstanceId) throws Exception;

	/**
	 * Trigger restart all workflow instances that are in error state.
	 * 
	 * @throws Exception
	 */
	public void restartAll() throws Exception;
}
