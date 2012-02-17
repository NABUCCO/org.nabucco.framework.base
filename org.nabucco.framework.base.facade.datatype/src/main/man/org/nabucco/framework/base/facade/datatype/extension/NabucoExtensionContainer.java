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

import org.nabucco.common.extension.NabuccoExtension;

/**
 * Adapter for NabuccoExtensions from common extensions to internal base Extensions.
 * 
 * @author Frank Ratschinski
 */
public class NabucoExtensionContainer implements NabuccoExtension {

    private NabuccoExtensionComposite extension;

    /**
     * Creates a new {@link NabucoExtensionContainer} instance.
     * 
     * @param extension
     *            the extension composite
     */
    public NabucoExtensionContainer(NabuccoExtensionComposite extension) {
        super();
        this.extension = extension;
    }

    /**
     * Getter for the extension composite.
     * 
     * @return the extension
     */
    public NabuccoExtensionComposite getExtension() {
        return extension;
    }

}
