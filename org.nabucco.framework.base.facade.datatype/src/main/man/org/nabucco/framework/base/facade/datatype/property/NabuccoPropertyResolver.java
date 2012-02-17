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
package org.nabucco.framework.base.facade.datatype.property;

import org.nabucco.framework.base.facade.datatype.Basetype;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.ExtendedAttribute;
import org.nabucco.framework.base.facade.datatype.NType;
import org.nabucco.framework.base.facade.datatype.NabuccoDatatype;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoList;
import org.nabucco.framework.base.facade.datatype.componentrelation.ComponentRelation;
import org.nabucco.framework.base.facade.datatype.converter.BasetypeConverter;
import org.nabucco.framework.base.facade.datatype.converter.ConverterException;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;

/**
 * Resolves properties of a datatype by a given path.
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class NabuccoPropertyResolver<T extends Datatype> {

    /** Constant for property path seperators. */
    private static final String PROPERTY_SEPARATOR = ".";

    /** The parent datatype. */
    private T datatype;

    /** Logger */
    private NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(NabuccoPropertyResolver.class);

    /**
     * Creates a new {@link NabuccoPropertyResolver} instance.
     * 
     * @param datatype
     */
    public NabuccoPropertyResolver(T datatype) {
        if (datatype == null) {
            throw new IllegalArgumentException("Cannot resolve property of parent datatype 'null'.");
        }
        this.datatype = datatype;
    }

    /**
     * Convert the property path into a property.
     * 
     * @param propertyPath
     *            the property path pointing to the property of the given datatype. A property path
     *            may be seperated by a point <b>"."</b> to reference a sub-property.
     * 
     *            <p/>
     *            <b>Examples:</b>
     *            <li><code>description</code></li>
     *            <li><code>user.username</code></li>
     * 
     * 
     * @return the referenced property or an empty string
     */
    public NabuccoProperty resolveProperty(String propertyPath) {
        return this.resolveProperty(propertyPath, this.datatype);
    }

    /**
     * Getter for property of the given datatype.
     * 
     * @param propertyPath
     *            the path to the property
     * @param datatype
     *            the datatype holding the property
     * 
     * @return the referenced property
     */
    private NabuccoProperty resolveProperty(String propertyPath, Datatype datatype) {
        if (propertyPath == null || propertyPath.isEmpty()) {
            return null;
        }
        if (datatype == null) {
            return null;
        }

        if (datatype instanceof ComponentRelation<?>) {
            NabuccoProperty property = datatype.getProperty(ComponentRelation.TARGET);
            datatype = (Datatype) property.getInstance();
        }

        if (propertyPath.contains(PROPERTY_SEPARATOR)) {
            int index = propertyPath.indexOf(PROPERTY_SEPARATOR);
            String parentPath = propertyPath.substring(0, index);

            NabuccoProperty property = datatype.getProperty(parentPath);
            if (property == null) {
                return null;
            }

            String childPath = propertyPath.substring(index + 1);

            switch (property.getPropertyType()) {

            case DATATYPE: {
                Datatype parent = (Datatype) property.getInstance();
                return this.resolveProperty(childPath, parent);
            }

            case COLLECTION: {
                if (property.getInstance() instanceof NabuccoList<?>) {

                    @SuppressWarnings("unchecked")
                    NabuccoList<Datatype> list = (NabuccoList<Datatype>) property.getInstance();
                    NType first = list.first();
                    if (first instanceof Datatype) {
                        return this.resolveProperty(childPath, (Datatype) first);
                    }
                }

                break;
            }

            case COMPONENT_RELATION: {
                if (property.getInstance() instanceof NabuccoList<?>) {

                    @SuppressWarnings("unchecked")
                    NabuccoList<ComponentRelation<NabuccoDatatype>> list = (NabuccoList<ComponentRelation<NabuccoDatatype>>) property
                            .getInstance();
                    ComponentRelation<NabuccoDatatype> first = list.first();
                    if (first != null) {
                        return this.resolveProperty(childPath, first.getTarget());
                    }
                }

                break;
            }

            }

            return null;
        }

        NabuccoProperty property = datatype.getProperty(propertyPath);

        if (property == null) {
            property = this.resolveExtendedAttribute(propertyPath, datatype);
        }

        return property;

    }

    /**
     * Resolve the property of the extended attributes!
     * 
     * @param propertyName
     *            the property path to resolve from the extended attributes
     * @param datatype
     *            the owning datatype
     * 
     * @return the resolved basetype property
     */
    private BasetypeProperty resolveExtendedAttribute(String propertyName, Datatype datatype) {

        if (!(datatype instanceof NabuccoDatatype)) {
            return null;
        }

        NabuccoProperty property = datatype.getProperty(NabuccoDatatype.EXTENDEDATTRIBUTES);

        CollectionProperty collectionProperty = (CollectionProperty) property;
        if (!collectionProperty.isTraversable()) {
            return null;
        }

        for (NType value : collectionProperty.getInstance()) {
            ExtendedAttribute attribute = (ExtendedAttribute) value;

            if (attribute.getName() == null || attribute.getName().getValue() == null) {
                continue;
            }

            if (attribute.getValue() == null || attribute.getValue().getValue() == null) {
                continue;
            }
            if (!attribute.getName().getValue().equalsIgnoreCase(propertyName)) {
                continue;
            }

            try {
                Class<?> basetypeClass = Class.forName(attribute.getType().getValue());
                Basetype basetype = (Basetype) basetypeClass.newInstance();
                BasetypeConverter.setValueAsString(basetype, attribute.getValue().getValue());

                PropertyDescriptorSupport descriptor = PropertyDescriptorSupport.createBasetype(attribute.getName()
                        .getValue(), basetype.getClass(), -1, "", false);

                return (BasetypeProperty) descriptor.createProperty(datatype, basetype, "");

            } catch (ClassNotFoundException ce) {
                this.logger.error("Problem by reading of ext.property ", propertyName, " class can not be found ", ce);
            } catch (InstantiationException e) {
                this.logger.error("Problem by reading of ext.property ", propertyName,
                        " class cannot be instanciated ", e);
            } catch (IllegalAccessException e) {
                this.logger.error("Problem by reading of ext.property ", propertyName, " illegal access ", e);
            } catch (ConverterException e) {
                this.logger.error("Problem by reading of ex.property ", propertyName, " value converting error ", e);
            }

        }

        return null;
    }
}
