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
package org.nabucco.framework.base.ui.web.model.editor.util.parser;

import org.nabucco.framework.base.facade.exception.NabuccoException;

/**
 * NabuccoParserException
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class ControlParserException extends NabuccoException {

    private static final long serialVersionUID = 1L;

    /**
     * 
     * Creates a new {@link ControlParserException} instance.
     * 
     * @param message
     * @param ex
     */
    public ControlParserException(String message, Exception ex) {
        super(message, ex);
    }

    /**
     * 
     * Creates a new {@link ControlParserException} instance.
     * 
     * @param message
     */
    public ControlParserException(String message) {
        super(message);
    }

}
