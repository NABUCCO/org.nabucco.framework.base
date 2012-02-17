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
package org.nabucco.framework.base.test.security;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.nabucco.framework.base.facade.datatype.NabuccoSystem;
import org.nabucco.framework.base.facade.datatype.security.Subject;
import org.nabucco.framework.base.facade.datatype.security.User;

/**
 * SecurityTestUtil
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class SecurityTestUtil {

    /** Default User ID */
    public static final String DEFAULT_SUBJECT_USERID = "NBC123";

    /** Default Owner */
    public static final String DEFAULT_SUBJECT_OWNER = "NABUCCO";

    /** Default Tenant */
    private static final String DEFAULT_SUBJECT_TENANT = "NABUCCO";

    /**
     * Private constructor must not be invoked.
     */
    private SecurityTestUtil() {
    }

    /**
     * Creates a subject for <b>test-purposes</b>.
     * 
     * @return the new subject.
     */
    public static Subject createSubject() {
        return defaultSubject();
    }

    /**
     * Returns an identification with username and hostname.
     * 
     * @return the identification
     */
    public static String getIdentification() {
        return getUserId() + "@" + getHostName();
    }

    /**
     * Creates a default subject.
     * 
     * @return the default subject
     */
    private static Subject defaultSubject() {
        Subject subject = new Subject();
        subject.setUserId(getUserId());
        subject.setOwner(DEFAULT_SUBJECT_OWNER);
        subject.setTenant(DEFAULT_SUBJECT_TENANT);
        subject.setLoginTime(NabuccoSystem.getCurrentTimeMillis());

        User user = new User();
        user.setOwner(DEFAULT_SUBJECT_OWNER);
        user.setUsername(getUserId());
        subject.setUser(user);

        return subject;
    }

    /**
     * Returns the userid of the current user.
     * 
     * @return the user id
     */
    private static String getUserId() {
        return System.getProperty("user.name", DEFAULT_SUBJECT_USERID);
    }

    /**
     * Returns the hostname for this machine.
     * 
     * @return the host name
     */
    private static String getHostName() {
        String hostName = "localhost";
        try {
            hostName = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
        }
        return hostName;
    }

}
