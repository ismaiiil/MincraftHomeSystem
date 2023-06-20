package com.ismail.homesystem.spigot.commands;

import com.ismail.homesystem.api.mysql.daos.PlayerHouseDAO;
import com.ismail.homesystem.api.mysql.models.PlayerHouse;
import com.ismail.homesystem.api.mysql.utils.ErrorCodes;
import com.ismail.homesystem.common.StringUtils;
import com.ismail.homesystem.spigot.HomeSystemPlugin;
import com.ismail.homesystem.spigot.menu.InventoryMenu;
import dev.jorel.commandapi.annotations.Command;
import dev.jorel.commandapi.annotations.Default;
import dev.jorel.commandapi.annotations.Subcommand;
import dev.jorel.commandapi.annotations.arguments.AStringArgument;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.logging.Logger;

import static com.ismail.homesystem.spigot.language.TranslationManager.getTranslatedComponent;
import static org.bukkit.Bukkit.getLogger;
import static org.bukkit.Bukkit.getWorld;
import static org.bukkit.plugin.java.JavaPlugin.getPlugin;

@Command("home")
public class HomeCommand {
    public static final int MAX_HOMES = 45;
    static Logger logger = getLogger();
    @Default
    public static void home(CommandSender sender) {
        sender.sendMessage(getTranslatedComponent("home.default.serversender",null));
    }

    @Default
    public static void home(Player player) {
        InventoryMenu myInventory = new InventoryMenu(getPlugin(HomeSystemPlugin.class), player);
        player.openInventory(myInventory.getInventory());
    }
    @Subcommand("help")
    public static void help(CommandSender sender){
        sender.sendMessage(getTranslatedComponent("home.help.text",null));
    }
    @Subcommand("help")
    public static void help(Player player){
        player.sendMessage(getTranslatedComponent("home.help.text",player).color(NamedTextColor.YELLOW));
    }

    @Subcommand("set")
    public static void setHome(Player player, @AStringArgument String name) {
        final var playerBlock = player.getLocation().getBlock();
        final StringUtils.FormattedLocation formattedLocation = new StringUtils.FormattedLocation(playerBlock.getWorld().getName(), playerBlock.getX(), playerBlock.getY(), playerBlock.getZ());
        final PlayerHouse playerHouse = new PlayerHouse(name, player.getUniqueId(), StringUtils.generateLocationString(formattedLocation)
        );

        PlayerHouseDAO playerHouseDAO = new PlayerHouseDAO();
        player.sendMessage(getTranslatedComponent("home.set.saving",player, playerHouse.getHomeName()).color(NamedTextColor.YELLOW));
        playerHouseDAO.getPlayerHouses(player.getUniqueId(), false).thenAccept((playerHouses -> {

            if (playerHouses.size() >= MAX_HOMES){
                player.sendMessage(getTranslatedComponent("home.set.warning_max",player,Integer.toString(MAX_HOMES))
                        .color(NamedTextColor.YELLOW));
                return;
            }

            playerHouseDAO.savePlayerHouse(playerHouse).thenRun(()->{
                player.sendMessage(getTranslatedComponent("home.set.saved",player, playerHouse.getHomeName())
                        .color(NamedTextColor.GREEN));
            }).exceptionally(throwable -> {
                ErrorCodes errorCode = ErrorCodes.getErrorCode(throwable.getCause().getMessage());

                if (errorCode == ErrorCodes.CONSTRAINT_VIOLATION) {
                    player.sendMessage(getTranslatedComponent("home.set.conflict",player, playerHouse.getHomeName())
                            .color(NamedTextColor.RED));
                } else if (errorCode == ErrorCodes.UNHANDLED_ERROR) {
                    sendGenericError(player, throwable);
                }
                return null;
            });
        })).exceptionally(throwable -> {
            ErrorCodes errorCode = ErrorCodes.getErrorCode(throwable.getCause().getMessage());
            if (errorCode == ErrorCodes.UNHANDLED_ERROR) {
                sendGenericError(player, throwable);
            }
            return null;
        });


    }

    @Subcommand("tp")
    public static void tp(Player player, @AStringArgument String name) {
        PlayerHouseDAO playerHouseDAO = new PlayerHouseDAO();
        player.sendMessage(getTranslatedComponent("home.teleport.loading",player,name)
                .color(NamedTextColor.YELLOW));
        playerHouseDAO.findByID(player.getUniqueId(), name, false).thenAccept((playerHouse)->{
            var formattedLocation = StringUtils.parseString(playerHouse.getCoordinates());
            assert formattedLocation != null;
            Bukkit.getScheduler().callSyncMethod(getPlugin(HomeSystemPlugin.class), ()->{
                player.teleport(new Location(getWorld(formattedLocation.world()), formattedLocation.x(),formattedLocation.y(),formattedLocation.z()));
                player.sendMessage(getTranslatedComponent("home.teleport.success",player,name)
                        .color(NamedTextColor.GREEN));
                return null;
            });
        }).exceptionally(throwable -> {
            ErrorCodes errorCode = ErrorCodes.getErrorCode(throwable.getCause().getMessage());
            if (errorCode == ErrorCodes.NOT_FOUND) {
                player.sendMessage(getTranslatedComponent("home.teleport.notfound",player,name)
                        .color(NamedTextColor.RED));
            } else if (errorCode == ErrorCodes.UNHANDLED_ERROR) {
                sendGenericError(player, throwable);
            }
            return null;
        });
    }


    @Subcommand("delete")
    public static void delete(Player player, @AStringArgument String name) {
        PlayerHouseDAO playerHouseDAO = new PlayerHouseDAO();

        player.sendPlainMessage("Deleting home: " + name);
        playerHouseDAO.findByID(player.getUniqueId(), name, true).thenRun(()->{
            player.sendMessage(getTranslatedComponent("home.delete.success.single",player,name)
                    .color(NamedTextColor.DARK_GREEN));
        }).exceptionally(throwable -> {
            ErrorCodes errorCode = ErrorCodes.getErrorCode(throwable.getCause().getMessage());
            if (errorCode == ErrorCodes.NOT_FOUND) {
                player.sendMessage(getTranslatedComponent("home.delete.notfound",player,name)
                        .color(NamedTextColor.RED));
            } else if (errorCode == ErrorCodes.UNHANDLED_ERROR) {
                sendGenericError(player, throwable);
            }
            return null;

        });
    }

    public static void sendGenericError(Player player, Throwable throwable) {
        player.sendMessage(getTranslatedComponent("home.generic.fail", player,Integer.toString(Bukkit.getServer().getCurrentTick()))
                .color(NamedTextColor.RED));
        throwable.printStackTrace();
    }


}
