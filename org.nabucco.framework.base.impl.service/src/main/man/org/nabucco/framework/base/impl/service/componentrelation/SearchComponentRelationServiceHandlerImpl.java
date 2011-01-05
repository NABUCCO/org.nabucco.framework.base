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
package org.nabucco.framework.base.impl.service.componentrelation;

import java.util.List;

import javax.persistence.Query;

import org.nabucco.framework.base.facade.datatype.componentrelation.ComponentRelation;
import org.nabucco.framework.base.facade.exception.service.SearchException;
import org.nabucco.framework.base.facade.message.componentrelation.ComponentRelationListMsg;
import org.nabucco.framework.base.facade.message.componentrelation.ComponentRelationSearchRq;

/**
 * SearchComponentRelationServiceHandlerImpl
 * 
 * @author Dominic Trumpfheller, PRODYNA AG
 */
public class SearchComponentRelationServiceHandlerImpl extends
        SearchComponentRelationServiceHandler {

    private static final long serialVersionUID = 1L;

    @Override
    protected ComponentRelationListMsg searchComponentRelation(ComponentRelationSearchRq msg)
            throws SearchException {

        String entityName = msg.getComponentRelationClass().getValue();
        String relationType = msg.getRelationType().getClass().getName()
                + "#" + msg.getRelationType().getId();

        StringBuilder queryString = new StringBuilder();
        queryString.append("SELECT cr FROM " + entityName + " cr");
        queryString.append(" WHERE cr.sourceId = :sourceId");
        queryString.append(" AND cr.type = :relationType");

        Query query = getEntityManager().createQuery(queryString.toString());
        query.setParameter("sourceId", msg.getSourceId());
        query.setParameter("relationType", relationType);

        @SuppressWarnings("unchecked")
        List<ComponentRelation<?>> resultList = query.getResultList();

        ComponentRelationListMsg rsMsg = new ComponentRelationListMsg();
        if (!resultList.isEmpty()) {
            rsMsg.getComponentRelationList().addAll(resultList);
        }

        return rsMsg;
    }

}
