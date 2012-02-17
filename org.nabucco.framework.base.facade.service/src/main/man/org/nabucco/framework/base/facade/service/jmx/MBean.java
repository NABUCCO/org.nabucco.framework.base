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
package org.nabucco.framework.base.facade.service.jmx;

/**
 * MBean
 * <p/>
 * Common interface for all NABUCCO management beans.
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public interface MBean {

    /** The MBean constant for <b>type</b>. */
    final String TYPE = "type";

    /** The MBean constant for <b>name</b>. */
    final String NAME = "name";

    /** The MBean constant for <b>service</b>. */
    final String SERVICE = "service";

}
