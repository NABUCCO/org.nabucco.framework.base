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

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.nabucco.framework.base.facade.datatype.Identifier;
import org.nabucco.framework.base.facade.datatype.componentrelation.ComponentRelation;
import org.nabucco.framework.base.facade.datatype.componentrelation.ComponentRelationTypeMapper;
import org.nabucco.framework.base.facade.exception.service.SearchException;
import org.nabucco.framework.base.facade.message.componentrelation.ComponentRelationListMsg;
import org.nabucco.framework.base.facade.message.componentrelation.ComponentRelationSearchRq;
import org.nabucco.framework.base.impl.service.maintain.NabuccoQuery;

/**
 * SearchComponentRelationServiceHandlerImpl
 * 
 * @author Dominic Trumpfheller, PRODYNA AG
 * @author Nicolas Moser, PRODYNA AG
 */
public class SearchComponentRelationServiceHandlerImpl extends SearchComponentRelationServiceHandler {

    private static final long serialVersionUID = 1L;

    private static final String QUERY = "select cr from {0} cr"
            + " where (cr.type = :relationType or :relationType is null)";

    private static final Matcher QUERY_MATCHER = Pattern.compile("\\{0\\}").matcher(QUERY);

    @Override
    protected ComponentRelationListMsg searchComponentRelation(ComponentRelationSearchRq msg) throws SearchException {

        String entityName = msg.getComponentRelationClass().getValue();

        if (entityName == null || entityName.isEmpty()) {
            throw new SearchException("Cannot search for ComponentRelation class 'null'.");
        }

        // List of source ids, for compability reasons!
        List<Identifier> sourceIds = new ArrayList<Identifier>();
        if (msg.getSourceId() != null && msg.getSourceId().getValue() != null) {
            sourceIds.add(msg.getSourceId());
        }
        for (Identifier sourceId : msg.getSourceIdList()) {
            sourceIds.add(sourceId);
        }

        // List of target ids, for compability reasons!
        List<Identifier> targetIds = new ArrayList<Identifier>();
        if (msg.getTargetId() != null && msg.getTargetId().getValue() != null) {
            targetIds.add(msg.getTargetId());
        }
        for (Identifier targetId : msg.getTargetIdList()) {
            targetIds.add(targetId);
        }

        String queryString = QUERY_MATCHER.replaceFirst(entityName);
        if (!sourceIds.isEmpty()) {
            queryString = queryString + " and (cr.sourceId in (:sourceId))";
        }
        if (!targetIds.isEmpty()) {
            queryString = queryString + " and (cr.target.id in (:targetId))";
        }

        try {
            NabuccoQuery<ComponentRelation<?>> query = super.getPersistenceManager().createQuery(queryString);

            String relationType = ComponentRelationTypeMapper.getInstance().toString(msg.getRelationType());

            query.setParameter("relationType", relationType);

            if (!sourceIds.isEmpty()) {
                query.setParameter("sourceId", sourceIds);
            }
            if (!targetIds.isEmpty()) {
                List<Long> targetIdList = this.transformIdentifiersList(targetIds);
                query.setParameter("targetId", targetIdList);
            }

            List<ComponentRelation<?>> resultList = query.getResultList();

            ComponentRelationListMsg rsMsg = new ComponentRelationListMsg();
            rsMsg.getComponentRelationList().addAll(resultList);

            return rsMsg;

        } catch (Exception e) {
            throw new SearchException("Error searching for component relations with relation type '"
                    + msg.getRelationType() + "'!", e);
        }
    }

    /**
     * Converts a list of identifiers to the list of longs
     * 
     * @param list
     *            list of identifiers
     * 
     * @return list of longs
     */
    private List<Long> transformIdentifiersList(List<Identifier> list) {
        List<Long> retVal = new ArrayList<Long>();
        for (Identifier ident : list) {
            retVal.add(ident.getValue());
        }
        return retVal;
    }
}
