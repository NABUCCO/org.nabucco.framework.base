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
package org.nabucco.framework.base.ui.web.action.result;

import org.nabucco.framework.base.ui.web.component.WebElementType;
import org.nabucco.framework.base.ui.web.component.work.WorkItem;
import org.nabucco.framework.base.ui.web.json.JsonMap;
import org.nabucco.framework.base.ui.web.servlet.util.NabuccoServletUtil;

/**
 * RefreshResultItem
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class RefreshItem extends WebActionResultItem {

    private String childId = null;

    /**
     * Creates a new {@link RefreshItem} instance.
     * 
     * @param elementType
     *            the element type to refresh
     */
    public RefreshItem(WebElementType elementType) {
        this(elementType, null);
    }

    /**
     * Creates a new {@link RefreshItem} instance.
     * 
     * @param elementType
     *            the element type to refresh
     * @param the
     *            element id to refresh
     */
    public RefreshItem(WebElementType elementType, String elementId) {
        super(WebActionResultItemType.REFRESH, elementType, elementId);
    }

    /**
     * Creates a new {@link RefreshItem} instance.
     * 
     * @param elementType
     *            the element type to refresh
     * @param elementId
     *            element id to refresh
     * @param childId
     *            child element to be refreshed
     */
    public RefreshItem(WebElementType elementType, String elementId, String childId) {
        super(WebActionResultItemType.REFRESH, elementType, elementId);

        this.childId = childId;
    }

    @Override
    public boolean isValid() {
        if (this.getElementType().equals(WebElementType.EDITOR)) {
            String elementId = this.getElementId();
            if (elementId != null && !elementId.isEmpty()) {
                WorkItem workItem = NabuccoServletUtil.getWorkItem(elementId);
                if (workItem == null) {
                    return false;
                }
            } else {
                WorkItem workItem = NabuccoServletUtil.getSelectedWorkItem();
                if (!workItem.getType().equals(this.getElementType())) {
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public JsonMap toJson() {
        JsonMap retVal = super.toJson();
        
        if (this.childId != null) {
            retVal.add(JSON_VALUE, this.childId);
        }

        return retVal;
    }
}
