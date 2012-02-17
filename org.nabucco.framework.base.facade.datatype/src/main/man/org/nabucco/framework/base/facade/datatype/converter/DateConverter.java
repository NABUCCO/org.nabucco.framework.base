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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.nabucco.framework.base.facade.datatype.NDate;
import org.nabucco.framework.base.facade.datatype.NLong;
import org.nabucco.framework.base.facade.datatype.NTime;

/**
 * DateConverter
 * <p/>
 * Wraps a DateFormat for a common date parsing.
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public final class DateConverter implements Converter<Date> {

    private static final String PATTERN_DEFAULT = "dd.MM.yyyy HH:mm:ss(S) Z";

    private static final String PATTERN_DATE = "dd.MM.yyyy";

    private static final String PATTERN_TIME = "HH:mm:ss";
    
    private static final String PATTERN_DATETIME = "dd.MM.yyyy HH:mm:ss";

    private final DateFormat defaultFormat;

    private final DateFormat dateFormat;

    private final DateFormat timeFormat;
    
    private final DateFormat dateTimeFormat;

    /**
     * Creates a new {@link DateConverter} instance.
     */
    DateConverter() {
        this.defaultFormat = new SimpleDateFormat(PATTERN_DEFAULT);
        this.dateFormat = new SimpleDateFormat(PATTERN_DATE);
        this.timeFormat = new SimpleDateFormat(PATTERN_TIME);
        this.dateTimeFormat = new SimpleDateFormat(PATTERN_DATETIME);
    }

    @Override
    public Date convert(String value) throws ConverterException {
        try {
            return this.defaultFormat.parse(value);
        } catch (java.text.ParseException pe) {
            throw new ConverterException("Cannot format value.", pe);
        }
    }

    @Override
    public String convert(Date date) {
        if (date == null) {
            return null;
        }
        return this.defaultFormat.format(date);
    }

    /**
     * Format the date to a string.
     * 
     * @param date
     *            the date to format
     * 
     * @return the formatted string
     */
    public String convertDate(Date date) {
        if (date == null) {
            return null;
        }
        return this.dateFormat.format(date);
    }

    /**
     * Convert the date string with pattern <code>dd.MM.yyyy</code> to a date instance.
     * 
     * @param value
     *            the date value as string
     * 
     * @return the parsed date instance
     * 
     * @throws ConverterException
     *             when the date cannot be parsed
     */
    public Date convertDate(String value) throws ConverterException {
        try {
            return this.dateFormat.parse(value);
        } catch (java.text.ParseException pe) {
            throw new ConverterException("Cannot format value.", pe);
        }
    }

    /**
     * Format the date to a string.
     * 
     * @param date
     *            the date to format
     * 
     * @return the formatted string
     */
    public String convertDate(NDate date) {
        if (date == null || date.getValue() == null) {
            return null;
        }
        return this.dateFormat.format(date.getValue());
    }

    /**
     * Format the time to a string.
     * 
     * @param time
     *            the time to format
     * 
     * @return the formatted string
     */
    public String convertTime(Date time) {
        if (time == null) {
            return null;
        }
        return this.timeFormat.format(time);
    }

    /**
     * Format the time to a string.
     * 
     * @param time
     *            the time to format
     * 
     * @return the formatted string
     */
    public String convertTime(NTime time) {
        if (time == null || time.getValue() == null) {
            return null;
        }
        return this.timeFormat.format(time.getValue());
    }

    /**
     * Convert the time string with pattern <code>HH:mm:ss</code> to a date instance.
     * 
     * @param value
     *            the time value as string
     * 
     * @return the parsed date instance
     * 
     * @throws ConverterException
     *             when the time cannot be parsed
     */
    public Date convertTime(String value) throws ConverterException {
        try {
            return this.timeFormat.parse(value);
        } catch (java.text.ParseException pe) {
            throw new ConverterException("Cannot format value '" + value + "'.", pe);
        }
    }

    /**
     * Format the time to a string.
     * 
     * @param dateTime
     *            the time to format
     * 
     * @return the formatted string
     */
    public String convertDateTime(Date dateTime) {
        if (dateTime == null) {
            return null;
        }
        return this.dateTimeFormat.format(dateTime);
    }

    /**
     * Format the time to a string.
     * 
     * @param dateTime
     *            the time to format
     * 
     * @return the formatted string
     */
    public String convertDateTime(NLong dateTime) {
        if (dateTime == null || dateTime.getValue() == null) {
            return null;
        }
        return this.dateTimeFormat.format(dateTime.getValue());
    }

    /**
     * Convert the time string with pattern <code>dd.MM.yyyy HH:mm:ss</code> to a date instance.
     * 
     * @param value
     *            the time value as string
     * 
     * @return the parsed date instance
     * 
     * @throws ConverterException
     *             when the time cannot be parsed
     */
    public Date convertDateTime(String value) throws ConverterException {
        try {
            return this.dateTimeFormat.parse(value);
        } catch (java.text.ParseException pe) {
            throw new ConverterException("Cannot format value '" + value + "'.", pe);
        }
    }
}
