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
package org.nabucco.framework.base.facade.datatype;

import java.util.UUID;

import org.nabucco.framework.base.facade.datatype.date.Timestamp;
import org.nabucco.framework.base.facade.datatype.extension.ExtensionResolver;

/**
 * The NABUCCO System class.
 * 
 * @author Frank Ratschinski, PRODYNA AG
 */
public final class NabuccoSystem {

    /** The time shift for . */
    private static long timeShift = 0L;

    /** The NABUCCO Extension Point resolver. */
    private static ExtensionResolver extensionResolver;

    /**
     * Retrieves a timestamp holding the current time.
     * 
     * @return the difference, measured in milliseconds, between the current time and midnight,
     *         January 1, 1970 UTC.
     */
    public static final Timestamp getCurrentTimestamp() {
        return new Timestamp(getCurrentTimeMillis());
    }

    /**
     * Retrieves the current time in milliseconds.
     * 
     * @return the difference, measured in milliseconds, between the current time and midnight,
     *         January 1, 1970 UTC.
     */
    public static final long getCurrentTimeMillis() {
        return System.currentTimeMillis() - timeShift;
    }

    /**
     * Getter for the extension resolver. Extension point bottstrap is defined in
     * /extensions/org.nabucco.bootstrap/NabuccoBootstrap.xml.
     * 
     * @return the extension resolver holding all parsed extension points
     */
    public static synchronized ExtensionResolver getExtensionResolver() {
        if (extensionResolver == null) {
            extensionResolver = new ExtensionResolver();
        }

        return extensionResolver;
    }

    /**
     * Creates a new random UUID.
     * 
     * @return The string representation of the 128 bit uuid as <MSB><LSB>
     */
    public static String createUUID() {

        UUID uuid = UUID.randomUUID();
        long msb = uuid.getMostSignificantBits();
        long lsb = uuid.getLeastSignificantBits();

        StringBuilder sb = new StringBuilder();
        sb.append(Math.abs(msb)).append(Math.abs(lsb));
        return sb.toString();
    }
}
