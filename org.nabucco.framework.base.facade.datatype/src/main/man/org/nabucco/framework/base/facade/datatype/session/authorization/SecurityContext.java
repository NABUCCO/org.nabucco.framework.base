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
package org.nabucco.framework.base.facade.datatype.session.authorization;

import java.util.ArrayList;
import java.util.List;

import org.nabucco.framework.base.facade.datatype.security.Group;
import org.nabucco.framework.base.facade.datatype.security.Permission;
import org.nabucco.framework.base.facade.datatype.security.Role;
import org.nabucco.framework.base.facade.datatype.security.Subject;

/**
 * SecurityContext
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class SecurityContext {

    private Subject subject;

    private List<Group> groupList;

    private List<Role> roleList;

    private List<Permission> permissionList;

    /**
     * Getter for the authorization subject.
     * 
     * @return the subject
     */
    public Subject getSubject() {
        return subject;
    }

    /**
     * Setter for the authorization subject.
     * 
     * @param subject
     *            the subject to set
     */
    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    /**
     * Checks whether the current user is authenticated or not.
     * 
     * @return <b>true</b> if the user is authenticated, <b>false</b> if not.
     */
    public boolean isAuthenticated() {
        if (this.subject == null) {
            return false;
        }
        if (this.subject.getUser() == null) {
            return false;
        }
        if (this.subject.getToken() == null) {
            return false;
        }

        return true;
    }

    /**
     * Checks whether the group is given or not.
     * 
     * @param name
     *            the group name
     * 
     * @return <b>true</b> if the group is given, <b>false</b> if not.
     */
    public boolean containsGroup(String groupName) {
        for (Group group : this.getGroupList()) {
            if (group.getGroupname().getValue().equals(groupName)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Checks whether the role is given or not.
     * 
     * @param name
     *            the role name
     * 
     * @return <b>true</b> if the role is given, <b>false</b> if not.
     */
    public boolean containsRole(String roleName) {
        for (Role role : this.getRoleList()) {
            if (role.getRolename().getValue().equals(roleName)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Checks whether the permission is given or not.
     * 
     * @param name
     *            the permission name
     * 
     * @return <b>true</b> if the permission is given, <b>false</b> if not.
     */
    public boolean containsPermission(String permissionName) {
        for (Permission permission : this.getPermissionList()) {
            if (permission.getPermissionname().getValue().equals(permissionName)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Getter for the groupList.
     * 
     * @return Returns the groupList.
     */
    public List<Group> getGroupList() {
        if (this.groupList == null) {
            this.groupList = new ArrayList<Group>();
        }
        return this.groupList;
    }

    /**
     * Getter for the roleList.
     * 
     * @return Returns the roleList.
     */
    public List<Role> getRoleList() {
        if (this.roleList == null) {
            this.roleList = new ArrayList<Role>();
        }
        return this.roleList;
    }

    /**
     * Getter for the permissionList.
     * 
     * @return Returns the permissionList.
     */
    public List<Permission> getPermissionList() {
        if (this.permissionList == null) {
            this.permissionList = new ArrayList<Permission>();
        }
        return this.permissionList;
    }

}
