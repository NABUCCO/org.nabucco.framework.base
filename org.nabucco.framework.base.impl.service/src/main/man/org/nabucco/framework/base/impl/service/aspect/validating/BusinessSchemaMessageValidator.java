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
package org.nabucco.framework.base.impl.service.aspect.validating;

import org.nabucco.framework.base.facade.datatype.NabuccoSystem;
import org.nabucco.framework.base.facade.datatype.extension.ExtensionPointType;
import org.nabucco.framework.base.facade.datatype.extension.property.StringProperty;
import org.nabucco.framework.base.facade.datatype.extension.schema.business.schema.BusinessSchemaExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.property.PropertyExtension;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.nabucco.framework.base.facade.datatype.visitor.VisitorException;
import org.nabucco.framework.base.facade.message.ServiceMessage;

/**
 * BusinessSchemaMessageValidator
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class BusinessSchemaMessageValidator implements ValidatingAspect {

    private static final String PROPERTY_SCHEMA = "schema";

    private BusinessSchemaExtension extension;

    private static NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(
            BusinessSchemaMessageValidator.class);

    @Override
    public void configure(PropertyExtension properties) {
        try {
            StringProperty schema = (StringProperty) properties.getPropertyMap().get(PROPERTY_SCHEMA);

            if (schema != null && schema.getValue() != null) {
                String schemaId = schema.getValue().getValue();

                this.extension = (BusinessSchemaExtension) NabuccoSystem.getExtensionResolver().resolveExtension(
                        ExtensionPointType.ORG_NABUCCO_BUSINESS_SCHEMA, schemaId);
            }
        } catch (Exception e) {
            logger.warning("Property PROPERTY_SCHEMA not set for validating aspect.");
        }
    }

    @Override
    public void validateBefore(ServiceMessage requestMessage) throws ValidatingException {
        if (this.extension == null) {
            return;
        }

        try {
            BusinessSchemaVisitor visitor = new BusinessSchemaVisitor(this.extension);
            requestMessage.accept(visitor);
        } catch (VisitorException ve) {
            throw new ValidatingException("Error validating request against business schema '"
                    + this.extension.getIdentifier().getValue() + "'.", ve);
        }
    }

    @Override
    public void validateAfter(ServiceMessage requestMessage, ServiceMessage responseMessage) throws ValidatingException {
        // Nothing to validate after invocation.
    }

}
