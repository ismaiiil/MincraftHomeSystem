package com.ismail.homesystem.spigot;
import com.ismail.homesystem.api.mysql.daos.PlayerHouseDAO;
import com.ismail.homesystem.api.mysql.models.PlayerHouse;
import com.ismail.homesystem.api.mysql.utils.HibernateManager;
import com.ismail.homesystem.spigot.commands.HomeCommand;
import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPIBukkitConfig;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;
import java.util.logging.Logger;


// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
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

        if (!HibernateManager.initHibernate()){
            logger.severe("Hibernate database could not be initialised");
        }else{
            logger.info("Hibernate database initialised");
        }

        CommandAPI.registerCommand(HomeCommand.class);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        PlayerHouseDAO playerHouseDAO = new PlayerHouseDAO();
        event.getPlayer().sendMessage(Component.text("Plugin House System Initialised"));
        final var playerBlock = event.getPlayer().getLocation().getBlock();
        String houseName =  UUID.randomUUID().toString().substring(0, 8);
        final PlayerHouse playerHouse = new PlayerHouse(houseName, event.getPlayer().getUniqueId().toString(),
                "x:"+ playerBlock.getX() +
                          "y:"+ playerBlock.getY() +
                          "z:"+ playerBlock.getZ()
                );

        playerHouseDAO.savePlayerHouse(playerHouse).thenRun(()->{
            logger.info("Pushed Data on DB");
        });
        playerHouseDAO.getPlayerHouses().thenAccept((playerHouses)->{
            for (PlayerHouse house : playerHouses) {
                logger.info(house.toString());
            }
        });

        //TODO /sethome <name> get current player position and push to database
        //TODO /delhome <name> get the line from db and delete the line
        //TODO /home <name> teleport to specific home
        //TODO /listhomes list all homes in the chat (maybe do pagination as well?)

        //TODO move json parsing in common module

        //TODO commands optimisation
        //TODO need to add help for commands
    }

    @Override
    public void onDisable() {
        CommandAPI.onDisable();
        HibernateManager.closeAllSessions();
    }
}