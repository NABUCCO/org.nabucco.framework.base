/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
 */
package org.nabucco.framework.base.facade.service.componentrelation;

import org.nabucco.framework.base.facade.exception.service.MaintainException;
import org.nabucco.framework.base.facade.exception.service.SearchException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.facade.message.componentrelation.ComponentRelationListMsg;
import org.nabucco.framework.base.facade.message.componentrelation.ComponentRelationMsg;
import org.nabucco.framework.base.facade.message.componentrelation.ComponentRelationSearchRq;
import org.nabucco.framework.base.facade.service.Service;

/**
 * 
 * ComponentRelationService
 * 
 * @author Dominic Trumpfheller, PRODYNA AG
 */
public interface ComponentRelationService extends Service {

    String JNDI_NAME = "components/org.nabucco.framework.base/org.nabucco.framework.base.facade.service.componentrelation.ComponentRelationService";

    /**
     * Missing description at method maintainComponentRelation.
     * 
     * @param rq
     *            the ServiceRequest<ComponentRelationMsg>.
     * @return the ServiceResponse<ComponentRelationMsg>.
     * @throws MaintainException
     */
    ServiceResponse<ComponentRelationMsg> maintainComponentRelation(
            ServiceRequest<ComponentRelationMsg> rq) throws MaintainException;

    /**
     * Missing description at method searchComponentRelation.
     * 
     * @param rq
     *            the ServiceRequest<ComponentRelationSearchRq>.
     * @return the ServiceResponse<ComponentRelationListMsg>.
     * @throws SearchException
     */
    ServiceResponse<ComponentRelationListMsg> searchComponentRelation(
            ServiceRequest<ComponentRelationSearchRq> rq) throws SearchException;
}
