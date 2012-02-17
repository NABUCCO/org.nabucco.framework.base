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

/**
 * Item that opens a new element
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class OpenItem extends WebActionResultItem {

    /**
     * Creates a new {@link OpenItem} instance.
     * 
     * @param dialogId
     *            the id of the dialog to display
     */
    public OpenItem(WebElementType type, String dialogId) {
        super(WebActionResultItemType.OPEN, type, dialogId);
    }

    /**
     * 
     * Creates a new {@link OpenItem} instance.
     * 
     * @param url
     *            url to be opened
     */
    public OpenItem(String url){
        super(WebActionResultItemType.OPEN, WebElementType.LINK, url);
    }

}
