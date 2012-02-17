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
package org.nabucco.framework.base.facade.datatype.utils;

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * I18N
 * <p/>
 * Message localisation.
 * 
 * @author Michael Krausse
 */
public class I18N {

    /* Static Fields */

    private static boolean initialized = false;

    private static Locale locale = Locale.US;

    private static Queue<MessageBundle> bundleQueue = new PriorityQueue<MessageBundle>();

    /* Constants */

    private static final String MISSING = "??";

    private static final int DEFAULT_PRIORITY = 0;

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
        return i18n(key, Collections.<String, String> emptyMap());
    }

    /**
     * Localizes a given key.
     * <p/>
     * If key can not be resolved it will be returned with surrounding by ??.
     * 
     * @param key
     *            key of the message to localize
     * @param parameters
     *            parameters for message formatting
     * 
     * @return localized message
     */
    public static String i18n(String key, Map<String, ? extends Serializable> parameters) {
        if (key == null) {
            return MISSING;
        }

        if (!initialized) {
            initializeMessageBundles();
        }

        Queue<MessageBundle> bundles = new LinkedList<MessageBundle>(I18N.bundleQueue);

        while (bundles.peek() != null) {
            MessageBundle bundle = bundles.poll();

            String plainMessage = bundle.localize(key);

            if (plainMessage != null) {
                return MessageFormatter.format(plainMessage, parameters, locale);
            }
        }

        return MISSING + key + MISSING;
    }

    /**
     * Checks whether localized messages should be reloaded and reloads them if not.
     */
    private static void initializeMessageBundles() {
        for (MessageBundle bundle : bundleQueue) {
            bundle.init(locale);
        }

        initialized = true;
    }

    /**
     * Add a resourcebundle for localization with DEFAULT_PRIORITY = 0.
     * 
     * @param ressourceName
     *            full qualified name of bundle
     */
    public static void addRessource(String ressourceName) {
        I18N.addRessource(ressourceName, DEFAULT_PRIORITY);
    }

    /**
     * Add a resourcebundle for localization.
     * 
     * @param ressourceName
     *            full qualified name of bundle
     * @param priority
     *            the resource priority, the higher the priority, the more probable is the use of
     *            this bundle
     */
    public static void addRessource(String ressourceName, int priority) {
        if (ressourceName != null) {
            bundleQueue.offer(new MessageBundle(ressourceName, priority));
        }
    }

    /**
     * Remove the resource from the localization bundles.
     * 
     * @param resourceName
     *            full qualified name of the bundle
     */
    public static void remove(String resourceName) {
        MessageBundle removedBundle = null;
        for (MessageBundle bundle : bundleQueue) {
            if (bundle.getName().equalsIgnoreCase(resourceName)) {
                removedBundle = bundle;
            }
        }

        if (removedBundle != null) {
            bundleQueue.remove(removedBundle);
        }
    }

    /**
     * Setter for the current locale.
     * 
     * @param currentLocale
     *            the current locale
     */
    public static void setLocale(Locale currentLocale) {
        locale = currentLocale;
        initialized = false;
    }

}
