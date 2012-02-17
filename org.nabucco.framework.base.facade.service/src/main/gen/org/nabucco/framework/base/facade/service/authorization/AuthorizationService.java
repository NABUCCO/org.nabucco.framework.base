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

import org.nabucco.framework.base.facade.exception.service.SearchException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.facade.message.authorization.GroupListRs;
import org.nabucco.framework.base.facade.message.authorization.PermissionListRs;
import org.nabucco.framework.base.facade.message.authorization.RoleListRs;
import org.nabucco.framework.base.facade.message.authorization.UserRq;
import org.nabucco.framework.base.facade.message.authorization.UserRs;
import org.nabucco.framework.base.facade.service.Service;

/**
 * AuthorizationService<p/>Service for resolving users and permissions from authorization component.<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2011-04-18
 */
public interface AuthorizationService extends Service {

    /**
     * Resolve the user for the given UserId.
     *
     * @param rq the ServiceRequest<UserRq>.
     * @return the ServiceResponse<UserRs>.
     * @throws SearchException
     */
    ServiceResponse<UserRs> resolveUser(ServiceRequest<UserRq> rq) throws SearchException;

    /**
     * Loads all permissions of the user for the given UserId.
     *
     * @param rq the ServiceRequest<UserRq>.
     * @return the ServiceResponse<GroupListRs>.
     * @throws SearchException
     */
    ServiceResponse<GroupListRs> getGroups(ServiceRequest<UserRq> rq) throws SearchException;

    /**
     * Loads all permissions of the user for the given UserId.
     *
     * @param rq the ServiceRequest<UserRq>.
     * @return the ServiceResponse<RoleListRs>.
     * @throws SearchException
     */
    ServiceResponse<RoleListRs> getRoles(ServiceRequest<UserRq> rq) throws SearchException;

    /**
     * Loads all permissions of the user for the given UserId.
     *
     * @param rq the ServiceRequest<UserRq>.
     * @return the ServiceResponse<PermissionListRs>.
     * @throws SearchException
     */
    ServiceResponse<PermissionListRs> getPermissions(ServiceRequest<UserRq> rq) throws SearchException;
}
