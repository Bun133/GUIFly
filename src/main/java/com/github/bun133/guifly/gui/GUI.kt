package com.github.bun133.guifly.gui

import com.github.bun133.guifly.gui.item.GUIItem
import com.github.bun133.guifly.gui.type.IndexConverter
import com.github.bun133.guifly.gui.type.InventoryType
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryAction
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCreativeEvent
import org.bukkit.event.inventory.InventoryDragEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder
import org.bukkit.plugin.java.JavaPlugin

open class GUI(
    private val indexConverter: IndexConverter,
    inventoryType: org.bukkit.event.inventory.InventoryType,
    size: Int,
    title: Component,
    holder: InventoryHolder?,
    plugin: JavaPlugin
) : Listener {
    constructor(type: InventoryType, title: Component, holder: InventoryHolder?, plugin: JavaPlugin) : this(
        type.indexConverter,
        type.type,
        type.size,
        title,
        null,
        plugin
    )

    val gui = if (inventoryType == org.bukkit.event.inventory.InventoryType.CHEST) {
        Bukkit.createInventory(holder, size, title)
    } else {
        Bukkit.createInventory(holder, inventoryType, title)
    }

    val listener = GUIListener(gui, plugin, { onClick(it) }, { onDrag(it) })

    protected val items = mutableMapOf<Pair<Int, Int>, GUIItem>()

    operator fun set(unit: Unit, item: GUIItem) {
        set(item)
    }

    /**
     * set item in this GUI
     */
    open fun set(vararg item: GUIItem) {
        item.forEach { e ->
            indexConverter.get(e.x to e.y)?.let {
                gui.setItem(it, e.item)
                items[e.x to e.y] = e
            }
        }
    }

    /**
     * open this GUI to player
     */
    fun open(p: Player) {
        p.openInventory(gui)
    }

    /// EVENTS
    private fun onClick(e: InventoryClickEvent) {
        println("onClick")
        if (e.clickedInventory != gui) return
        val en = items.toList().find { indexConverter.get(it.first) == e.rawSlot } ?: return
        val entry = en.second

        // List click event
        val click = if (
            e.action != InventoryAction.COLLECT_TO_CURSOR
        ) {
            entry.click.toList()
        } else {
            emptyList()
        }

        // List pick event
        val pick = if (
            e.action == InventoryAction.PICKUP_ALL ||
            e.action == InventoryAction.PICKUP_HALF ||
            e.action == InventoryAction.PICKUP_ONE ||
            e.action == InventoryAction.PICKUP_SOME
        ) {
            entry.pick.toList()
        } else {
            emptyList()
        }

        val move = if (
            e.action == InventoryAction.MOVE_TO_OTHER_INVENTORY ||
            e.action == InventoryAction.HOTBAR_MOVE_AND_READD ||
            e.action == InventoryAction.DROP_ALL_SLOT ||
            e.action == InventoryAction.DROP_ONE_SLOT
        ) {
            entry.move.toList()
        } else {
            emptyList()
        }

        val change = if (
            e.action == InventoryAction.PICKUP_ALL ||
            e.action == InventoryAction.PICKUP_SOME ||
            e.action == InventoryAction.PICKUP_HALF ||
            e.action == InventoryAction.PICKUP_ONE ||
            e.action == InventoryAction.PLACE_ALL ||
            e.action == InventoryAction.PLACE_SOME ||
            e.action == InventoryAction.PLACE_ONE ||
            e.action == InventoryAction.SWAP_WITH_CURSOR ||
            e.action == InventoryAction.DROP_ALL_SLOT ||
            e.action == InventoryAction.DROP_ONE_SLOT ||
            e.action == InventoryAction.MOVE_TO_OTHER_INVENTORY ||
            e.action == InventoryAction.HOTBAR_MOVE_AND_READD ||
            e.action == InventoryAction.HOTBAR_SWAP ||
            e.action == InventoryAction.COLLECT_TO_CURSOR
        ) {
            entry.change.toList()
        } else {
            emptyList()
        }

        // Invoke click event
        click.forEach { it(e) }

        // Invoke pick event
        pick.forEach { it(e) }

        // Invoke move event
        move.forEach { it(e) }

        // Invoke change event
        change.forEach { it(e) }

        // cancel event
        if (entry.isMarkUnMovable) {
            e.isCancelled = true
        }
    }

    private fun onDrag(e: InventoryDragEvent) {
        println("onDrag")
        val entry = items.values.filter { e.rawSlots.contains(indexConverter.get(it.x to it.y)) }
        if (entry.isEmpty()) return

        val change = entry.map { it.change }.flatten()

        change.forEach { it(e) }
    }
}

class GUIListener(
    val gui: Inventory,
    plugin: JavaPlugin,
    val click: (InventoryClickEvent) -> Unit,
    val drag: (InventoryDragEvent) -> Unit
) : Listener {
    init {
        plugin.server.pluginManager.registerEvents(this, plugin)
    }

    @EventHandler
    private fun onDrag(e: InventoryDragEvent) {
        drag(e)
    }

    @EventHandler
    private fun onClick(e: InventoryClickEvent) {
        click(e)
    }
}