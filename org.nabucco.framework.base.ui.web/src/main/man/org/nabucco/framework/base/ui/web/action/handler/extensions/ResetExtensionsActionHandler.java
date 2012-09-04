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
package org.nabucco.framework.base.ui.web.action.handler.extensions;

import org.nabucco.common.extension.ExtensionException;
import org.nabucco.framework.base.facade.datatype.NabuccoSystem;
import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.base.ui.web.action.WebActionHandlerSupport;
import org.nabucco.framework.base.ui.web.action.parameter.WebActionParameter;
import org.nabucco.framework.base.ui.web.action.result.WebActionResult;

/**
 * ResetExtensionsActionHandler
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class ResetExtensionsActionHandler extends WebActionHandlerSupport {

    @Override
    public WebActionResult execute(WebActionParameter parameter) throws ClientException {

        try {
            NabuccoSystem.getExtensionResolver().reset("NABUCCO");
        } catch (ExtensionException e) {
            throw new ClientException("Cannot reset extensions", e);
        }

        return new WebActionResult();
    }

}
