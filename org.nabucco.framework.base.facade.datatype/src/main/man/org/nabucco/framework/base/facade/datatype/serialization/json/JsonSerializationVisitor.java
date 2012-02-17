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
package org.nabucco.framework.base.facade.datatype.serialization.json;

import java.util.Iterator;

import org.nabucco.framework.base.facade.datatype.Basetype;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.DatatypeState;
import org.nabucco.framework.base.facade.datatype.Enumeration;
import org.nabucco.framework.base.facade.datatype.NBoolean;
import org.nabucco.framework.base.facade.datatype.NDouble;
import org.nabucco.framework.base.facade.datatype.NFloat;
import org.nabucco.framework.base.facade.datatype.NInteger;
import org.nabucco.framework.base.facade.datatype.NLong;
import org.nabucco.framework.base.facade.datatype.NType;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoCollection;
import org.nabucco.framework.base.facade.datatype.property.CollectionProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.PropertyOwner;
import org.nabucco.framework.base.facade.datatype.serialization.SerializationConstants;
import org.nabucco.framework.base.facade.datatype.visitor.DatatypeVisitor;
import org.nabucco.framework.base.facade.datatype.visitor.Visitable;
import org.nabucco.framework.base.facade.datatype.visitor.VisitorException;

/**
 * JsonSerializationVisitor
 * 
 * @author Silas Schwarz, PRODYNA AG
 */
final class JsonSerializationVisitor extends DatatypeVisitor implements JsonSyntaxConstants {

    private StringBuffer buffer = new StringBuffer();

    /**
     * Getter for the buffer content.
     * 
     * @return Returns the buffer.
     */
    public String getBufferContent() {
        return JsonFormatter.format(buffer.toString());
    }

    @Override
    public void visit(Visitable visitable) throws VisitorException {
        if (visitable instanceof Datatype) {
            visit((Datatype) visitable);
        } else if (visitable instanceof Basetype) {
            visit((Basetype) visitable);
        } else if (visitable instanceof PropertyOwner) {
            visit((PropertyOwner) visitable);
        }
    }

    @Override
    public void visit(Datatype datatype) throws VisitorException {
        buffer.append(LEFT_BRACE);
        handleState(datatype.getDatatypeState());
        Iterator<NabuccoProperty> iterator = datatype.getProperties().iterator();
        if (iterator.hasNext()) {
            buffer.append(COMMA);
        }
        while (iterator.hasNext()) {
            NabuccoProperty next = iterator.next();
            appendNabuccoProperty(next);
            if (iterator.hasNext()) {
                buffer.append(COMMA);
            }
        }
        buffer.append(RIGHT_BRACE);
    }

    public void visit(PropertyOwner propertyOwner) throws VisitorException {
        buffer.append(LEFT_BRACE);
        Iterator<NabuccoProperty> iter = propertyOwner.getProperties().iterator();
        while (iter.hasNext()) {
            NabuccoProperty next = iter.next();
            appendNabuccoProperty(next);
            if (iter.hasNext()) {
                buffer.append(COMMA);
            }
        }
        buffer.append(RIGHT_BRACE);
    }

    /**
     * Handles the addition of the Datatype State to the output.
     * 
     * @param datatypeState
     */
    private void handleState(DatatypeState datatypeState) {
        buffer.append(DOUBLE_QUOTES);
        buffer.append(SerializationConstants.TAG_STATE);
        buffer.append(DOUBLE_QUOTES);
        buffer.append(COLON);
        buffer.append(DOUBLE_QUOTES);
        buffer.append(String.valueOf(datatypeState));
        buffer.append(DOUBLE_QUOTES);

    }

    @Override
    protected void visitChildren(Visitable visitable) throws VisitorException {
        super.visitChildren(visitable);
    }

    void appendNabuccoProperty(NabuccoProperty property) throws VisitorException {
        buffer.append(DOUBLE_QUOTES);
        buffer.append(property.getName());
        buffer.append(DOUBLE_QUOTES);
        buffer.append(COLON);
        if (property.getInstance() == null) {
            buffer.append(NULL);
        } else if (property.getInstance() instanceof NBoolean) {
            NBoolean instance = (NBoolean) property.getInstance();
            if (instance.getValue()) {
                buffer.append(TRUE);
            } else {
                buffer.append(FALSE);
            }
        } else {
            switch (property.getPropertyType()) {
            case BASETYPE: {
                handleBasetypeValue(property);
                break;
            }

            case ENUMERATION: {
                buffer.append(DOUBLE_QUOTES);
                Object instance = property.getInstance();
                if (instance instanceof Enumeration) {
                    Enumeration enumInstance = (Enumeration) instance;
                    buffer.append(enumInstance.getId());
                }
                buffer.append(DOUBLE_QUOTES);
                break;
            }
            case DATATYPE: {
                Datatype datatype = (Datatype) property.getInstance();
                datatype.accept(this);
                break;
            }
            case COLLECTION: {
                buffer.append(LEFT_BRACKET);
                CollectionProperty collectionProperty = (CollectionProperty) property;
                NabuccoCollection<? extends NType> instance = collectionProperty.getInstance();
                if (instance.isTraversable()) {
                    Iterator<? extends NType> iterator = instance.iterator();
                    while (iterator.hasNext()) {
                        NType next = iterator.next();
                        next.accept(this);
                        if (iterator.hasNext()) {
                            buffer.append(COMMA);
                        }
                    }
                }
                buffer.append(RIGHT_BRACKET);
                break;
            }
            }

        }
    }

    /**
     * Handle the basetype value.
     * 
     * @param property
     *            the property to handle.
     */
    private void handleBasetypeValue(NabuccoProperty property) {
        Object instance = property.getInstance();
        if (instance instanceof Basetype) {
            Basetype basetypeInstance = (Basetype) instance;
            if (basetypeInstance instanceof NLong
                    || basetypeInstance instanceof NDouble || basetypeInstance instanceof NFloat
                    || basetypeInstance instanceof NInteger) {
                buffer.append(basetypeInstance.getValueAsString());
            } else {
                buffer.append(DOUBLE_QUOTES);
                buffer.append(basetypeInstance.getValueAsString());
                buffer.append(DOUBLE_QUOTES);
            }
        }
    }

}
