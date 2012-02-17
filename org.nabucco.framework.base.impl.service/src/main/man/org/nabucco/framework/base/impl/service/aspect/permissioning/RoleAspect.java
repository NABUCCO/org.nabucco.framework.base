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
package org.nabucco.framework.base.impl.service.aspect.permissioning;

import org.nabucco.framework.base.facade.datatype.extension.property.StringProperty;
import org.nabucco.framework.base.facade.datatype.extension.schema.property.PropertyExtension;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.nabucco.framework.base.facade.datatype.security.Role;
import org.nabucco.framework.base.facade.datatype.security.UserId;
import org.nabucco.framework.base.facade.exception.service.SearchException;
import org.nabucco.framework.base.facade.message.ServiceMessage;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.facade.message.authorization.RoleListRs;
import org.nabucco.framework.base.facade.message.authorization.UserRq;
import org.nabucco.framework.base.facade.service.authorization.AuthorizationService;
import org.nabucco.framework.base.facade.service.authorization.AuthorizationServiceLocator;

/**
 * RoleAspect
 * <p/>
 * Validate whether the current user has the configured role.
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public final class RoleAspect extends PermissioningAspectSupport implements PermissioningAspect {

    private String role;

    private NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(RoleAspect.class);

    @Override
    public void configure(PropertyExtension properties) {
        try {
            StringProperty role = (StringProperty) properties.getPropertyMap().get("ROLE");
            this.role = role.getValue().getValue();
        } catch (Exception e) {
            logger.warning("Property 'ROLE' not defined in RoleAspect '", this.getClass().getCanonicalName(), "'.");
        }
    }

    @Override
    public void permissionBefore(ServiceMessage requestMessage) throws PermissioningException {
        if (!this.hasRole()) {
            logger.warning("Access denied! Missing role [", this.role, "] to access the service.");
            throw new PermissioningException("Access denied! User has no role to access the service.");
        }
    }

    /**
     * Check whether the current user has the role to access the service.
     * 
     * @return <b>true</b> if the user has the role to access the service, <b>false</b> if not
     * 
     * @throws PermissioningException
     *             when an error during the search occurs
     */
    private boolean hasRole() throws PermissioningException {

        if (this.role == null) {
            return false;
        }

        if (super.getContext().getSubject() == null) {
            return false;
        }

        UserId userId = super.getContext().getSubject().getUserId();

        try {
            AuthorizationServiceLocator locator = new AuthorizationServiceLocator();
            AuthorizationService authorizationService = locator.getService(null);

            ServiceRequest<UserRq> rq = new ServiceRequest<UserRq>(super.getContext());

            UserRq msg = new UserRq();
            msg.setUserId(userId);
            rq.setRequestMessage(msg);

            ServiceResponse<RoleListRs> rs = authorizationService.getRoles(rq);

            if (rs == null || rs.getResponseMessage() == null) {
                return false;
            }

            for (Role role : rs.getResponseMessage().getRoles()) {
                if (role.getRolename().getValue().equals(this.role)) {
                    return true;
                }
            }

        } catch (SearchException se) {
            logger.error(se, "Error searching Roles for user [", userId, "].");
            throw new PermissioningException("Error searching Roles for user [" + userId + "].", se);
        } catch (Exception e) {
            logger.error(e, "Error searching Roles for user [", userId, "].");
            throw new PermissioningException("Error searching Roles for user [" + userId + "].", e);
        }

        return false;
    }
}
