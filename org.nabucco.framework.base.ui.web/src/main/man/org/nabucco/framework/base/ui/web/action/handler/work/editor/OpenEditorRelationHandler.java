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
package org.nabucco.framework.base.ui.web.action.handler.work.editor;

import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.base.facade.exception.client.action.ActionException;
import org.nabucco.framework.base.ui.web.action.WebActionHandlerSupport;
import org.nabucco.framework.base.ui.web.action.parameter.WebActionParameter;
import org.nabucco.framework.base.ui.web.action.result.RefreshItem;
import org.nabucco.framework.base.ui.web.action.result.WebActionResult;
import org.nabucco.framework.base.ui.web.component.WebElementType;
import org.nabucco.framework.base.ui.web.component.work.editor.RelationArea;
import org.nabucco.framework.base.ui.web.component.work.editor.RelationTab;
import org.nabucco.framework.base.ui.web.servlet.util.NabuccoServletUtil;
import org.nabucco.framework.base.ui.web.servlet.util.path.NabuccoServletPathType;

/**
 * Action handler responsible for opening editor relation tabs.
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class OpenEditorRelationHandler extends WebActionHandlerSupport {

    NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(OpenEditorRelationHandler.class);

    @Override
    public WebActionResult execute(WebActionParameter parameter) throws ActionException {

        String editorId = parameter.get(NabuccoServletPathType.EDITOR);
        RelationArea relationArea = NabuccoServletUtil.getRelationArea(editorId);

        if (relationArea != null) {

            String tabId = parameter.get(NabuccoServletPathType.TAB);
            RelationTab tab = relationArea.getTab(tabId);

            if (tab != null) {
                relationArea.focusTab(tabId);

                if (tab.getModel().isLazy()) {
                    try {
                        super.executeAction(tab.getLoadAction(), parameter);
                    } catch (ClientException e) {
                        logger.error("Cannot execute load action of the lazy loaded relation tab", e);
                    }
                }
            }
        }

        WebActionResult result = new WebActionResult();
        result.addItem(new RefreshItem(WebElementType.EDITOR));

        return result;
    }

}
