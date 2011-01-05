/*
 * Copyright 2010 PRODYNA AG
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
package org.nabucco.framework.base.impl.service.maintain;

import java.util.List;

import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.DatatypeState;
import org.nabucco.framework.base.facade.datatype.NType;
import org.nabucco.framework.base.facade.datatype.NabuccoDatatype;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoCollectionAccessor;
import org.nabucco.framework.base.facade.datatype.property.ListProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.PropertyType;
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
        this.cleanDatatype(datatype);

        this.resetDatatypeState(datatype);

        super.visit(datatype);
    }

    /**
     * Replaces lazy initialized persistence provider specific collections by empty
     * {@link java.util.Collection} implementations.
     * 
     * @param datatype
     *            the datatype to clean
     */
    private void cleanDatatype(Datatype datatype) {
        List<NabuccoProperty<?>> properties = datatype.getProperties();
        if (properties == null) {
            return;
        }

        for (NabuccoProperty<?> property : properties) {
            if (property.getPropertyType() == PropertyType.LIST) {
                this.cleanList((ListProperty<?>) property);
            }
        }
    }

    /**
     * Cleans JPA collection implementations and replaces them by {@link java.util.Collection}
     * instances.
     * 
     * @param property
     *            the list property to clean
     */
    private <T extends NType> void cleanList(ListProperty<T> property) {
        NabuccoCollectionAccessor.getInstance().detachCollection(property.getInstance());
    }

    /**
     * Reset the datatype state from constructed to persistent in order to set datatypes loaded from
     * DB to persistent.
     * 
     * @param datatype
     *            the datatype to reset
     */
    private void resetDatatypeState(Datatype datatype) {
        if (datatype.getDatatypeState() == DatatypeState.CONSTRUCTED) {
            if (datatype instanceof NabuccoDatatype) {
                if (((NabuccoDatatype) datatype).getId() != null) {
                    datatype.setDatatypeState(DatatypeState.PERSISTENT);
                }
            }
        }
    }

}
