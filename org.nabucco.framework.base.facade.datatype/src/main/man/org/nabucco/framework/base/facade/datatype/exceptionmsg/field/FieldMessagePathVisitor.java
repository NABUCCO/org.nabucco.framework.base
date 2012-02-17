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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.NType;
import org.nabucco.framework.base.facade.datatype.property.CollectionProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyType;
import org.nabucco.framework.base.facade.datatype.visitor.DatatypeVisitor;
import org.nabucco.framework.base.facade.datatype.visitor.VisitorException;

/**
 * FieldMessagePathVisitor
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
final class FieldMessagePathVisitor extends DatatypeVisitor {

    private Object child;

    private List<FieldMessagePathToken> pathTokens;

    /**
     * Creates a new {@link FieldMessagePathVisitor} instance.
     * 
     * @param property
     *            the nabucco property
     */
    public FieldMessagePathVisitor(NabuccoProperty property) {
        this.child = property.getInstance();
        this.pathTokens = new ArrayList<FieldMessagePathToken>();
    }

    @Override
    public void visit(Datatype datatype) throws VisitorException {
        super.visit(datatype);

        for (NabuccoProperty property : datatype.getProperties()) {
            if (property.getInstance() == null) {
                continue;
            }

            if (property.getPropertyType() != NabuccoPropertyType.COLLECTION) {
                this.createProperty(datatype, property);
            } else {
                this.createCollectionProperty(datatype, property);
            }

        }

    }

    /**
     * Create a token for a single property.
     * 
     * @param datatype
     *            the parent datatype
     * @param property
     *            the property
     */
    private void createProperty(Datatype datatype, NabuccoProperty property) {
        if (property.getInstance().equals(this.child)) {
            this.createToken(property);
            this.child = datatype;
        }
    }

    /**
     * Create a token for a collection property.
     * 
     * @param datatype
     *            the parent datatype
     * @param property
     *            the property
     */
    private void createCollectionProperty(Datatype datatype, NabuccoProperty property) {

        CollectionProperty collectionProperty = (CollectionProperty) property;

        if (collectionProperty.isTraversable()) {

            int index = 0;
            for (NType type : collectionProperty.getInstance()) {
                if (type.equals(this.child)) {
                    FieldMessagePathToken token = this.createToken(property);
                    token.setIndex(index);
                    this.child = datatype;
                    break;
                }

                index++;
            }
        }
    }

    /**
     * Create a new path token and adds it to the list.
     * 
     * @param property
     *            the property to add
     * 
     * @return the path token
     */
    private FieldMessagePathToken createToken(NabuccoProperty property) {
        FieldMessagePathToken token = new FieldMessagePathToken(property);
        this.pathTokens.add(token);
        return token;
    }

    /**
     * Getter for the property path.
     * 
     * @return the path of the property
     */
    public String getPath() {

        Collections.reverse(this.pathTokens);

        StringBuilder path = new StringBuilder();
        for (FieldMessagePathToken token : this.pathTokens) {
            path.append(token.getProperty().getName());
            if (token.getIndex() != null) {
                path.append('/');
                path.append(token.getIndex());
            }
            path.append('/');
        }

        return path.toString();
    }
}
