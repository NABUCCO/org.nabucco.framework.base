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
package org.nabucco.framework.base.ui.web.action.handler.perspective;

import org.nabucco.framework.base.facade.exception.client.action.ActionException;
import org.nabucco.framework.base.ui.web.action.WebActionHandler;
import org.nabucco.framework.base.ui.web.action.parameter.WebActionParameter;
import org.nabucco.framework.base.ui.web.action.result.RefreshItem;
import org.nabucco.framework.base.ui.web.action.result.WebActionResult;
import org.nabucco.framework.base.ui.web.component.WebElementType;
import org.nabucco.framework.base.ui.web.component.perspective.Perspective;
import org.nabucco.framework.base.ui.web.component.perspective.PerspectiveArea;
import org.nabucco.framework.base.ui.web.servlet.util.NabuccoServletUtil;
import org.nabucco.framework.base.ui.web.servlet.util.path.NabuccoServletPathType;

/**
 * OpenPerspectiveHandler
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class OpenPerspectiveHandler extends WebActionHandler {

    @Override
    public WebActionResult execute(WebActionParameter parameter) throws ActionException {

        String perspectiveId = parameter.get(NabuccoServletPathType.PERSPECTIVE_AREA);
        PerspectiveArea perspectiveArea = NabuccoServletUtil.getPerspectiveArea();

        if (perspectiveArea == null) {
            throw new ActionException("Cannot locate Perspective Area.");
        }

        Perspective perspective = perspectiveArea.getPerspective(perspectiveId);
        perspectiveArea.setCurrentPerspective(perspective);

        WebActionResult result = new WebActionResult();
        result.addItem(new RefreshItem(WebElementType.PERSPECTIVE_AREA));

        return result;
    }

}
