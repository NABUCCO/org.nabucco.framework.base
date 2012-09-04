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
package org.nabucco.framework.base.ui.web.component;

import java.io.Serializable;
import java.util.List;

import org.nabucco.common.extension.ExtensionException;
import org.nabucco.framework.base.facade.datatype.extension.property.EnumerationProperty;
import org.nabucco.framework.base.facade.datatype.extension.property.PropertyLoader;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.UiExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.common.PermissionExtension;
import org.nabucco.framework.base.facade.datatype.security.Permission;
import org.nabucco.framework.base.facade.datatype.ui.permission.UiAccessType;
import org.nabucco.framework.base.facade.datatype.visitor.VisitorException;
import org.nabucco.framework.base.ui.web.component.work.visitor.VisitableWebElement;
import org.nabucco.framework.base.ui.web.component.work.visitor.WebElementVisitor;
import org.nabucco.framework.base.ui.web.component.work.visitor.WebElementVisitorContext;
import org.nabucco.framework.base.ui.web.json.Jsonable;
import org.nabucco.framework.base.ui.web.session.NabuccoWebSessionFactory;

/**
 * An element that is presentable in a web user interface. Each web element may have a couple of
 * child elements, addressable by an identifier.
 * 
 * @author Frank Ratschinski, PRODYNA AG
 * @author Nicolas Moser, PRODYNA AG
 */
public abstract class WebElement implements Serializable, Jsonable, VisitableWebElement {

    /** The default serial version UID */
    private static final long serialVersionUID = 1L;

    /** Default Access Type of all Web Elements */
    private static final UiAccessType DEFAULT_ACCESS = UiAccessType.ACTIVE;

    /** The Web Element Type */
    private WebElementType type;

    /** The UI Extension */
    private UiExtension extension;

    /** The Access Type of this element */
    private UiAccessType accessType;

    /**
     * Creates a new {@link WebElement} instance.
     * 
     * @param type
     *            the web element type
     * @param extension
     *            the UI extension
     */
    public WebElement(WebElementType type, UiExtension extension) {
        if (type == null) {
            throw new IllegalArgumentException("Cannot create web element for type [null].");
        }
        if (extension == null) {
            throw new IllegalArgumentException("Cannot create web element for extension [null].");
        }

        this.type = type;
        this.extension = extension;
    }

    /**
     * Getter for the access type of this Web Element.
     * 
     * @return the access type of this element
     */
    public UiAccessType getAccessType() {
        if (this.accessType != null) {
            return this.accessType;
        }

        // Default Access Type
        EnumerationProperty defaultGrant = this.extension.getGrant();
        if (defaultGrant == null || defaultGrant.getValue() == null) {
            this.accessType = DEFAULT_ACCESS;
        } else if (defaultGrant.getValue().getValue() == null || defaultGrant.getValue().getValue().isEmpty()) {
            this.accessType = DEFAULT_ACCESS;
        } else {
            this.accessType = PropertyLoader.loadProperty(UiAccessType.class, defaultGrant);
            if (this.accessType == null) {
                this.accessType = DEFAULT_ACCESS;
            }
        }

        // Permission Access Type
        for (PermissionExtension permissionExtension : this.extension.getPermissions()) {
            String permissionId = PropertyLoader.loadProperty(permissionExtension.getPermissionId());
            UiAccessType accessType = PropertyLoader.loadProperty(UiAccessType.class, permissionExtension.getGrant());

            for (Permission permission : NabuccoWebSessionFactory.getCurrentSession().getSecurityContext()
                    .getPermissionList()) {

                if (permission.getPermissionname() == null || permission.getPermissionname().getValue() == null) {
                    continue;
                }

                String permissionName = permission.getPermissionname().getValue();

                if (permissionName.equalsIgnoreCase(permissionId)) {
                    this.accessType = accessType;
                    break;
                }
            }
        }

        return this.accessType;
    }

    /**
     * Initialize the web element by the configured extensions.
     * 
     * @throws ExtensionException
     *             when the extensions are not valid
     */
    public abstract void init() throws ExtensionException;

    /**
     * Reset the web elements current state.
     */
    public void clear() {
        for (WebElement element : this.getElements()) {
            element.clear();
        }
    }

    /**
     * Getter for the type.
     * 
     * @return Returns the type.
     */
    public WebElementType getType() {
        return this.type;
    }

    /**
     * Getter for the ids of all child elements.
     * 
     * @return the child element ids
     */
    public abstract String[] getElementIds();

    /**
     * Getter for all child elements.
     * 
     * @return the list of all child elements
     */
    public abstract List<WebElement> getElements();

    /**
     * Getter for the child element with the given id.
     * 
     * @param id
     *            the child element id
     * 
     * @return the child element with the given id, or null if the element with the given id does
     *         not exist in this element
     */
    public abstract WebElement getElement(String id);

    /**
     * Accepts the web element visitor. Overload this function to let element be visited
     * 
     * @param visitor
     */
    @Override
    public <T extends WebElementVisitorContext> void accept(WebElementVisitor<T> visitor, T context)
            throws VisitorException {
        if (visitor != null) {
            visitor.visit(this, context);
        }
    }

}
