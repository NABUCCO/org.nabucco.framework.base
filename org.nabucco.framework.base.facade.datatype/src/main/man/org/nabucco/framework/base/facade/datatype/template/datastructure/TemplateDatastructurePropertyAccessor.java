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
package org.nabucco.framework.base.facade.datatype.template.datastructure;

import java.util.List;

import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.NType;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyResolver;
import org.nabucco.framework.base.facade.datatype.property.PropertyAccessor;

/**
 * TemplateDatastructurePropertyAccessor
 * 
 * Custom property accessor to access concrete template composite structure
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class TemplateDatastructurePropertyAccessor extends NabuccoPropertyResolver<Datatype> implements
        PropertyAccessor {

    private static final String VALUE_PROPERTY = "value";

    /**
     * 
     * Creates a new {@link TemplateDatastructurePropertyAccessor} instance.
     * 
     */
    public TemplateDatastructurePropertyAccessor() {
        super();
    }

    /**
     * 
     * Creates a new {@link TemplateDatastructurePropertyAccessor} instance.
     * 
     * @param datatype
     */
    public TemplateDatastructurePropertyAccessor(Datatype datatype) {
        super(datatype);
    }

    @Override
    protected NabuccoProperty locateProperty(String propertyName, Datatype datatype) {
        NabuccoProperty property = null;

        if (datatype instanceof TemplateDSInstance) {
            // Jump over datastructure in instance. Allows the binding to look better out
            // data.datastructure.applicant.... can be written as data.applicant
            TemplateDSInstanceElement datatstructure = ((TemplateDSInstance) datatype).getDatatstructure();

            if (datatstructure == null) {
                return null;
            }

            if (datatstructure.getName() != null
                    && datatstructure.getName().getValue() != null
                    && datatstructure.getName().getValue().equals(propertyName)) {

                property = datatype.getProperty(TemplateDSInstance.DATATSTRUCTURE);
                return property;
            }

            datatype = datatstructure;
        } else {
            property = super.locateProperty(propertyName, datatype);
        }

        if (property == null && datatype instanceof TemplateDSInstanceGroup) {
            TemplateDSInstanceGroup group = (TemplateDSInstanceGroup) datatype;
            List<TemplateDSInstanceElement> childrenList = group.getChildrenList();

            for (TemplateDSInstanceElement item : childrenList) {
                if (item.getName() != null
                        && item.getName().getValue() != null && item.getName().getValue().equals(propertyName)) {

                    if (item instanceof TemplateDSInstanceGroup) {
                        property = item.getProperty(TemplateDSInstanceGroup.CHILDRENLIST);
                    } else {
                        property = item.getProperty(VALUE_PROPERTY);
                    }
                    break;
                }
            }
        }

        return property;
    }

    @Override
    protected NabuccoProperty resolveCollectionProperty(String childPath, List<Datatype> datatypeList) {
        if (childPath == null || childPath.isEmpty()) {
            return null;
        }

        String propertyName = null;
        String propertyLastPart = null;
        if (childPath.contains(PROPERTY_SEPARATOR)) {
            int index = childPath.indexOf(PROPERTY_SEPARATOR);
            propertyName = childPath.substring(0, index);
            propertyLastPart = childPath.substring(index + 1);
        } else {
            propertyName = childPath;
        }

        for (NType item : datatypeList) {
            if (item instanceof TemplateDSInstanceElement) {
                TemplateDSInstanceElement element = (TemplateDSInstanceElement) item;

                if (element.getName() != null
                        && element.getName().getValue() != null && element.getName().getValue().equals(propertyName)) {

                    if (item instanceof TemplateDSInstanceGroup) {
                        return this.resolveProperty(propertyLastPart, element);
                    } else {
                        NabuccoProperty property = element.getProperty(VALUE_PROPERTY);
                        return property;
                    }
                }
            }
        }

        return super.resolveCollectionProperty(childPath, datatypeList);
    }
}
