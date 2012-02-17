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
package org.nabucco.framework.base.impl.service.aspect.resolving;

import org.nabucco.framework.base.facade.datatype.Identifier;
import org.nabucco.framework.base.facade.datatype.code.Code;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoList;
import org.nabucco.framework.base.facade.datatype.security.User;
import org.nabucco.framework.base.facade.datatype.security.UserId;
import org.nabucco.framework.base.facade.exception.service.ResolveException;
import org.nabucco.framework.base.facade.exception.service.SearchException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.facade.message.authorization.UserRq;
import org.nabucco.framework.base.facade.message.authorization.UserRs;
import org.nabucco.framework.base.facade.message.context.ServiceMessageContext;
import org.nabucco.framework.base.facade.message.dynamiccode.DynamicCodeIdRq;
import org.nabucco.framework.base.facade.message.dynamiccode.DynamicCodeRs;
import org.nabucco.framework.base.facade.service.authorization.AuthorizationService;
import org.nabucco.framework.base.facade.service.authorization.AuthorizationServiceLocator;
import org.nabucco.framework.base.facade.service.dynamiccode.DynamicCodeService;
import org.nabucco.framework.base.facade.service.dynamiccode.DynamicCodeServiceLocator;

/**
 * ResolvingSupport
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public abstract class ResolvingSupport {

    /** The service context for workflow connection. */
    private ServiceMessageContext context;

    /**
     * Setter for the service message context.
     * 
     * @param context
     *            the context
     */
    public final void setContext(ServiceMessageContext context) {
        this.context = context;
    }

    /**
     * Getter for the context.
     * 
     * @return Returns the context.
     */
    public final ServiceMessageContext getContext() {
        return this.context;
    }

    /**
     * Resolve a dynamic code by its ID.
     * 
     * @param referenceId
     *            the reference ID of the dynamic code
     * 
     * @return the resolved dynamic code
     * 
     * @throws ResolvingException
     *             when the code with the given ID cannot be found
     */
    protected Code resolveCode(long referenceId) throws ResolvingException {

        DynamicCodeServiceLocator locator = new DynamicCodeServiceLocator();
        DynamicCodeService service = locator.getDynamicCodeService(null);

        DynamicCodeIdRq requestMessage = new DynamicCodeIdRq();
        requestMessage.setReferenceId(new Identifier(referenceId));

        ServiceRequest<DynamicCodeIdRq> rq = new ServiceRequest<DynamicCodeIdRq>(this.context);
        rq.setRequestMessage(requestMessage);

        try {
            ServiceResponse<DynamicCodeRs> rs = service.resolveDynamicCode(rq);

            if (rs == null || rs.getResponseMessage() == null) {
                throw new ResolvingException("Cannot resolve Dynamic Code with id '" + referenceId + "'.");
            }

            NabuccoList<Code> codes = rs.getResponseMessage().getCodeList();

            if (codes.isEmpty() || codes.size() > 1) {
                throw new ResolvingException("Cannot resolve Dynamic Code with id '" + referenceId + "'.");
            }

            return codes.first();

        } catch (ResolveException re) {
            throw new ResolvingException("Error resolving Dynamic Code with id '" + referenceId + "'.", re);
        }

    }

    /**
     * Resolve a user by its ID.
     * 
     * @param referenceId
     *            the reference ID of the user
     * 
     * @return the resolved user
     * 
     * @throws ResolvingException
     *             when the user with the given ID cannot be found
     */
    protected User resolveUser(long referenceId) throws ResolvingException {

        AuthorizationServiceLocator locator = new AuthorizationServiceLocator();
        AuthorizationService service = locator.getService(null);

        UserRq requestMessage = new UserRq();
        requestMessage.setId(new Identifier(referenceId));

        ServiceRequest<UserRq> rq = new ServiceRequest<UserRq>(this.context);
        rq.setRequestMessage(requestMessage);

        try {
            ServiceResponse<UserRs> rs = service.resolveUser(rq);

            if (rs == null || rs.getResponseMessage() == null) {
                throw new ResolvingException("Cannot resolve User with id '" + referenceId + "'.");
            }

            User user = rs.getResponseMessage().getUser();

            if (user == null) {
                throw new ResolvingException("Cannot resolve User with id '" + referenceId + "'.");
            }

            return user;

        } catch (SearchException se) {
            throw new ResolvingException("Error resolving User with id '" + referenceId + "'.", se);
        }
    }

    /**
     * Resolve a user by its ID.
     * 
     * @param userId
     *            the userId of the user
     * 
     * @return the resolved user
     * 
     * @throws ResolvingException
     *             when the user with the given userId cannot be found
     */
    protected User resolveUser(String userId) throws ResolvingException {

        AuthorizationServiceLocator locator = new AuthorizationServiceLocator();
        AuthorizationService service = locator.getService(null);

        UserRq requestMessage = new UserRq();
        requestMessage.setUserId(new UserId(userId));

        ServiceRequest<UserRq> rq = new ServiceRequest<UserRq>(this.context);
        rq.setRequestMessage(requestMessage);

        try {
            ServiceResponse<UserRs> rs = service.resolveUser(rq);

            if (rs == null || rs.getResponseMessage() == null) {
                throw new ResolvingException("Cannot resolve User with user-id '" + userId + "'.");
            }

            User user = rs.getResponseMessage().getUser();

            if (user == null) {
                throw new ResolvingException("Cannot resolve User with user-id '" + userId + "'.");
            }

            return user;

        } catch (SearchException se) {
            throw new ResolvingException("Error resolving User with user-id '" + userId + "'.", se);
        }
    }
}
