package com.github.bun133.guifly.gui.item

import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryInteractEvent
import org.bukkit.inventory.ItemStack


class GUIItem(val x: Int, val y: Int, val item: ItemStack) {
    internal var click = mutableListOf<(InventoryClickEvent) -> Unit>()
    internal var shiftClick = mutableListOf<(InventoryClickEvent) -> Unit>()
    internal var pick = mutableListOf<(InventoryClickEvent) -> Unit>()
    internal var move = mutableListOf<(InventoryClickEvent) -> Unit>()
    internal var change = mutableListOf<(InventoryInteractEvent) -> Unit>()
    internal var isMarkUnMovable = false
//    internal var swap = mutableListOf<(InventoryClickEvent) -> Unit>()
}