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
package org.nabucco.framework.base.ui.web.servlet.util.path;

import java.util.EnumMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * NabuccoServletPathParser
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class NabuccoServletPath implements Iterable<NabuccoServletPathEntry> {

    /** The servlet mapping */
    private final String servletMapping;

    /** The servlet path */
    private final String servletPath;

    /** The root servlet path entry */
    private final NabuccoServletPathEntry root;

    /** The path delimiter. */
    private static final String DELIMITER = "/";

    /**
     * Creates a new {@link NabuccoServletPath} instance.
     * 
     * @param servletMapping
     *            name of the servlet mapping
     * @param path
     *            the servlet path
     */
    public NabuccoServletPath(String servletMapping, String path) {
        if (servletMapping == null || servletMapping.isEmpty()) {
            throw new IllegalArgumentException("Cannot create servlet path for mapping [null].");
        }

        this.servletMapping = servletMapping.substring(1);
        this.servletPath = path;

        this.root = new NabuccoServletPathEntry(this.servletMapping);

        if (this.servletPath != null) {
            this.parsePath();
        }
    }

    /**
     * Initialize the servlet path parser.
     */
    private void parsePath() {
        StringTokenizer tokenizer = new StringTokenizer(this.servletPath, DELIMITER);

        NabuccoServletPathEntry entry = this.root;

        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();
            entry = new NabuccoServletPathEntry(token, entry);
        }

    }

    /**
     * Resolve the path entry for the given name.
     * 
     * @param name
     *            the path entry name
     * 
     * @return the path entry with the given name, or null if the entry does not exist
     */
    public NabuccoServletPathEntry get(String name) {
        for (NabuccoServletPathEntry entry : this) {
            if (entry.getValue().equals(name)) {
                return entry;
            }
        }

        return null;
    }

    /**
     * Getter for the complete path as string.
     * 
     * @return Returns the complete servlet path.
     */
    public String getPath() {
        StringBuilder result = new StringBuilder();
        result.append(this.servletMapping);
        if (this.servletPath != null) {
            result.append(this.servletPath);
        }
        return result.toString();
    }

    /**
     * Getter for the root.
     * 
     * @return Returns the root.
     */
    public NabuccoServletPathEntry getRootEntry() {
        return this.root;
    }

    /**
     * Getter for the entry with the given type.
     * 
     * @param type
     *            the entry type to resolve
     * 
     * @return the entry with the given type, or null
     */
    public NabuccoServletPathEntry getEntry(NabuccoServletPathType type) {
        for (NabuccoServletPathEntry entry : this) {
            if (entry.getType() == type) {
                return entry;
            }
        }

        return null;
    }

    /**
     * Resolves the id of an path entry type.
     * 
     * @param type
     *            the type to resolve
     * 
     * @return the id
     */
    public String getId(NabuccoServletPathType type) {

        NabuccoServletPathEntry entry = this.getEntry(type);

        if (entry == null || entry.getNext() == null) {
            return null;
        }

        if (entry.getNext().getType() != NabuccoServletPathType.ID) {
            return null;
        }

        return entry.getNext().getValue();
    }

    /**
     * Converts the servlet path to a parameter map.
     * 
     * @return the parameter map
     */
    public Map<NabuccoServletPathType, String> toMap() {
        Map<NabuccoServletPathType, String> parameterMap = new EnumMap<NabuccoServletPathType, String>(
                NabuccoServletPathType.class);

        for (NabuccoServletPathEntry entry : this) {
            NabuccoServletPathType key = entry.getType();

            // Skip IDs
            if (key == NabuccoServletPathType.ID) {
                continue;
            }

            String value = null;
            if (entry.getNext() != null && entry.getNext().getType() == NabuccoServletPathType.ID) {
                value = entry.getNext().getValue();
            }

            parameterMap.put(key, value);
        }

        return parameterMap;
    }

    @Override
    public Iterator<NabuccoServletPathEntry> iterator() {
        return new NabuccoServletPathIterator(this.root);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("ServletPath:\n");

        result.append(this.root.toString());

        return result.toString();
    }

}
