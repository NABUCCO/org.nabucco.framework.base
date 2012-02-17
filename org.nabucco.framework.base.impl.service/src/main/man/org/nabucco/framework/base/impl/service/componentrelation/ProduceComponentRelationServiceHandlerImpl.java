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
package org.nabucco.framework.base.impl.service.componentrelation;

import org.nabucco.framework.base.facade.datatype.DatatypeState;
import org.nabucco.framework.base.facade.datatype.componentrelation.ComponentRelation;
import org.nabucco.framework.base.facade.exception.service.ProduceException;
import org.nabucco.framework.base.facade.message.componentrelation.ComponentRelationMsg;
import org.nabucco.framework.base.facade.message.componentrelation.ComponentRelationProduceRq;

/**
 * Produces a new component relation instance.
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class ProduceComponentRelationServiceHandlerImpl extends ProduceComponentRelationServiceHandler {

    private static final long serialVersionUID = 1L;

    @Override
    protected ComponentRelationMsg produceComponentRelation(ComponentRelationProduceRq msg) throws ProduceException {

        ComponentRelationMsg rs = new ComponentRelationMsg();

        String classname = msg.getType().getValue();
        ClassLoader classLoader = ProduceComponentRelationServiceHandlerImpl.class.getClassLoader();

        try {
            @SuppressWarnings("unchecked")
            Class<? extends ComponentRelation<?>> relationClass = (Class<? extends ComponentRelation<?>>) classLoader
                    .loadClass(classname);

            ComponentRelation<?> relation = relationClass.newInstance();

            relation.setDatatypeState(DatatypeState.INITIALIZED);
            relation.setRelationType(msg.getRelationType());
            relation.setSourceId(msg.getSourceId());
            relation.setTarget(msg.getTarget());

            rs.setComponentRelation(relation);

        } catch (ClassNotFoundException cnfe) {
            throw new ProduceException("Cannot find component relation class '" + classname + "'.", cnfe);
        } catch (InstantiationException ie) {
            throw new ProduceException("Cannot instantiate component relation '" + classname + "'.", ie);
        } catch (IllegalAccessException e) {
            throw new ProduceException("Cannot access constructor of component relation '" + classname + "'.", e);
        }

        return rs;
    }
}
