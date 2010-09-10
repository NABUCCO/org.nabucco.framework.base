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
package org.nabucco.framework.base.test.security;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.nabucco.framework.base.facade.datatype.security.Subject;

/**
 * SecurityTestUtil
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class SecurityTestUtil {

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
        return getUserName() + "@" + getHostName();
    }

    /**
     * Creates a default subject.
     * 
     * @return the default subject
     */
    private static Subject defaultSubject() {
        Subject subject = new Subject();
        subject.setUserId(getUserName());
        return subject;
    }

    /** Returns the username. */
    private static String getUserName() {
        return System.getProperty("user.name");
    }

    /** Returns the hostname for this machine. */
    private static String getHostName() {
        String hostName = "localhost";
        try {
            hostName = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
        }
        return hostName;
    }

}
