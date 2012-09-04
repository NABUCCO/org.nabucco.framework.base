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
package org.nabucco.framework.base.ui.web.component.browser;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import org.nabucco.common.extension.ExtensionException;
import org.nabucco.framework.base.facade.datatype.NabuccoSystem;
import org.nabucco.framework.base.facade.datatype.extension.ExtensionMap;
import org.nabucco.framework.base.facade.datatype.extension.ExtensionPointType;
import org.nabucco.framework.base.facade.datatype.extension.property.PropertyLoader;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.browser.BrowserAreaExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.browser.BrowserExtension;
import org.nabucco.framework.base.ui.web.component.WebComposite;
import org.nabucco.framework.base.ui.web.component.WebElement;
import org.nabucco.framework.base.ui.web.component.WebElementType;
import org.nabucco.framework.base.ui.web.json.JsonElement;
import org.nabucco.framework.base.ui.web.json.JsonList;
import org.nabucco.framework.base.ui.web.json.JsonMap;

/**
 * The browser area presents all configured {@link Browser} instances in a configured layout.
 * 
 * @author Frank Ratschinski, PRODYNA AG
 * @author Nicolas Moser, PRODYNA AG
 */
public class BrowserArea extends WebComposite {

    /** The default serial version UID */
    private static final long serialVersionUID = 1L;

    private static final String JSON_SELECTED = "selected";

    private static final String JSON_BROWSERS = "browsers";

    private static final String JSON_SYNC = "sync";

    /** ID of the currenlty focused browser */
    private String selectedBrowserId;

    private boolean synchronize = false;

    /** The BrowserArea Extension. */
    private BrowserAreaExtension extension;

    /**
     * Creates a new {@link BrowserArea} instance.
     * 
     * @param extension
     *            the browser area extension
     */
    public BrowserArea(BrowserAreaExtension extension) {
        super(WebElementType.BROWSER_AREA, extension);
        if (extension == null) {
            throw new IllegalArgumentException("Cannot create browser area for extension 'null'.");
        }

        this.extension = extension;
    }

    @Override
    public void init() throws ExtensionException {
        this.loadBrowsers();
    }

    /**
     * Load the web browser of the browser area.
     * 
     * @throws ExtensionException
     *             when a browser is not configured correctly
     */
    private void loadBrowsers() throws ExtensionException {
        ExtensionMap browserMap = NabuccoSystem.getExtensionResolver().resolveExtensions(
                ExtensionPointType.ORG_NABUCCO_UI_BROWSER);

        String[] browserIds = browserMap.getExtensionNames();

        for (String browserId : browserIds) {

            BrowserExtension browserExtension = (BrowserExtension) browserMap.getExtension(browserId);

            Class<?> browserClass = PropertyLoader.loadProperty(browserExtension.getBrowserClass());

            if (!Browser.class.isAssignableFrom(browserClass)) {
                throw new ExtensionException("Cannot load configured browser class ["
                        + browserClass.getName() + "] for browser " + browserId + ".");
            }

            try {
                Constructor<?>[] constructors = browserClass.getConstructors();
                if (constructors.length < 1) {
                    throw new IllegalStateException("Browser classes must have exactly one constructor.");
                }

                Constructor<?> constructor = constructors[0];
                Browser browser = (Browser) constructor.newInstance(browserExtension);

                this.addElement(browserId, browser);
                browser.init();

            } catch (Exception e) {
                throw new ExtensionException("Cannot instantiate configured browser class ["
                        + browserClass.getName() + "] for browser " + browserId + ".", e);
            }
        }
    }

    /**
     * Getter for the browsers with the given id.
     * 
     * @param name
     *            name of the browser
     * 
     * @return the browser or null if no browser with the given name exists
     */
    public Browser getBrowser(String id) {
        WebElement child = super.getElement(id);
        if (child instanceof Browser) {
            return (Browser) child;
        }
        return null;
    }

    /**
     * Select the browser with the given id.
     * 
     * @param id
     *            the browser id to select
     */
    public void selectBrowser(String id) {
        if (this.getBrowser(id) != null) {
            this.selectedBrowserId = id;
        }
    }

    /**
     * Getter for the currently focused browser.
     * 
     * @return the current browser or null if none is focused
     */
    public Browser getSelectedBrowser() {
        Browser browser = this.getBrowser(this.selectedBrowserId);

        if (browser != null) {
            return browser;
        }

        String[] elementIds = super.getElementIds();
        for (int i = 0; i < elementIds.length; i++) {
            WebElement element = super.getElement(elementIds[i]);
            if (element instanceof Browser) {
                this.selectedBrowserId = elementIds[i];
                return (Browser) element;
            }
        }

        return null;
    }

    /**
     * Getter for the browsers.
     * 
     * @return Returns the browsers.
     */
    public List<Browser> getBrowsers() {
        List<Browser> browsers = new ArrayList<Browser>();
        for (WebElement child : super.getElements()) {
            if (child instanceof Browser) {
                browsers.add((Browser) child);
            }
        }
        return browsers;
    }

    /**
     * Getter for the browser area layout.
     * 
     * @return the layout
     */
    public String getLayout() {
        return PropertyLoader.loadProperty(this.extension.getLayout());
    }

    /**
     * Indicates if the browser area is synchronized to the working area
     * 
     * @return true if synchronized
     */
    public boolean isSynchronized() {
        return synchronize;
    }

    /**
     * Sets the synchronized value of the browser area
     * 
     * @param value
     *            value to be set
     */
    public void setSynchronized(boolean value) {
        synchronize = value;
    }

    @Override
    public JsonElement toJson() {

        JsonMap json = new JsonMap();
        JsonList jsonList = new JsonList();

        String[] elementIds = super.getElementIds();
        for (int i = 0; i < elementIds.length; i++) {
            Browser browser = (Browser) super.getElement(elementIds[i]);
            JsonMap jsonEntry = new JsonMap();
            jsonEntry.add(JSON_ID, elementIds[i]);
            jsonEntry.add(JSON_LABEL, browser.getLabel());
            jsonEntry.add(JSON_TOOLTIP, browser.getTooltip());
            jsonList.add(jsonEntry);
        }

        json.add(JSON_LAYOUT, this.getLayout());
        json.add(JSON_BROWSERS, jsonList);
        json.add(JSON_SYNC, synchronize);

        Browser selected = this.getSelectedBrowser();
        if (selected != null) {
            json.add(JSON_SELECTED, selected.getId());
        }

        return json;
    }

}
