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
package org.nabucco.framework.base.ui.web.model.editor.util.shortcuts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.nabucco.common.extension.ExtensionException;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoList;
import org.nabucco.framework.base.facade.datatype.extension.ExtensionMap;
import org.nabucco.framework.base.facade.datatype.extension.ExtensionPointType;
import org.nabucco.framework.base.facade.datatype.extension.ExtensionResolver;
import org.nabucco.framework.base.facade.datatype.extension.property.PropertyLoader;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.editor.shortcuts.ShortcutExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.editor.shortcuts.ShortcutsExtension;
import org.nabucco.framework.base.ui.web.json.JsonElement;
import org.nabucco.framework.base.ui.web.json.JsonList;
import org.nabucco.framework.base.ui.web.json.JsonMap;
import org.nabucco.framework.base.ui.web.json.Jsonable;
import org.nabucco.framework.base.ui.web.model.editor.ControlType;
import org.nabucco.framework.base.ui.web.session.NabuccoWebSession;
import org.nabucco.framework.base.ui.web.session.NabuccoWebSessionFactory;

/**
 * ControlShortcutResolver
 * 
 * controls the shortcuts according to the configuration in extension point
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class ControlShortcutResolver implements Jsonable {

    private static final String DEFAULT = "default";

    private static final String JSON_LANGUAGE = "language";

    private static final String JSON_FUNCTIONAL_TYPE = "functionalType";

    private static final String JSON_ALIAS = "alias";

    private static final String JSON_SHORTCUTS = "shortcuts";

    private static ControlShortcutResolver instance;

    private List<ShortcutsExtension> shortcutExtensions = new ArrayList<ShortcutsExtension>();

    /**
     * 
     * Creates a new {@link ControlShortcutResolver} instance.
     * 
     */
    private ControlShortcutResolver() {

    }

    /**
     * Initializes the list with shortcuts
     * 
     * @throws ExtensionException
     */
    private void init() throws ExtensionException {
        ExtensionMap resolveExtensions = new ExtensionResolver()
                .resolveExtensions(ExtensionPointType.ORG_NABUCCO_UI_SHORTCUTS);

        for (String extensionName : resolveExtensions.getExtensionNames()) {
            ShortcutsExtension shortcuts = (ShortcutsExtension) resolveExtensions.getExtension(extensionName);

            shortcutExtensions.add(shortcuts);
        }

    }

    /**
     * Retuns the list of the possible shortcuts for the configured control type
     * 
     * @param controlType
     *            the control type to resolve the shortcuts for
     * @return empty list if nothing configured or found or list with aliases
     */
    public List<String> getShortcutList(ControlType controlType) {
        if (controlType == null) {
            throw new IllegalArgumentException("Cannot get shortcuts of the control type. control type is 'null'.");
        }

        NabuccoWebSession session = NabuccoWebSessionFactory.getCurrentSession();
        String actualSystemLanguage = session.getLocale().getLanguage();

        NabuccoList<ShortcutExtension> shortcuts = null;
        for (ShortcutsExtension shortcutsMap : shortcutExtensions) {

            String language = PropertyLoader.loadProperty(shortcutsMap.getLanguage());

            if (language.equalsIgnoreCase(actualSystemLanguage)) {
                shortcuts = shortcutsMap.getShortcuts();
                break;
            } else if (language.equalsIgnoreCase(DEFAULT)) {
                shortcuts = shortcutsMap.getShortcuts();
            }

        }

        if (shortcuts == null || shortcuts.isEmpty()) {
            return Collections.emptyList();
        }

        List<String> aliasList = new ArrayList<String>();
        for (ShortcutExtension shortcutExtension : shortcuts) {
            String alias = PropertyLoader.loadProperty(shortcutExtension.getAlias());
            aliasList.add(alias);
        }

        return aliasList;
    }

    /**
     * Getter for the control shortcut resolver
     * 
     * @return instance
     */
    public static ControlShortcutResolver getInstance() {
        if (instance == null) {
            try {
                instance = new ControlShortcutResolver();
                instance.init();
            } catch (ExtensionException e) {
                throw new IllegalArgumentException("Cannot initialize shortcuts resolver. Problem reading extension", e);
            }
        }

        return instance;
    }

    @Override
    public JsonElement toJson() {
        JsonList extensions = new JsonList();

        for (ShortcutsExtension extension : shortcutExtensions) {
            JsonList shortcuts = new JsonList();

            for (ShortcutExtension shortcut : extension.getShortcuts()) {
                String alias = PropertyLoader.loadProperty(shortcut.getAlias());
                String functionalType = PropertyLoader.loadProperty(shortcut.getFunctionalType());

                JsonMap shortcutmap = new JsonMap();
                shortcutmap.add(JSON_ALIAS, alias);
                shortcutmap.add(JSON_FUNCTIONAL_TYPE, functionalType);

                shortcuts.add(shortcutmap);
            }

            String type = PropertyLoader.loadProperty(extension.getType());
            String language = PropertyLoader.loadProperty(extension.getLanguage());

            JsonMap shortcutGroup = new JsonMap();
            shortcutGroup.add(JSON_TYPE, type);
            shortcutGroup.add(JSON_LANGUAGE, language);
            shortcutGroup.add(JSON_SHORTCUTS, shortcuts);

            extensions.add(shortcutGroup);
        }

        return extensions;
    }
}
