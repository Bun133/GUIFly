package com.github.bun133.guifly.gui

import com.github.bun133.guifly.gui.item.GUIItem
import com.github.bun133.guifly.gui.type.IndexConverter
import com.github.bun133.guifly.gui.type.InventoryType
import com.github.bun133.guifly.value.Value
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
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin

open class GUI(
    private val indexConverter: IndexConverter,
    inventoryType: org.bukkit.event.inventory.InventoryType,
    size: Int,
    title: Component,
    holder: InventoryHolder?,
    plugin: JavaPlugin,
    val notInsertable: Boolean
) : Listener {
    constructor(
        type: InventoryType,
        title: Component,
        holder: InventoryHolder?,
        plugin: JavaPlugin,
        notInsertable: Boolean = false
    ) : this(
        type.indexConverter,
        type.type,
        type.size,
        title,
        null,
        plugin,
        notInsertable
    )

    val gui = if (inventoryType == org.bukkit.event.inventory.InventoryType.CHEST) {
        Bukkit.createInventory(holder, size, title)
    } else {
        Bukkit.createInventory(holder, inventoryType, title)
    }

    val listener = GUIListener(gui, plugin, { onClick(it) }, { onDrag(it) })

    protected val items = mutableMapOf<Pair<Int, Int>, GUIItem>()
    private val itemStackListeners =
        mutableMapOf<GUIItem, Pair<Value<ItemStack>, Value<ItemStack>.(ItemStack) -> Unit>>()

    operator fun set(unit: Unit, item: GUIItem) {
        set(item)
    }

    /**
     * set item in this GUI
     */
    open fun set(vararg item: GUIItem) {
        item.forEach { e ->
            indexConverter.get(e.x to e.y)?.let {
                val before = getEntry(e.x to e.y)
                if (before != null) {
                    itemStackListeners[before]?.let { p ->
                        p.first.unListen(p.second)
                    }
                    itemStackListeners.remove(before)
                }
                val f: Value<ItemStack>.(ItemStack) -> Unit = { _: ItemStack -> set(e) }
                e.item.listen(f)
                itemStackListeners[e] = e.item to f
                gui.setItem(it, e.item.value)
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

    private fun getEntry(slot: Int): GUIItem? {
        return items.toList().filter { indexConverter.get(it.first) == slot }.map { it.second }.firstOrNull()
    }

    private fun getEntry(pos: Pair<Int, Int>): GUIItem? {
        return items[pos]
    }

    private fun preventInsert(e: InventoryClickEvent) {
        if (e.clickedInventory == gui) {
            if (
                e.action == InventoryAction.PLACE_ALL ||
                e.action == InventoryAction.PLACE_SOME ||
                e.action == InventoryAction.PLACE_ONE ||
                e.action == InventoryAction.PLACE_ALL
            ) {
                e.isCancelled = true
            }
        } else if (e.view.topInventory == gui && e.clickedInventory == e.view.bottomInventory) {
            if (e.action == InventoryAction.MOVE_TO_OTHER_INVENTORY) {
                e.isCancelled = true
            }
        }
    }

    /// EVENTS
    private fun onClick(e: InventoryClickEvent) {
        if (notInsertable) preventInsert(e)
        if (e.clickedInventory != gui) return
        val entry = getEntry(e.slot) ?: return

        // List click event
        val click = if (
            e.action == InventoryAction.PICKUP_ALL ||
            e.action == InventoryAction.PICKUP_HALF ||
            e.action == InventoryAction.PICKUP_ONE ||
            e.action == InventoryAction.PICKUP_SOME ||
            e.action == InventoryAction.PLACE_ALL ||
            e.action == InventoryAction.PLACE_SOME ||
            e.action == InventoryAction.PLACE_ONE ||
            e.action == InventoryAction.SWAP_WITH_CURSOR ||
            e.action == InventoryAction.MOVE_TO_OTHER_INVENTORY
        ) {
            entry.click.toList()
        } else {
            emptyList()
        }

        val shiftClick = if (
            e.action == InventoryAction.MOVE_TO_OTHER_INVENTORY
        ) {
            entry.shiftClick.toList()
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
        click.forEach { entry.it(e) }

        // Invoke shift click event
        shiftClick.forEach { entry.it(e) }

        // Invoke pick event
        pick.forEach { entry.it(e) }

        // Invoke move event
        move.forEach { entry.it(e) }

        // Invoke change event
        change.forEach { entry.it(e) }

        // cancel event
        if (entry.isMarkUnMovable) {
            e.isCancelled = true
        }
    }

    private fun onDrag(e: InventoryDragEvent) {
        e.rawSlots.filter { it < e.view.topInventory.size }.mapNotNull { getEntry(it) }.forEach {
            val change = it.change
            change.forEach { c -> it.c(e) }
            if (it.isMarkUnMovable) {
                e.isCancelled = true
            }
        }
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