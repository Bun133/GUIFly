package com.github.bun133.guifly.gui.item

import com.github.bun133.guifly.value.Value
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryInteractEvent
import org.bukkit.inventory.ItemStack


class GUIItem(val x: Int, val y: Int, val item: Value<ItemStack>) {
    internal var click = mutableListOf<GUIItem.(InventoryClickEvent) -> Unit>()
    internal var shiftClick = mutableListOf<GUIItem.(InventoryClickEvent) -> Unit>()
    internal var pick = mutableListOf<GUIItem.(InventoryClickEvent) -> Unit>()
    internal var move = mutableListOf<GUIItem.(InventoryClickEvent) -> Unit>()
    internal var change = mutableListOf<GUIItem.(InventoryInteractEvent) -> Unit>()
    internal var isMarkUnMovable = false
}