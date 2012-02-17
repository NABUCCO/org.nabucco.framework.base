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
package org.nabucco.framework.base.facade.datatype.exceptionmsg.field;

/**
 * FieldMessageCodeType
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public enum FieldMessageCodeType {

    /**
     * The field is null.
     */
    FIELD_NULL,

    /**
     * The field is not null but empty.
     */
    FIELD_EMPTY,

    /**
     * The field is too long.
     */
    FIELD_TOO_LONG,

    /**
     * The field is too short.
     */
    FIELD_TOO_SHORT,

    /**
     * The field is not valid.
     */
    FIELD_INVALID,

    /**
     * The field is out of range.
     */
    FIELD_OUT_OF_RANGE,

    /**
     * The field minimum is exceeded.
     */
    FIELD_MIN_EXCEEDED,

    /**
     * The field maximum is exceeded.
     */
    FIELD_MAX_EXCEEDED,

    /**
     * The collection is empty.
     */
    COLLECTION_EMPTY;

}
