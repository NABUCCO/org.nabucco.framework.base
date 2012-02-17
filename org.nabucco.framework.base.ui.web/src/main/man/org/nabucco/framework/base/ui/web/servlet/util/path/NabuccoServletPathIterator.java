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

import java.util.Iterator;

/**
 * NabuccoServletPathIterator
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
class NabuccoServletPathIterator implements Iterator<NabuccoServletPathEntry> {

    private boolean isInitialized = false;

    /** The current path entry. */
    private NabuccoServletPathEntry current;

    /**
     * Creates a new {@link NabuccoServletPathIterator} instance.
     * 
     * @param root
     *            the root entry
     */
    NabuccoServletPathIterator(NabuccoServletPathEntry root) {
        if (root == null) {
            throw new IllegalArgumentException("Cannot create servlet path iterator for root element [null].");
        }

        this.current = root;
    }

    @Override
    public boolean hasNext() {
        if (!this.isInitialized) {
            return true;
        }
        return this.current.getNext() != null;
    }

    @Override
    public NabuccoServletPathEntry next() {
        if (!this.isInitialized) {
            this.isInitialized = true;
            return this.current;
        }
        return this.current = this.current.getNext();
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("Servlet Path must not be manipulated.");
    }

}
