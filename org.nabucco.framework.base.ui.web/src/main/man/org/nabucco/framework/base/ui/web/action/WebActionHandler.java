/*
 * Copyright 2012 PRODYNA AG
 *
 * Licensed under the Eclipse Public License (EPL), Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/eclipse-1.0.php or
 * http://www.nabucco.org/License.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.nabucco.framework.base.ui.web.action;

import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.base.ui.web.action.parameter.WebActionParameter;
import org.nabucco.framework.base.ui.web.action.result.WebActionResult;

/**
 * WebActionHandler
 * <p/>
 * The interface for configured action implementations.
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public abstract class WebActionHandler {

    /**
     * Executes the web action with the given parameter.
     * 
     * @param parameter
     *            the action parameter
     * 
     * @return the action result
     * 
     * @throws ClientException
     *             when the action raises a functional error
     */
    public abstract WebActionResult execute(WebActionParameter parameter) throws ClientException;

}
