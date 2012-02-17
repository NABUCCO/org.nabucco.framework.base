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
package org.nabucco.framework.base.facade.service.authorization;

import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.facade.message.authorization.GroupListRs;
import org.nabucco.framework.base.facade.message.authorization.PermissionListRs;
import org.nabucco.framework.base.facade.message.authorization.RoleListRs;
import org.nabucco.framework.base.facade.message.authorization.UserRq;
import org.nabucco.framework.base.facade.message.authorization.UserRs;
import org.nabucco.framework.base.facade.message.context.ServiceContextFactory;
import org.nabucco.framework.base.facade.service.authorization.AuthorizationService;

/**
 * AuthorizationServiceDefaultImpl<p/>Service for resolving users and permissions from authorization component.<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2011-04-18
 */
public class AuthorizationServiceDefaultImpl implements AuthorizationService {

    private static final long serialVersionUID = 1L;

    /** Constructs a new AuthorizationServiceDefaultImpl instance. */
    public AuthorizationServiceDefaultImpl() {
        super();
    }

    @Override
    public String[] getAspects(String operation) {
        return new String[0];
    }

    /**
     * ResolveUser.
     *
     * @param rq the ServiceRequest<UserRq>.
     * @return the ServiceResponse<UserRs>.
     */
    public ServiceResponse<UserRs> resolveUser(ServiceRequest<UserRq> rq) {
        ServiceResponse<UserRs> rs = new ServiceResponse<UserRs>(ServiceContextFactory.getInstance()
                .newServiceMessageContext());
        rs.setResponseMessage(new UserRs());
        return rs;
    }

    /**
     * Getter for the Groups.
     *
     * @param rq the ServiceRequest<UserRq>.
     * @return the ServiceResponse<GroupListRs>.
     */
    public ServiceResponse<GroupListRs> getGroups(ServiceRequest<UserRq> rq) {
        ServiceResponse<GroupListRs> rs = new ServiceResponse<GroupListRs>(ServiceContextFactory.getInstance()
                .newServiceMessageContext());
        rs.setResponseMessage(new GroupListRs());
        return rs;
    }

    /**
     * Getter for the Roles.
     *
     * @param rq the ServiceRequest<UserRq>.
     * @return the ServiceResponse<RoleListRs>.
     */
    public ServiceResponse<RoleListRs> getRoles(ServiceRequest<UserRq> rq) {
        ServiceResponse<RoleListRs> rs = new ServiceResponse<RoleListRs>(ServiceContextFactory.getInstance()
                .newServiceMessageContext());
        rs.setResponseMessage(new RoleListRs());
        return rs;
    }

    /**
     * Getter for the Permissions.
     *
     * @param rq the ServiceRequest<UserRq>.
     * @return the ServiceResponse<PermissionListRs>.
     */
    public ServiceResponse<PermissionListRs> getPermissions(ServiceRequest<UserRq> rq) {
        ServiceResponse<PermissionListRs> rs = new ServiceResponse<PermissionListRs>(ServiceContextFactory
                .getInstance().newServiceMessageContext());
        rs.setResponseMessage(new PermissionListRs());
        return rs;
    }

    @Override
    public final String getName() {
        return null;
    }
}
