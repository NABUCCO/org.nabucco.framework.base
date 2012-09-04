/*
 * Copyright 2012 PRODYNA AG
 *
 * Licensed under the Eclipse Public License (EPL), Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/eclipse-1.0.php or
 * http://www.nabucco-source.org/nabucco-license.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.nabucco.framework.base.ui.web.action.handler.common;

import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.base.ui.web.action.WebActionHandler;
import org.nabucco.framework.base.ui.web.action.parameter.WebActionParameter;
import org.nabucco.framework.base.ui.web.action.result.WebActionResult;

/**
 * DoNothingAction
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class DoNothingAction extends WebActionHandler {

    NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(DoNothingAction.class);

    @Override
    public WebActionResult execute(WebActionParameter parameter) throws ClientException {

        logger.info("Do-Nothing Action called. That is only in development allowed as far no special action is configured.");

        return new WebActionResult();
    }

}
