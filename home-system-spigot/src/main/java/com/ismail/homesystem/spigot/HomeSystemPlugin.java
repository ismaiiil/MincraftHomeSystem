package com.ismail.homesystem.spigot;

import com.ismail.homesystem.api.APIDummy;
import com.ismail.homesystem.api.mysql.daos.PlayerHouseDAO;
import com.ismail.homesystem.api.mysql.models.PlayerHouse;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import com.ismail.homesystem.common.Utils;

import java.util.List;


// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class HomeSystemPlugin extends JavaPlugin implements Listener {
    PlayerHouseDAO playerHouseDAO;
    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
        Utils test;
        test = new Utils();
        System.out.println(test.UtilDummy);
        APIDummy apitest = new APIDummy();
        System.out.println(apitest.APIString);

        playerHouseDAO = new PlayerHouseDAO();

    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.getPlayer().sendMessage(Component.text("Hello, " + event.getPlayer().getName() + "!"));
        var playerBlock = event.getPlayer().getLocation().getBlock();

        PlayerHouse playerHouse = new PlayerHouse("test", event.getPlayer().getUniqueId().toString(),
                "x:"+ playerBlock.getX() +
                          "y:"+ playerBlock.getY() +
                          "z:"+ playerBlock.getZ()
                );

        playerHouseDAO.savePlayerHouse(playerHouse);
        List< PlayerHouse > playerHouses = playerHouseDAO.getPlayerHouses();
        playerHouses.forEach(s -> System.out.println(s.getPlayerUUID() + s.getCoordinates()));

        //TODO Add "Name" to homes database
        //TODO Optimise initialisation and database creation to fix "hanging", need to run in another thread
        //TODO Add commands with Command API
        //TODO /sethome <name> get current player position and push to database
        //TODO /delhome <name> get the line from db and delete the line
        //TODO /home <name> teleport to specific home
        //TODO /listhomes list all homes in the chat (maybe do pagination as well?)

        //TODO commands optimisation
        //TODO need to add help for commands
        //TODO
    }

}