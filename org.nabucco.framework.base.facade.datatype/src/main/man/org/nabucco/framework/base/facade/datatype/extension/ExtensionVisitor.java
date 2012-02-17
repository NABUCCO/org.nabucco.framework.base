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
package org.nabucco.framework.base.facade.datatype.extension;

import org.nabucco.framework.base.facade.datatype.Basetype;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.extension.property.BooleanProperty;
import org.nabucco.framework.base.facade.datatype.extension.property.BooleanPropertyValue;
import org.nabucco.framework.base.facade.datatype.extension.property.ClassProperty;
import org.nabucco.framework.base.facade.datatype.extension.property.ClassPropertyValue;
import org.nabucco.framework.base.facade.datatype.extension.property.EnumerationProperty;
import org.nabucco.framework.base.facade.datatype.extension.property.EnumerationPropertyValue;
import org.nabucco.framework.base.facade.datatype.extension.property.IntegerProperty;
import org.nabucco.framework.base.facade.datatype.extension.property.IntegerPropertyValue;
import org.nabucco.framework.base.facade.datatype.extension.property.StringProperty;
import org.nabucco.framework.base.facade.datatype.extension.property.StringPropertyValue;
import org.nabucco.framework.base.facade.datatype.extension.property.UrlProperty;
import org.nabucco.framework.base.facade.datatype.extension.property.UrlPropertyValue;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.nabucco.framework.base.facade.datatype.visitor.DatatypeVisitor;
import org.nabucco.framework.base.facade.datatype.visitor.VisitorException;

/**
 * Visitor for NABUCCO Extensions.
 * 
 * @author Frank Ratschinski
 */
public class ExtensionVisitor extends DatatypeVisitor {

    private static final NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(ExtensionVisitor.class);

    public void visit(NabuccoExtensionComposite composite) throws VisitorException {
        super.visit(composite);
    }

    public void visit(StringProperty property) throws VisitorException {
    }

    public void visit(BooleanProperty property) throws VisitorException {
    }

    public void visit(IntegerProperty property) throws VisitorException {
    }

    public void visit(EnumerationProperty property) throws VisitorException {
    }

    public void visit(UrlProperty property) throws VisitorException {
    }

    public void visit(ClassProperty property) throws VisitorException {
    }

    public void visit(StringPropertyValue property) throws VisitorException {
    }

    public void visit(BooleanPropertyValue value) throws VisitorException {
    }

    public void visit(IntegerPropertyValue value) throws VisitorException {
    }

    public void visit(EnumerationPropertyValue value) throws VisitorException {
    }

    public void visit(UrlPropertyValue value) throws VisitorException {
    }

    public void visit(ClassPropertyValue value) throws VisitorException {
    }

    public void visit(ExtensionId id) throws VisitorException {
    }

    @Override
    public void visit(Basetype basetype) throws VisitorException {
        if (basetype == null) {
            logger.warning("Visiting Nullpointer as Basetype, returning without any action");
            return;
        }
        if (basetype instanceof StringPropertyValue) {
            StringPropertyValue value = (StringPropertyValue) basetype;
            visit(value);
        } else if (basetype instanceof BooleanPropertyValue) {
            BooleanPropertyValue value = (BooleanPropertyValue) basetype;
            visit(value);
        } else if (basetype instanceof IntegerPropertyValue) {
            IntegerPropertyValue value = (IntegerPropertyValue) basetype;
            visit(value);
        } else if (basetype instanceof EnumerationPropertyValue) {
            EnumerationPropertyValue value = (EnumerationPropertyValue) basetype;
            visit(value);
        } else if (basetype instanceof UrlPropertyValue) {
            UrlPropertyValue value = (UrlPropertyValue) basetype;
            visit(value);
        } else if (basetype instanceof ClassPropertyValue) {
            ClassPropertyValue value = (ClassPropertyValue) basetype;
            visit(value);
        } else if (basetype instanceof ExtensionId) {
            ExtensionId id = (ExtensionId) basetype;
            visit(id);
        } else {
            logger.error("Unsupported type of extension property class=[", basetype.getClass().getName(), "]");
        }

    }

    @Override
    public void visit(Datatype datatype) throws VisitorException {
        if (datatype == null) {
            logger.warning("Visiting Nullpointer as Datatype, returning without any action");
            return;
        }
        if (datatype instanceof NabuccoExtensionElement) {
            NabuccoExtensionElement element = (NabuccoExtensionElement) datatype;
            visit(element);
        } else if (datatype instanceof NabuccoExtensionComposite) {
            NabuccoExtensionComposite composite = (NabuccoExtensionComposite) datatype;
            visit(composite);
        } else {
            logger.error("Unsupported type of extension class=[", datatype.getClass().getName(), "]");
        }
    }

    public void visit(NabuccoExtensionElement element) throws VisitorException {
        if (element == null) {
            logger.warning("Visiting Nullpointer as NabuccoExtensionElement, returning without any action");
            return;
        }
        if (element instanceof StringProperty) {
            StringProperty p = (StringProperty) element;
            visit(p);
        } else if (element instanceof BooleanProperty) {
            BooleanProperty p = (BooleanProperty) element;
            visit(p);
        } else if (element instanceof IntegerProperty) {
            IntegerProperty p = (IntegerProperty) element;
            visit(p);
        } else if (element instanceof EnumerationProperty) {
            EnumerationProperty p = (EnumerationProperty) element;
            visit(p);
        } else if (element instanceof UrlProperty) {
            UrlProperty p = (UrlProperty) element;
            visit(p);
        } else if (element instanceof ClassProperty) {
            ClassProperty p = (ClassProperty) element;
            visit(p);
        } else {
            logger.error("Unsupported type of extension property class=[", element.getClass().getName(), "]");
        }
    }

}
