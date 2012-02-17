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
package org.nabucco.framework.base.facade.datatype.extension;

import org.nabucco.common.extension.ExtensionException;
import org.nabucco.framework.base.facade.datatype.NabuccoSystem;

/**
 * Management Bean for JMX Extension Configuration.
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class ExtensionManager {

    private static final String MESSAGE_NO_EXTENSION = "No Extension found for Extension Point.";

    private static final String MESSAGE_WRONG_ID = "Extension Point ID is not valid.";

    /**
     * Getter for the size of configured extension points.
     * 
     * @return the size of bootstrap extensions
     */
    public int getSize() {

        int size = 0;

        for (ExtensionPointType point : ExtensionPointType.values()) {
            try {
                size += NabuccoSystem.getExtensionResolver().resolveExtensions(point).getExtensionNames().length;
            } catch (Exception e) {
                // Shallow exception!
            }
        }

        return size;
    }

    /**
     * Reset the extension registry.
     */
    public void reset() {
        try {
            NabuccoSystem.getExtensionResolver().reset("nabucco");
        } catch (ExtensionException e) {
            // Shallow exception!
        }
    }

    /**
     * List the extensions for the given extension point.
     * 
     * @param point
     *            the extension point
     * 
     * @return the configured extensions
     */
    public String list(String point) {
        if (point == null || point.isEmpty()) {
            return MESSAGE_WRONG_ID;
        }

        ExtensionPointType pointType = ExtensionPointType.valueOfId(point);

        if (pointType == null) {
            return MESSAGE_WRONG_ID;
        }

        try {
            ExtensionMap extensions = NabuccoSystem.getExtensionResolver().resolveExtensions(pointType);

            if (extensions.getExtensionNames().length == 0) {
                return MESSAGE_NO_EXTENSION;
            }

            StringBuilder result = new StringBuilder();
            result.append(point);
            result.append(": \n");

            for (int i = 0; i < extensions.getExtensionNames().length; i++) {
                result.append(" - ");
                result.append(extensions.getExtensionNames()[i]);
                if (i < extensions.getExtensionNames().length) {
                    result.append('\n');
                }
            }

            return result.toString();

        } catch (Exception e) {
            // Shallow exception!
        }

        return MESSAGE_NO_EXTENSION;
    }

    /**
     * List the extensions for all extension points.
     * 
     * @param point
     *            the extension point
     * 
     * @return the configured extensions
     */
    public String listAll() {

        StringBuilder result = new StringBuilder();

        for (ExtensionPointType point : ExtensionPointType.values()) {

            try {
                ExtensionMap extensions = NabuccoSystem.getExtensionResolver().resolveExtensions(point);

                if (extensions.getExtensionNames().length == 0) {
                    continue;
                }

                result.append(point.getId());
                result.append(": \n");

                for (int i = 0; i < extensions.getExtensionNames().length; i++) {
                    result.append(" - ");
                    result.append(extensions.getExtensionNames()[i]);
                    if (i < extensions.getExtensionNames().length) {
                        result.append('\n');
                    }
                }

                result.append('\n');

            } catch (Exception e) {
                // Shallow exception!
            }
        }

        return result.toString();
    }

}
