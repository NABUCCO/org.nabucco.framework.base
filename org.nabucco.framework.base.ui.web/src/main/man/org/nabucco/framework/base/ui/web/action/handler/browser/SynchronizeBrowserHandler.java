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
package org.nabucco.framework.base.ui.web.action.handler.browser;

import org.nabucco.framework.base.facade.exception.client.action.ActionException;
import org.nabucco.framework.base.ui.web.action.WebActionHandler;
import org.nabucco.framework.base.ui.web.action.parameter.WebActionParameter;
import org.nabucco.framework.base.ui.web.action.result.RefreshItem;
import org.nabucco.framework.base.ui.web.action.result.WebActionResult;
import org.nabucco.framework.base.ui.web.component.WebElementType;
import org.nabucco.framework.base.ui.web.servlet.util.NabuccoServletUtil;

/**
 * Action handler responsible for setting browser area in synchronized or no synchronized state
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class SynchronizeBrowserHandler extends WebActionHandler {

    private static final String JSON_VALUE = "value";

    @Override
    public WebActionResult execute(WebActionParameter parameter) throws ActionException {

        String value = parameter.getJsonRequest().getValue(JSON_VALUE);
        boolean synchronize = Boolean.parseBoolean(value);

        NabuccoServletUtil.getBrowserArea().setSynchronized(synchronize);

        WebActionResult result = new WebActionResult();
        result.addItem(new RefreshItem(WebElementType.BROWSER_AREA));

        return result;
    }
}
