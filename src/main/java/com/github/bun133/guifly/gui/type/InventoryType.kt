package com.github.bun133.guifly.gui.type

import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder

enum class InventoryType(
    val size: Int,
    val type: org.bukkit.event.inventory.InventoryType,
    val indexConverter: IndexConverter
) {
    CHEST_3(27, org.bukkit.event.inventory.InventoryType.CHEST, chest3IndexConverter),
    CHEST_4(36, org.bukkit.event.inventory.InventoryType.CHEST, chest4IndexConverter),
    CHEST_5(45, org.bukkit.event.inventory.InventoryType.CHEST, chest5IndexConverter),
    CHEST_6(54, org.bukkit.event.inventory.InventoryType.CHEST, chest6IndexConverter),
    DISPENSER(9, org.bukkit.event.inventory.InventoryType.DISPENSER, square3Converter),
    DROPPER(9, org.bukkit.event.inventory.InventoryType.DROPPER, square3Converter),
    FURNACE(3, org.bukkit.event.inventory.InventoryType.FURNACE, furnaceIndexConverter),
    WORKBENCH(10, org.bukkit.event.inventory.InventoryType.WORKBENCH, workbenchIndexConverter),
    CRAFTING(5, org.bukkit.event.inventory.InventoryType.CRAFTING, craftingIndexConverter),
    ENCHANTING(2, org.bukkit.event.inventory.InventoryType.ENCHANTING, enchantingIndexConverter),
    BREWING(5, org.bukkit.event.inventory.InventoryType.BREWING, brewingIndexConverter),
    PLAYER(41, org.bukkit.event.inventory.InventoryType.PLAYER, playerIndexConverter),
    CREATIVE(9, org.bukkit.event.inventory.InventoryType.CREATIVE, chestIndexConverter),
    MERCHANT(3, org.bukkit.event.inventory.InventoryType.MERCHANT, merchantIndexConverter),
    ENDER_CHEST(27, org.bukkit.event.inventory.InventoryType.ENDER_CHEST, chest3IndexConverter),
    ANVIL(3, org.bukkit.event.inventory.InventoryType.ANVIL, anvilIndexConverter),
    SMITHING(3, org.bukkit.event.inventory.InventoryType.SMITHING, smithIndexConverter),
    BEACON(1, org.bukkit.event.inventory.InventoryType.BEACON, beaconIndexConverter),
    HOPPER(5, org.bukkit.event.inventory.InventoryType.HOPPER, hopperIndexConverter),
    SHULKER_BOX(27, org.bukkit.event.inventory.InventoryType.SHULKER_BOX, chest3IndexConverter),
    BARREL(27, org.bukkit.event.inventory.InventoryType.BARREL, chest3IndexConverter),
    BLAST_FURNACE(3, org.bukkit.event.inventory.InventoryType.BLAST_FURNACE,furnaceIndexConverter),
    LECTERN(1, org.bukkit.event.inventory.InventoryType.LECTERN, lecternIndexConverter),
    SMOKER(3, org.bukkit.event.inventory.InventoryType.SMOKER, furnaceIndexConverter),
    LOOM(4, org.bukkit.event.inventory.InventoryType.LOOM,loomIndexConverter),
    CARTOGRAPHY(3, org.bukkit.event.inventory.InventoryType.CARTOGRAPHY, furnaceIndexConverter),
    GRINDSTONE(3, org.bukkit.event.inventory.InventoryType.GRINDSTONE,furnaceIndexConverter),
    STONECUTTER(2, org.bukkit.event.inventory.InventoryType.STONECUTTER,stoneCutterIndexConverter);

    fun createInventory(holder: InventoryHolder?, title: Component): Inventory {
        return Bukkit.getServer().createInventory(holder, this.type, title)
    }
}