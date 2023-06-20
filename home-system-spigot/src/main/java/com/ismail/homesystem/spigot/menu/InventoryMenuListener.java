package com.ismail.homesystem.spigot.menu;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import java.util.Objects;
import java.util.Set;

public class InventoryMenuListener implements Listener {
    @EventHandler
    public void onMenuClick(final InventoryClickEvent event){

        Inventory inventory = event.getClickedInventory();
        if (inventory == null || !(inventory.getHolder() instanceof InventoryMenu myInventoryMenu)) {
            return;
        }
        event.setCancelled(true);
        Player player = (Player) event.getWhoClicked();
        ItemStack clicked = event.getCurrentItem();
        // Check if the player clicked the stone.
        if (clicked != null && clicked.getType() == Material.MAP) {
            int rawSlot = event.getRawSlot();
            myInventoryMenu.teleportToHouse(rawSlot,player);
        }else if (clicked != null && clicked.getType() == Material.REDSTONE_BLOCK) {
            myInventoryMenu.deleteAllHouses(player);
        }
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {
        Set<Integer> rawSlots = event.getRawSlots();
        for (int slot : rawSlots) {
            if (isCustomInventorySlot(event.getView().getInventory(slot))) {
                event.setCancelled(true);
                return;
            }
        }
    }
    private boolean isCustomInventorySlot(Inventory inventory) {
        return  Objects.nonNull(inventory) && inventory.getHolder() instanceof InventoryMenu;
    }
}
