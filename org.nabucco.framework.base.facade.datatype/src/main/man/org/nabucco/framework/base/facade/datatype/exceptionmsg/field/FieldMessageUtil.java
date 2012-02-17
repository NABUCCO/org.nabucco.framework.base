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
package org.nabucco.framework.base.facade.datatype.exceptionmsg.field;

import java.util.regex.Pattern;

import org.nabucco.framework.base.facade.datatype.NType;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoCollection;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoCollectionType;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoList;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.nabucco.framework.base.facade.datatype.property.CollectionProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyType;
import org.nabucco.framework.base.facade.datatype.property.PropertyOwner;
import org.nabucco.framework.base.facade.datatype.visitor.VisitorException;

/**
 * FieldMessageUtil
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class FieldMessageUtil {

    /** The pattern for the property path. */
    private static final Pattern PATTERN = Pattern.compile("/");

    /** The logger */
    private static NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(FieldMessageUtil.class);

    /**
     * Private constructor must not be invoked.
     */
    private FieldMessageUtil() {
    }

    /**
     * Resolves a field message from the root object and the marked property
     * 
     * @param root
     *            the root object
     * @param property
     *            the property to resolve
     * @param code
     *            the field code
     * 
     * @return the field message for the given property
     */
    public static FieldMessage getFieldMessage(PropertyOwner root, NabuccoProperty property, FieldMessageCodeType code) {
        if (root == null) {
            throw new IllegalArgumentException("Cannot create field message for root [null].");
        }
        if (property == null) {
            throw new IllegalArgumentException("Cannot create field message for property [null].");
        }
        if (code == null) {
            throw new IllegalArgumentException("Cannot create field message for code [null].");
        }

        try {

            FieldMessagePathVisitor visitor = new FieldMessagePathVisitor(property);
            root.accept(visitor);
            FieldMessage message = new FieldMessage(visitor.getPath(), code);
            return message;

        } catch (VisitorException e) {
            logger.warning(e, "Error creating property path.");
        }

        return null;
    }

    /**
     * Resolves a property out of a root element via a path.
     * 
     * @param root
     *            the root element to resolve
     * @param path
     *            the path to the property
     * 
     * @return the resolved property
     */
    public static NabuccoProperty getProperty(PropertyOwner root, String path) {
        if (root == null) {
            throw new IllegalArgumentException("Cannot resolve property for root [null].");
        }
        if (path == null) {
            throw new IllegalArgumentException("Cannot resolve property for path [null].");
        }

        String[] tokens = PATTERN.split(path);

        PropertyOwner parent = root;
        NabuccoProperty property = null;

        for (int i = 0; i < tokens.length; i++) {

            String name = tokens[i];
            property = parent.getProperty(name);

            if (property == null || property.getInstance() == null) {
                return null;
            }

            if (property.getInstance() instanceof PropertyOwner) {
                parent = (PropertyOwner) property.getInstance();
                continue;
            }

            if (property.getPropertyType() == NabuccoPropertyType.COLLECTION) {

                i++;
                String index = tokens[i];

                CollectionProperty collectionProperty = (CollectionProperty) property;
                if (!collectionProperty.isTraversable()) {
                    return null;
                }

                NabuccoCollection<? extends NType> collection = collectionProperty.getInstance();

                if (collection.getType() == NabuccoCollectionType.LIST) {
                    parent = (PropertyOwner) ((NabuccoList<?>) collection).get(Integer.parseInt(index));
                    continue;
                }
            }

            return null;
        }

        return property;
    }
}
