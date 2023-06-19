package com.ismail.homesystem.spigot.language;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import net.kyori.adventure.text.TranslatableComponent;
import net.kyori.adventure.translation.GlobalTranslator;
import net.kyori.adventure.translation.TranslationRegistry;
import org.bukkit.entity.Player;

import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import static org.bukkit.Bukkit.getLogger;

public class TranslationManager {
    GlobalTranslator globalTranslator;
    Logger logger = getLogger();
    private TranslationRegistry translationRegistry;

    public void loadTranslations(Locale locale) {
        translationRegistry = TranslationRegistry.create(Key.key("home_system", "main"));

        globalTranslator = GlobalTranslator.translator();
        ResourceBundle bundle;
        if (locale.equals(Locale.ENGLISH)) {
            bundle = loadResourceBundle("message");
        }
        else if (locale.equals(Locale.FRANCE)) {
            bundle = loadResourceBundle("message_fr_FR");
        } else {
            logger.severe("Default locale loaded as the one specified doesn't exist in the resources");
            bundle = loadResourceBundle("message");
        }

        if (bundle != null) {
            registerTranslations(bundle, locale);
        }

        globalTranslator.addSource(translationRegistry);
    }

    private void registerTranslations(ResourceBundle resourceBundle, Locale locale) {
        for (String key : resourceBundle.keySet()) {
            String translation = resourceBundle.getString(key);
            translationRegistry.register(key, locale, new MessageFormat(translation));
        }
    }

    private ResourceBundle loadResourceBundle(String baseName) {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(baseName + ".properties")) {
            if (inputStream != null) {
                return new PropertyResourceBundle(inputStream);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
//    public static Component renderComponent(TranslatableComponent translatableComponent, Player player, String... placeholders){
//        return GlobalTranslator.render(translatableComponent, player.locale());
//    }


    public static Component renderComponent(TranslatableComponent translatableComponent, Player player, String... placeholders){
        Component component =  GlobalTranslator.render(translatableComponent, player.locale());
        if (placeholders.length > 0){
            component = component.replaceText(builder -> {
                for (int i = 0; i < placeholders.length; i++) {
                    String placeholder = "{" + (i + 1) + "}";
                    builder.matchLiteral(placeholder).replacement(placeholders[i]);
                }
            });
        }
        return component;
    }


}