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
import org.nabucco.framework.base.facade.datatype.security.Permission;
import org.nabucco.framework.base.facade.datatype.security.UserId;
import org.nabucco.framework.base.facade.exception.service.SearchException;
import org.nabucco.framework.base.facade.message.ServiceMessage;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.facade.message.authorization.PermissionListRs;
import org.nabucco.framework.base.facade.message.authorization.UserRq;
import org.nabucco.framework.base.facade.service.authorization.AuthorizationService;
import org.nabucco.framework.base.facade.service.authorization.AuthorizationServiceLocator;

/**
 * PermissionAspect
 * <p/>
 * Validate whether the current user has the configured permission.
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public final class PermissionAspect extends PermissioningAspectSupport implements PermissioningAspect {

    private String permission;

    private NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(PermissionAspect.class);

    @Override
    public void configure(PropertyExtension properties) {
        try {
            StringProperty permission = (StringProperty) properties.getPropertyMap().get("PERMISSION");
            this.permission = permission.getValue().getValue();
        } catch (Exception e) {
            logger.warning("Property 'PERMISSION' not defined in PermissionAspect '", this.getClass()
                    .getCanonicalName(), "'.");
        }
    }

    @Override
    public void permissionBefore(ServiceMessage requestMessage) throws PermissioningException {
        if (!this.hasPermission()) {
            logger.warning("Access denied! Missing permission [", this.permission, "] to access the service.");
            throw new PermissioningException("Access denied! User has no permission to access the service.");
        }
    }

    /**
     * Check whether the current user has the permission to access the service.
     * 
     * @return <b>true</b> if the user has the permission to access the service, <b>false</b> if not
     * 
     * @throws PermissioningException
     *             when an error during the search occurs
     */
    private boolean hasPermission() throws PermissioningException {

        if (this.permission == null) {
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

            ServiceResponse<PermissionListRs> rs = authorizationService.getPermissions(rq);

            if (rs == null || rs.getResponseMessage() == null) {
                return false;
            }

            for (Permission permission : rs.getResponseMessage().getPermissions()) {
                if (permission.getPermissionname().getValue().equals(this.permission)) {
                    return true;
                }
            }

        } catch (SearchException se) {
            logger.error(se, "Error searching Permissions for user [", userId, "].");
            throw new PermissioningException("Error searching Permissions for user [" + userId + "].", se);
        } catch (Exception e) {
            logger.error(e, "Error searching Permissions for user [", userId, "].");
            throw new PermissioningException("Error searching Permissions for user [" + userId + "].", e);
        }

        return false;
    }
}
