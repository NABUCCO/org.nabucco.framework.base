/*

 * Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved. Created 02.06.2010

 */
package org.nabucco.framework.base.facade.datatype.utils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Queue;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;

/**
 * Localizes a given string.
 * 
 * @author Michael Krausse
 */
public class I18N {

    /** All handlers which are available for localizating a message. */
    private static Queue<ResourceBundle> messageHandlers;

    private static Set<String> messageBundleNames = new HashSet<String>();

    private static final String DEFAULT_LANGUAGE = "en";

    private static final String DEFAULT_COUNTRY = "US";

    private static final String BUNDLE_NAME_BASE = "org.nabucco.framework.base.facade.datatype.localization.MessagesBundle";

    private static Locale currentLocale = new Locale(DEFAULT_LANGUAGE, DEFAULT_COUNTRY);

    private static NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(I18N.class);
    
    /**
     * Private constructor.
     */
    private I18N() {
        throw new IllegalStateException("Private constructor must not be invoked.");
    }

    /**
     * Localize a given key, without parameter.
     * 
     * @param key
     *            key of the message to localize
     * 
     * @return localized message
     */
    public static String i18n(String key) {
        return i18n(key, new HashMap<String, String>());
    }

    /**
     * Localizes a given key.
     * <p/>
     * If key can not be resolved it will be returned with surrounding ?? (two questionmarks in
     * front, two behind).
     * 
     * @param key
     *            key of the message to localize
     * @param parameters
     *            parameters for message formatting
     * 
     * @return localized message
     */
    public static String i18n(final String key, Map<String, ? extends Serializable> parameters) {
        String result = "??";
        if (key != null) {
            try {
                result = result + key + "??";
                String plainMessage = null;
                initializeMessageBundles();
                int possibleBundles = messageHandlers.size();
                while (plainMessage == null && possibleBundles > 0) {
                    ResourceBundle currentBundle = messageHandlers.peek();
                    if (currentBundle.containsKey(key)) {
                        plainMessage = currentBundle.getString(key);
                    }
                    messageHandlers.offer(messageHandlers.poll());
                    possibleBundles = possibleBundles - 1;
                }
                if (plainMessage != null) {
                    result = MessageFormatter.format(plainMessage, parameters, currentLocale);
                }
            } catch (Exception e) {
                logger.error(e, "Key ", key, " cannot be translated.");
            }
        }
        return result;
    }

    /**
     * Checks whether localized messages should be reloaded and reloads them.
     */
    private static void initializeMessageBundles() {
        if (messageHandlers == null || messageHandlers.size() != messageBundleNames.size()) {
            addRessource(BUNDLE_NAME_BASE);
            messageHandlers = new ConcurrentLinkedQueue<ResourceBundle>();
            for (String ressourceName : messageBundleNames) {
                try {
                    messageHandlers.add(ResourceBundle.getBundle(ressourceName, currentLocale));
                } catch (MissingResourceException e) {
                    logger.error("Resource: " + ressourceName + " cannot be loaded");
                }
            }
        }
    }

    /**
     * Add a resourcebundle for localization.
     * 
     * @param ressourceName
     *            full qualified name of bundle
     */
    public static void addRessource(String ressourceName) {
        if (ressourceName != null) {
            messageBundleNames.add(ressourceName);
        }
    }

    /**
     * Remove resourename for localization. This resource will not be used for localization anymore
     * (it will be removed from lookupable resourcehandlers).
     * 
     * @param ressourceName
     *            full qualified name of the bundle
     */
    public static void remove(String ressourceName) {
        messageBundleNames.remove(ressourceName);
    }

    /**
     * Setter for the current locale.
     * 
     * @param currentLocale
     *            the current locale
     */
    public static void setCurrentLocale(Locale currentLocale) {
        I18N.currentLocale = currentLocale;
    }

}
