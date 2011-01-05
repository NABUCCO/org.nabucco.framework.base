/*
 * Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved. Created 14.12.2010
 */
package org.nabucco.framework.base.facade.datatype.utils;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;

/**
 * MessageBundle
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
class MessageBundle implements Comparable<MessageBundle> {

    private final String name;

    private int priority;

    private ResourceBundle bundle;

    private static NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(I18N.class);

    /**
     * Creates a new {@link MessageBundle} instance.
     */
    MessageBundle(String name, int priority) {
        if (name == null) {
            throw new IllegalArgumentException("Cannot create MessageBundle for name [null].");
        }

        this.name = name;
        this.priority = priority;
    }

    /**
     * Init the message bundle for the given locale.
     * 
     * @param locale
     *            the local to resolve the messages for
     */
    void init(Locale locale) {
        if (locale == null) {
            throw new IllegalArgumentException("Cannot init MessageBundle for locale [null].");
        }

        try {
            this.bundle = ResourceBundle.getBundle(this.name, locale);
        } catch (MissingResourceException e) {
            logger.error(e, "Resource: " + this.name + " cannot be loaded.");
        }
    }

    /**
     * Getter for the name.
     * 
     * @return Returns the name.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Getter for the priority.
     * 
     * @return Returns the priority.
     */
    public Integer getPriority() {
        return this.priority;
    }

    /**
     * Getter for the bundle.
     * 
     * @return Returns the bundle.
     */
    ResourceBundle getBundle() {
        return this.bundle;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.name == null) ? 0 : this.name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        MessageBundle other = (MessageBundle) obj;
        if (this.name == null) {
            if (other.name != null)
                return false;
        } else if (!this.name.equals(other.name))
            return false;
        return true;
    }

    @Override
    public int compareTo(MessageBundle other) {
        if (other == null) {
            return -1;
        }

        return -this.getPriority().compareTo(other.getPriority());
    }

    /**
     * Localizes a key in this bundle.
     * 
     * @param key
     *            the key to localize
     * 
     * @return the localized message
     */
    public String localize(String key) {
        if (this.isInitialized() && this.bundle.containsKey(key)) {
            return this.bundle.getString(key);
        }
        return null;
    }

    /**
     * Checks whether the bundle is initialized or not.
     * 
     * @return <b>true</b> if the bundle is initialized, <b>false</b> if not
     */
    private boolean isInitialized() {
        return this.bundle != null;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("Bundle (");
        result.append(this.getName());
        result.append("): ");

        if (this.isInitialized()) {
            result.append("INITIALIZED");
        } else {
            result.append("INVALID");
        }
        
        return result.toString();
    }
}
