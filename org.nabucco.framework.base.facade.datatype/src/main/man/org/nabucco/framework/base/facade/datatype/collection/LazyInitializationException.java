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
package org.nabucco.framework.base.facade.datatype.collection;

import java.text.MessageFormat;

/**
 * LazyInitializationException.
 * <p/>
 * This exception indicates that the list is manipulated without beeing fetched completely.
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class LazyInitializationException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private static final String MESSAGE = "Failed to lazily initialize a collection of type [{0}].";

    /**
     * Creates a new {@link LazyInitializationException} instance.
     * 
     * @param type
     *            type of the collection.
     */
    public LazyInitializationException(Class<?> type) {
        super(MessageFormat.format(MESSAGE, type.getCanonicalName()));
    }

}
