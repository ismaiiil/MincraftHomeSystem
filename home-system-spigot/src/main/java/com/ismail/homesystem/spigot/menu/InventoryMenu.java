package com.ismail.homesystem.spigot.menu;

import com.ismail.homesystem.api.mysql.daos.PlayerHouseDAO;
import com.ismail.homesystem.api.mysql.models.PlayerHouse;
import com.ismail.homesystem.api.mysql.utils.ErrorCodes;
import com.ismail.homesystem.common.StringUtils;
import com.ismail.homesystem.spigot.HomeSystemPlugin;
import com.ismail.homesystem.spigot.commands.HomeCommand;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.bukkit.Bukkit.getWorld;
import static org.bukkit.plugin.java.JavaPlugin.getPlugin;

public class InventoryMenu implements  InventoryHolder {
    private final Inventory inventory;

    private List<PlayerHouse> playerHouseList = new ArrayList<>();

    public void teleportToHouse(int itemRawSlot, Player player){
        var home = playerHouseList.get(itemRawSlot);
        if(Objects.nonNull(home)){
            var formattedLocation = StringUtils.parseString(home.getCoordinates());
            assert formattedLocation != null;
            player.teleport(new Location(getWorld(formattedLocation.world()), formattedLocation.x(),formattedLocation.y(),formattedLocation.z()));
            player.sendPlainMessage("Teleported to " + home.getHomeName());
        }
    }

    public void deleteAllHouses(Player player){
        PlayerHouseDAO playerHouseDAO = new PlayerHouseDAO();;
        playerHouseDAO.getPlayerHouses(player.getUniqueId(), true).thenRun(()->{
            player.sendPlainMessage("All homes have been deleted");

            Bukkit.getScheduler().callSyncMethod(getPlugin(HomeSystemPlugin.class), ()->{
                player.getInventory().close();
                InventoryMenu myInventory = new InventoryMenu(getPlugin(HomeSystemPlugin.class), player);
                player.openInventory(myInventory.getInventory());
                return null;
            });


        }).exceptionally(throwable -> {
            ErrorCodes errorCode = ErrorCodes.getErrorCode(throwable.getCause().getMessage());
            if (errorCode == ErrorCodes.UNHANDLED_ERROR) {
                player.sendPlainMessage("Oops... something unexpected happened while deleting your home, please contact support!"
                        + "\n" + "Giving them this number might help: " + Bukkit.getServer().getCurrentTick());
                throwable.printStackTrace();
            }
            return null;
        });
    }

    public InventoryMenu(HomeSystemPlugin plugin, Player player) {
        Component inventoryTextComponent = Component.text("Your Homes" )
        .color(NamedTextColor.DARK_GREEN);

        this.inventory = plugin.getServer().createInventory(this, 54, inventoryTextComponent);

        ItemStack glassPane = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemStack redStoneBlock = new ItemStack(Material.REDSTONE_BLOCK);

        ItemMeta glassPaneMeta = glassPane.getItemMeta();
        glassPaneMeta.displayName(Component.empty());
        glassPane.setItemMeta(glassPaneMeta);

        for (int i = 45; i < 54; i++) {
            inventory.setItem(i, glassPane);
        }

        ItemMeta redstoneMeta = redStoneBlock.getItemMeta();
        redstoneMeta.displayName(Component.text("Delete all homes" )
                   .color(NamedTextColor.RED));
        redStoneBlock.setItemMeta(redstoneMeta);

        inventory.setItem(49, redStoneBlock);

        PlayerHouseDAO playerHouseDAO = new PlayerHouseDAO();
        playerHouseDAO.getPlayerHouses(player.getUniqueId(), false).thenAccept((playerHouses -> {
            playerHouseList = playerHouses;
            var fetchedHouses = playerHouses.size();
            if (playerHouses.size() > HomeCommand.MAX_HOMES) {
                fetchedHouses = HomeCommand.MAX_HOMES;
                player.sendPlainMessage("Consider deleting some homes as you have exceeded your quota");
            }

            for (int i = 0; i < fetchedHouses; i++) {
                Component mapTextName = Component.text(playerHouses.get(i).getHomeName())
                        .color(NamedTextColor.YELLOW);
                Component mapTextDescription = Component.text("Click to teleport to this home")
                        .color(NamedTextColor.GRAY);

                ItemStack mapItem = new ItemStack(Material.MAP);
                ItemMeta mapItemMeta = mapItem.getItemMeta();
                mapItemMeta.displayName(mapTextName);
                mapItem.setItemMeta(mapItemMeta);

                ArrayList<Component> mapTexts = new ArrayList<>();
                mapTexts.add(mapTextDescription);
                mapItem.lore(mapTexts);
                inventory.setItem(i, mapItem);
            }
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

    @Override
    public @NotNull Inventory getInventory() {
        return this.inventory;
    }


}
