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
package org.nabucco.framework.base.impl.service.maintain;

import java.util.Set;

import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoCollectionAccessor;
import org.nabucco.framework.base.facade.datatype.property.CollectionProperty;
import org.nabucco.framework.base.facade.datatype.property.DatatypeProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.visitor.VisitorException;
import org.nabucco.framework.base.facade.message.visitor.ServiceMessageVisitor;

/**
 * PersistenceCleaner
 * <p/>
 * Visitor that visits all datatypes holding {@link java.util.Collection} references and replaces
 * potential persistence implementation by java.util implementations. This is necessary since
 * dependencies on persistence implementations must not be handled by clients.
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class PersistenceCleaner extends ServiceMessageVisitor {

    @Override
    public void visit(Datatype datatype) throws VisitorException {
        this.checkDatatype(datatype);

        super.visit(datatype);
    }

    /**
     * Replaces lazy initialized persistence provider specific datatype proxies and collections by
     * clean accessible implementations.
     * 
     * @param datatype
     *            the datatype to clean
     */
    private void checkDatatype(Datatype datatype) {
        Set<NabuccoProperty> properties = datatype.getProperties();
        if (properties == null || properties.isEmpty()) {
            return;
        }

        for (NabuccoProperty property : properties) {

            switch (property.getPropertyType()) {

            case DATATYPE: {
                if (property.getInstance() != null && !this.isDatatype(property.getInstance())) {
                    this.cleanDatatype((DatatypeProperty) property);
                }
                break;
            }

            case COLLECTION: {
                this.cleanList((CollectionProperty) property);
                break;
            }

            }
        }
    }

    /**
     * Check whether a datatype is a real NABUCCO datatype or only a reflection proxy. The original
     * generated NABUCCO {@link Datatype} instances have a direct interface realization.
     * 
     * @param datatype
     *            the datatype to check for authenticity
     * 
     * @return <b>true</b> if the datatype is a real NABUCCO datatype, <b>false</b> if not (e.g. a
     *         {@link java.lang.reflect.Proxy}).
     */
    private boolean isDatatype(Object datatype) {
        if (!(datatype instanceof Datatype)) {
            return false;
        }

        Class<?> datatypeClass = datatype.getClass();
        for (Class<?> intf : datatypeClass.getInterfaces()) {
            if (intf == Datatype.class) {
                return true;
            }
        }
        return false;
    }

    /**
     * Clean JPA lazy proxies and replace them by <code>null</code>.
     * 
     * @param property
     *            the property to clean
     */
    private void cleanDatatype(DatatypeProperty property) {
        NabuccoProperty newProperty = property.createProperty(null);
        newProperty.getParent().setProperty(newProperty);
    }

    /**
     * Cleans JPA collection implementations and replaces them by {@link java.util.Collection}
     * instances.
     * 
     * @param property
     *            the list property to clean
     */
    private void cleanList(CollectionProperty property) {
        NabuccoCollectionAccessor.getInstance().detachCollection(property.getInstance());
    }

}
