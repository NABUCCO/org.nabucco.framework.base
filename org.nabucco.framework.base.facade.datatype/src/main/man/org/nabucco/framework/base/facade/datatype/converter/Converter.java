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
package org.nabucco.framework.base.facade.datatype.converter;

/**
 * Converter
 * <p/>
 * Bidirectional converter between an object and its target representation.
 * 
 * @param <T>
 *            the converted object
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public interface Converter<T extends Object> {

    /**
     * Convert a string value in the given type.
     * 
     * @param value
     *            the string value to convert
     * 
     * @return the converted instance
     * 
     * @throws ConverterException
     *             when the conversion fails
     */
    T convert(String value) throws ConverterException;

    /**
     * Convert a given value into a string.
     * 
     * @param value
     *            the value to convert
     * 
     * @return the converted value
     * 
     * @throws ConverterException
     *             when the conversion fails
     */
    String convert(T value) throws ConverterException;

}
