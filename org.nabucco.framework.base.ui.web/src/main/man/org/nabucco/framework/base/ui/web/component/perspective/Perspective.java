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
package org.nabucco.framework.base.ui.web.component.perspective;

import java.util.List;

import org.nabucco.common.extension.ExtensionException;
import org.nabucco.framework.base.facade.datatype.NabuccoSystem;
import org.nabucco.framework.base.facade.datatype.extension.ExtensionId;
import org.nabucco.framework.base.facade.datatype.extension.ExtensionPointType;
import org.nabucco.framework.base.facade.datatype.extension.property.PropertyLoader;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.browser.BrowserAreaExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.navigator.NavigationAreaExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.perspective.PerspectiveExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.WorkAreaExtension;
import org.nabucco.framework.base.ui.web.component.WebComposite;
import org.nabucco.framework.base.ui.web.component.WebElement;
import org.nabucco.framework.base.ui.web.component.WebElementType;
import org.nabucco.framework.base.ui.web.component.browser.BrowserArea;
import org.nabucco.framework.base.ui.web.component.navigator.NavigationArea;
import org.nabucco.framework.base.ui.web.component.work.WorkArea;

/**
 * A perspective defines the layout of the navigation and work area.
 * 
 * @author Frank Ratschinski, PRODYNA AG
 * @author Nicolas Moser, PRODYNA AG
 */
public class Perspective extends WebComposite {

    /** The default serial version UID */
    private static final long serialVersionUID = 1L;

    /** The Perspective Extension. */
    private PerspectiveExtension extension;

    /**
     * Creates a new {@link Perspective} instance.
     * 
     * @param extension
     *            the perspective extension
     */
    public Perspective(PerspectiveExtension extension) {
        super(WebElementType.PERSPECTIVE, extension);
        if (extension == null) {
            throw new IllegalArgumentException("Cannot create perspective for extension 'null'.");
        }

        this.extension = extension;
    }

    @Override
    public void init() throws ExtensionException {
        this.loadNavigatorArea();
        this.loadBrowserArea();
        this.loadWorkArea();
    }

    /**
     * Load the web application navigation area.
     * 
     * @throws ExtensionException
     *             when the navigation area is not configured correctly
     */
    private void loadNavigatorArea() throws ExtensionException {
        String id = PropertyLoader.loadProperty(this.extension.getNavigationArea());
        if (id == null || id.isEmpty()) {
            throw new ExtensionException("Cannot load navigator area with id 'null'.");
        }

        NavigationAreaExtension extension = (NavigationAreaExtension) NabuccoSystem.getExtensionResolver()
                .resolveExtension(ExtensionPointType.ORG_NABUCCO_UI_NAVIGATIONAREA, id);

        NavigationArea navigationArea = new NavigationArea(extension, this.extension.getIdentifier().getValue());
        super.addElement(id, navigationArea);
        navigationArea.init();
    }

    /**
     * Load the web application browser area.
     * 
     * @throws ExtensionException
     *             when the browser area is not configured correctly
     */
    private void loadBrowserArea() throws ExtensionException {
        String id = PropertyLoader.loadProperty(this.extension.getBrowserArea());
        if (id == null || id.isEmpty()) {
            throw new ExtensionException("Cannot load browser area with id 'null'.");
        }

        BrowserAreaExtension extension = (BrowserAreaExtension) NabuccoSystem.getExtensionResolver().resolveExtension(
                ExtensionPointType.ORG_NABUCCO_UI_BROWSERAREA, id);

        BrowserArea browserArea = new BrowserArea(extension);
        super.addElement(id, browserArea);
        browserArea.init();
    }

    /**
     * Load the web application work area.
     * 
     * @throws ExtensionException
     *             when the work area is not configured correctly
     */
    private void loadWorkArea() throws ExtensionException {
        String id = PropertyLoader.loadProperty(this.extension.getWorkArea());
        if (id == null || id.isEmpty()) {
            throw new ExtensionException("Cannot load work area with id 'null'.");
        }

        WorkAreaExtension extension = (WorkAreaExtension) NabuccoSystem.getExtensionResolver().resolveExtension(
                ExtensionPointType.ORG_NABUCCO_UI_WORKAREA, id);

        WorkArea workArea = new WorkArea(extension);
        super.addElement(id, workArea);
        workArea.init();
    }

    /**
     * Getter for the perspective ID.
     * 
     * @return the perspective id
     */
    public String getId() {
        ExtensionId identifier = this.extension.getIdentifier();
        if (identifier != null) {
            return identifier.getValue();
        }
        return null;
    }

    /**
     * Getter for the perspective label string.
     * 
     * @return the perspective label
     */
    public String getLabel() {
        return PropertyLoader.loadProperty(this.extension.getLabel());
    }

    /**
     * Getter for the perspective tooltip string.
     * 
     * @return the perspective tooltip
     */
    public String getTooltip() {
        return PropertyLoader.loadProperty(this.extension.getTooltip());
    }

    /**
     * Getter for the perspective index.
     * 
     * @return the perspective index
     */
    public Integer getIndex() {
        return PropertyLoader.loadProperty(this.extension.getIndex());
    }

    /**
     * Getter for the navigationArea.
     * 
     * @return Returns the navigationArea.
     */
    public NavigationArea getNavigationArea() {
        List<WebElement> children = super.getElements();
        for (WebElement child : children) {
            if (child instanceof NavigationArea) {
                return (NavigationArea) child;
            }
        }
        return null;
    }

    /**
     * Getter for the browserArea.
     * 
     * @return Returns the browserArea, or null if none is defined
     */
    public BrowserArea getBrowserArea() {
        List<WebElement> children = super.getElements();
        for (WebElement child : children) {
            if (child instanceof BrowserArea) {
                return (BrowserArea) child;
            }
        }
        return null;
    }

    /**
     * Getter for the workArea.
     * 
     * @return Returns the workArea, or null if none is defined
     */
    public WorkArea getWorkArea() {
        List<WebElement> children = super.getElements();
        for (WebElement child : children) {
            if (child instanceof WorkArea) {
                return (WorkArea) child;
            }
        }
        return null;
    }

}
