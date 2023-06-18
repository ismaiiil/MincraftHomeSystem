package com.ismail.homesystem.spigot;
import com.ismail.homesystem.api.mysql.utils.HibernateManager;
import com.ismail.homesystem.spigot.commands.HomeCommand;
import com.ismail.homesystem.spigot.menu.InventoryMenuListener;
import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPIBukkitConfig;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

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
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.getPlayer().sendMessage(Component.text("Plugin House System Initialised"));

        //TODO move json parsing in common module
        //TODO commands optimisation
        //TODO localisation of texts
        //TODO permissions?
    }

    @Override
    public void onDisable() {
        CommandAPI.onDisable();
        HibernateManager.closeAllSessions();
    }
}