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

import org.nabucco.framework.base.facade.datatype.componentrelation.ComponentRelation;
import org.nabucco.framework.base.facade.exception.service.MaintainException;
import org.nabucco.framework.base.facade.message.componentrelation.ComponentRelationMsg;

/**
 * MaintainComponentRelationServiceHandlerImpl
 * 
 * @author Dominic Trumpfheller, PRODYNA AG
 */
public class MaintainComponentRelationServiceHandlerImpl extends MaintainComponentRelationServiceHandler {

    private static final long serialVersionUID = 1L;

    @Override
    protected ComponentRelationMsg maintainComponentRelation(ComponentRelationMsg msg) throws MaintainException {

        ComponentRelation<?> componentRelation = msg.getComponentRelation();

        try {
            componentRelation = super.getPersistenceManager().persist(componentRelation);
        } catch (Exception pe) {
            throw new MaintainException("Error maintaining Component Relation.", pe);
        }

        ComponentRelationMsg response = new ComponentRelationMsg();
        response.setComponentRelation(componentRelation);
        return response;
    }

}
