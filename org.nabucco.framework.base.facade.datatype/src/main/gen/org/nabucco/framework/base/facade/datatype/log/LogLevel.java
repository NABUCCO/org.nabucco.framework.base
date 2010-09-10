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
*
* Generated with NABUCCO Generator 
*/
package org.nabucco.framework.base.facade.datatype.log;

import org.nabucco.framework.base.facade.datatype.Enumeration;

/**
 * LogLevel<p/>TODO<p/>
 *
 * @version 1.0
 * @author Lasse Asbach, PRODYNA AG, 2010-07-22
 */
public enum LogLevel implements Enumeration {
    FATAL("F"), ERROR("E"), WARNING("W"), INFO("I"), DEBUG("D"), TRACE("T"), ;

    private String id;

    /**
     * Constructs a new LogLevel instance.
     *
     * @param id the String.
     */
    LogLevel(String id) {
        this.id = id;
    }

    @Override
    public Enumeration cloneObject() {
        return this;
    }

    @Override
    public int getOrdinal() {
        return this.ordinal();
    }

    @Override
    public String getId() {
        return this.id;
    }
}
