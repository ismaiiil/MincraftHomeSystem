package com.ismail.homesystem.spigot.commands;

import com.ismail.homesystem.api.mysql.daos.PlayerHouseDAO;
import com.ismail.homesystem.api.mysql.models.PlayerHouse;
import com.ismail.homesystem.api.mysql.utils.ErrorCodes;
import com.ismail.homesystem.common.StringUtils;
import com.ismail.homesystem.spigot.HomeSystemPlugin;
import com.ismail.homesystem.spigot.menu.InventoryMenu;
import dev.jorel.commandapi.annotations.Command;
import dev.jorel.commandapi.annotations.Default;
import dev.jorel.commandapi.annotations.Permission;
import dev.jorel.commandapi.annotations.Subcommand;
import dev.jorel.commandapi.annotations.arguments.AStringArgument;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.logging.Logger;

import static org.bukkit.Bukkit.getLogger;
import static org.bukkit.Bukkit.getWorld;
import static org.bukkit.plugin.java.JavaPlugin.getPlugin;

@Command("home")
public class HomeCommand {
    public static final int MAX_HOMES = 45;
    static Logger logger = getLogger();
    @Default
    public static void home(CommandSender sender) {
        sender.sendPlainMessage("This normally opens the GUI, " +
                "but since this command \"/home\" was not ran from the minecraft client, " +
                "there is nothing to show here, you can try \"/home /help\" to see the list of other commands");
    }

    @Default
    public static void home(Player player) {
        InventoryMenu myInventory = new InventoryMenu(getPlugin(HomeSystemPlugin.class), player);
        player.openInventory(myInventory.getInventory());
    }

    @Subcommand("help")
    public static void help(CommandSender sender){
        sender.sendMessage(Component.text(String.format("------ %s help ------", "Home System" ))
                .color(NamedTextColor.YELLOW));
        sender.sendPlainMessage("/home - Shows all you homes in a GUI");
        sender.sendPlainMessage("/home help - Show this help");
        sender.sendPlainMessage("/home set <name> - creates a home at your current location with the name specifed");
        sender.sendPlainMessage("/home tp <name> - teleports to the home with name");
        sender.sendPlainMessage("/home delete <name> - deletes the home with name");
    }

    @Subcommand("set")
    public static void setHome(Player player, @AStringArgument String name) {
        final var playerBlock = player.getLocation().getBlock();
        final StringUtils.FormattedLocation formattedLocation = new StringUtils.FormattedLocation(playerBlock.getWorld().getName(), playerBlock.getX(), playerBlock.getY(), playerBlock.getZ());
        final PlayerHouse playerHouse = new PlayerHouse(name, player.getUniqueId(), StringUtils.generateLocationString(formattedLocation)
        );

        PlayerHouseDAO playerHouseDAO = new PlayerHouseDAO();
        player.sendPlainMessage("Saving your home: "+ name + "...");
        playerHouseDAO.getPlayerHouses(player.getUniqueId(), false).thenAccept((playerHouses -> {

            if (playerHouses.size() >= MAX_HOMES){
                player.sendPlainMessage("You have exceeded your max quota of homes: "+ MAX_HOMES + ", and cannot create anymore");
                return;
            }

            playerHouseDAO.savePlayerHouse(playerHouse).thenRun(()->{
                player.sendPlainMessage("Your home location has been saved");
            }).exceptionally(throwable -> {
                ErrorCodes errorCode = ErrorCodes.getErrorCode(throwable.getCause().getMessage());

                if (errorCode == ErrorCodes.CONSTRAINT_VIOLATION) {
                    player.sendPlainMessage("Could not create home, you already have a home named: " + name);
                } else if (errorCode == ErrorCodes.UNHANDLED_ERROR) {
                    player.sendPlainMessage("Oops... something unexpected happened while creating your home, please contact support!"
                            + "\n" + "Giving them this number might help: " + Bukkit.getServer().getCurrentTick());
                    throwable.printStackTrace();
                }
                return null;
            });
        })).exceptionally(throwable -> {
            ErrorCodes errorCode = ErrorCodes.getErrorCode(throwable.getCause().getMessage());
            if (errorCode == ErrorCodes.UNHANDLED_ERROR) {
                player.sendPlainMessage("Oops... something unexpected happened while creating your home, please contact support!"
                        + "\n" + "Giving them this number might help: " + Bukkit.getServer().getCurrentTick());
                throwable.printStackTrace();
            }
            return null;
        });


    }

    @Subcommand("tp")
    public static void tp(Player player, @AStringArgument String name) {
        PlayerHouseDAO playerHouseDAO = new PlayerHouseDAO();
        player.sendPlainMessage("You have chosen to teleport to: " + name);
        playerHouseDAO.findByID(player.getUniqueId(), name, false).thenAccept((playerHouse)->{
            var formattedLocation = StringUtils.parseString(playerHouse.getCoordinates());
            assert formattedLocation != null;
            Bukkit.getScheduler().callSyncMethod(getPlugin(HomeSystemPlugin.class), ()->{
                player.teleport(new Location(getWorld(formattedLocation.world()), formattedLocation.x(),formattedLocation.y(),formattedLocation.z()));
                player.sendPlainMessage("Teleported to " + name);
                return null;
            });
        }).exceptionally(throwable -> {
            ErrorCodes errorCode = ErrorCodes.getErrorCode(throwable.getCause().getMessage());
            if (errorCode == ErrorCodes.NOT_FOUND) {
                player.sendPlainMessage("Could not teleport to: " + name + ", this home does not exist");
            } else if (errorCode == ErrorCodes.UNHANDLED_ERROR) {
                player.sendPlainMessage("Oops... something unexpected happened while teleporting to your home, please contact support!"
                        + "\n" + "Giving them this number might help: " + Bukkit.getServer().getCurrentTick());
                throwable.printStackTrace();
            }
            return null;
        });
    }


    @Subcommand("delete")
    public static void delete(Player player, @AStringArgument String name) {
        PlayerHouseDAO playerHouseDAO = new PlayerHouseDAO();

        player.sendPlainMessage("Deleting home: " + name);
        playerHouseDAO.findByID(player.getUniqueId(), name, true).thenRun(()->{
            player.sendPlainMessage(name + " has been deleted");
        }).exceptionally(throwable -> {
            ErrorCodes errorCode = ErrorCodes.getErrorCode(throwable.getCause().getMessage());
            if (errorCode == ErrorCodes.NOT_FOUND) {
                player.sendPlainMessage("Could not delete " + name + ", this home does not exist");
            } else if (errorCode == ErrorCodes.UNHANDLED_ERROR) {
                player.sendPlainMessage("Oops... something unexpected happened while deleting your home, please contact support!"
                        + "\n" + "Giving them this number might help: " + Bukkit.getServer().getCurrentTick());
                throwable.printStackTrace();
            }
            return null;

        });
    }



}
