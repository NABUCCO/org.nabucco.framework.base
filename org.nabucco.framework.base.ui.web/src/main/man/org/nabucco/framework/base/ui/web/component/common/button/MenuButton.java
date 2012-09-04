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
package org.nabucco.framework.base.ui.web.component.common.button;

import org.nabucco.common.extension.ExtensionException;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.common.MenuButtonExtension;
import org.nabucco.framework.base.ui.web.component.WebComposite;
import org.nabucco.framework.base.ui.web.component.WebElement;
import org.nabucco.framework.base.ui.web.component.WebElementType;
import org.nabucco.framework.base.ui.web.json.JsonElement;
import org.nabucco.framework.base.ui.web.json.JsonList;
import org.nabucco.framework.base.ui.web.json.JsonMap;

/**
 * MenuButton
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class MenuButton extends WebComposite {

    private static final long serialVersionUID = 1L;

    private static final String JSON_BUTTONS = "buttons";

    MenuButtonExtension buttonExtension;

    /**
     * Creates a new {@link MenuButton} instance.
     * 
     * @param buttonExtension
     *            menu button extension
     */
    public MenuButton(MenuButtonExtension buttonExtension) {
        super(WebElementType.MENUBUTTON, buttonExtension);
        this.buttonExtension = buttonExtension;
    }

    @Override
    public void init() throws ExtensionException {

    }

    /**
     * Adds a button to the menu
     * 
     * @param button
     *            button to add
     * @throws ExtensionException
     */
    public void addButton(Button button) throws ExtensionException {
        this.addElement(button.getId(), button);
    }

    @Override
    public JsonElement toJson() {
        JsonMap jsonMap = new JsonMap();

        JsonList buttonList = new JsonList();
        for (WebElement button : this.getElements()) {
            buttonList.add(button.toJson());
        }
        jsonMap.add(JSON_BUTTONS, buttonList);

        return jsonMap;
    }
}
