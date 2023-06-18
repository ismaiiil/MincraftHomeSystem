package com.ismail.homesystem.spigot.commands;

import com.ismail.homesystem.api.mysql.daos.PlayerHouseDAO;
import com.ismail.homesystem.api.mysql.models.PlayerHouse;
import com.ismail.homesystem.api.mysql.utils.ErrorCodes;
import dev.jorel.commandapi.annotations.Command;
import dev.jorel.commandapi.annotations.Default;
import dev.jorel.commandapi.annotations.Permission;
import dev.jorel.commandapi.annotations.Subcommand;
import dev.jorel.commandapi.annotations.arguments.AStringArgument;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Command("home")
public class HomeCommand {

    @Default
    public static void home(CommandSender sender) {
        sender.sendPlainMessage("Opening the GUI, you may use \"/home help\" for details on other commands");
    }
    @Subcommand("set")
    public static void setHome(Player player, @AStringArgument String name) {
        final var playerBlock = player.getLocation().getBlock();
        final PlayerHouse playerHouse = new PlayerHouse(name, player.getUniqueId().toString(),
                "x:"+ playerBlock.getX() +
                        "y:"+ playerBlock.getY() +
                        "z:"+ playerBlock.getZ()
        );

        player.sendPlainMessage("Saving your home: "+ name + "...");
        PlayerHouseDAO playerHouseDAO = new PlayerHouseDAO();
        playerHouseDAO.savePlayerHouse(playerHouse).thenRun(()->{
            player.sendPlainMessage("Your home location has been saved");
        }).exceptionally(throwable -> {
            ErrorCodes errorCode = ErrorCodes.getErrorCode(throwable);
            if (errorCode == ErrorCodes.CONSTRAINT_VIOLATION) {
                player.sendPlainMessage("Could not create home, you already have a home named: " + name);
            } else if (errorCode == ErrorCodes.UNKNOWN_ERROR) {
                player.sendPlainMessage("Oops... something unexpected happened while creating your home, please contact support!"
                        + "\n" + "Giving them this number might help: " + Bukkit.getServer().getCurrentTick());
            }
            return null;
        });
    }

}
