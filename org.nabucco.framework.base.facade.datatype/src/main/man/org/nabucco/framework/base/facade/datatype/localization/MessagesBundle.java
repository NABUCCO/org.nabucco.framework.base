/*

 * Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved. Created 08.06.2010

 */
package org.nabucco.framework.base.facade.datatype.localization;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Allows access to localization properties.
 * 
 * @author Michael Krausse, PRODYNA AG
 */
public class MessagesBundle {

    private static final String BUNDLE_NAME = "org.nabucco.framework.base.facade.datatype.localization.MessagesBundle";

    public static ResourceBundle get(final Locale locale) {
        return ResourceBundle.getBundle(BUNDLE_NAME, locale);
    }
}
