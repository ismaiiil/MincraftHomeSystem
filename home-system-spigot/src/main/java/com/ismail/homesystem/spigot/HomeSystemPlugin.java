package com.ismail.homesystem.spigot;
import com.ismail.homesystem.api.mysql.utils.HibernateManager;
import com.ismail.homesystem.spigot.commands.HomeCommand;
import com.ismail.homesystem.spigot.language.LocaleManager;
import com.ismail.homesystem.spigot.menu.InventoryMenuListener;
import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPIBukkitConfig;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TranslatableComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.translation.GlobalTranslator;
import net.kyori.adventure.translation.TranslationRegistry;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class HomeSystemPlugin extends JavaPlugin implements Listener {
    Logger logger = getLogger();

    @Override
    public void onLoad() {
        CommandAPI.onLoad(new CommandAPIBukkitConfig(this).verboseOutput(true));
    }

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
        CommandAPI.onEnable();

        Bukkit.getPluginManager().registerEvents(new InventoryMenuListener(), this);

        if (!HibernateManager.initHibernate()){
            logger.severe("Hibernate database could not be initialised");
        }else{
            logger.info("Hibernate database initialised");
        }

        CommandAPI.registerCommand(HomeCommand.class);

        GlobalTranslator globalTranslator = GlobalTranslator.translator();

        Component inventoryTextComponent = Component.text("Your Homes" )
                .color(NamedTextColor.DARK_GREEN);

        TranslationRegistry translationRegistry = TranslationRegistry.create(Key.key("translationtest", "main"));

        translationRegistry.register("abc.myKey", Locale.US, new MessageFormat("This is a test."));
        translationRegistry.register("abc.myKey", Locale.FRANCE, new MessageFormat("C'est un test."));
        translationRegistry.register("abc.myKey", Locale.GERMANY, new MessageFormat("ich bin test"));

        globalTranslator.addSource(translationRegistry);


    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        //note to self, locales don't work in onPlayerJoin
        //observed with player.locale() not being set
        //https://discord.com/channels/289587909051416579/555462289851940864/1070459148103323698
        //to observe locale changes use the events related to settings changes? not sure need to test

//        ResourceBundle bundle = LocaleManager.getResourceBundle();
        /*TODO
           - code refactoring (moving string utils to common)
           - permissions, has been set auto
           - text localization
           - test clean jenkins deployment
         */


    }

    @Override
    public void onDisable() {
        CommandAPI.onDisable();
        HibernateManager.closeAllSessions();
    }
}