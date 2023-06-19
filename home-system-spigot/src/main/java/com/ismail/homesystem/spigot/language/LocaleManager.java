package com.ismail.homesystem.spigot.language;

import java.util.Locale;
import java.util.ResourceBundle;

public class LocaleManager {
    private static final String BASE_NAME = "messages";
    private static final Locale DEFAULT_LOCALE = Locale.US;

    public static ResourceBundle getResourceBundle(Locale locale) {
        Locale targetLocale = (locale != null) ? locale : DEFAULT_LOCALE;
        return ResourceBundle.getBundle(BASE_NAME, targetLocale);
    }
}