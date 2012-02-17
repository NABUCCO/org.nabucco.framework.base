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
package org.nabucco.framework.base.facade.component.handler;

/**
 * PostConstructHandler
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public abstract class PostConstructHandler extends LocatableHandlerSupport implements LocatableHandler {

    /** Default serial version UID. */
    private static final long serialVersionUID = 1L;

    /** The ID */
    private static final String ID = "PostConstructHandler";

    /**
     * Getter for the handler ID.
     * 
     * @return the id
     */
    public static final String getId() {
        return ID;
    }

    /**
     * Invoke the post construct handler.
     */
    public abstract void invoke();

}
