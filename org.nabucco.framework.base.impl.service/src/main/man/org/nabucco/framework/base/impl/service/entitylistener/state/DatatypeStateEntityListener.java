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
package org.nabucco.framework.base.impl.service.entitylistener.state;

import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.DatatypeState;
import org.nabucco.framework.base.impl.service.entitylistener.EntityListener;

/**
 * DatatypeStateEntityListener
 * <p/>
 * Set the state of a datatype to PERSISTENT when it is load from database.
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class DatatypeStateEntityListener implements EntityListener {

    private static final long serialVersionUID = 1L;

    @Override
    public void postLoad(Datatype datatype) {
        datatype.setDatatypeState(DatatypeState.PERSISTENT);
    }

    @Override
    public void prePersist(Datatype datatype) {
        // Nothing to do here!
    }

    @Override
    public void postPersist(Datatype datatype) {
        // Nothing to do here!
    }

    @Override
    public void preUpdate(Datatype datatype) {
        // Nothing to do here!
    }

    @Override
    public void postUpdate(Datatype datatype) {
        // Nothing to do here!
    }

    @Override
    public void preRemove(Datatype datatype) {
        // Nothing to do here!
    }

    @Override
    public void postRemove(Datatype datatype) {
        // Nothing to do here!
    }

}
