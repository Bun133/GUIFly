package com.github.bun133.guifly.gui.item

import com.github.bun133.guifly.value.Value
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryInteractEvent
import org.bukkit.inventory.ItemStack

open class ItemBuilder(
    @Suppress("MemberVisibilityCanBePrivate") val x: Int,
    @Suppress("MemberVisibilityCanBePrivate") val y: Int,
) {
    fun build(): GUIItem {
        if (stack == null) {
            throw IllegalStateException("$this stack is null,please check code and set stack")
        } else {
            val item = GUIItem(x, y, Value(stack!!))
            item.click = click
            item.shiftClick = shiftClick
            item.pick = pick
            item.move = move
            item.change = change
//            item.swap = swap
            item.isMarkUnMovable = unmovable
            return item
        }
    }

    private var stack: ItemStack? = null

    fun stack(f: () -> ItemStack): ItemBuilder {
        stack = f()
        return this
    }

    fun stack(stack: ItemStack): ItemBuilder {
        this.stack = stack
        return this
    }

    private val click = mutableListOf<GUIItem.(InventoryClickEvent) -> Unit>()
    fun click(f: GUIItem.(InventoryClickEvent) -> Unit): ItemBuilder {
        click.add(f)
        return this
    }

    private val shiftClick = mutableListOf<GUIItem.(InventoryClickEvent) -> Unit>()
    fun shiftClick(f: GUIItem.(InventoryClickEvent) -> Unit): ItemBuilder {
        shiftClick.add(f)
        return this
    }

    private val pick = mutableListOf<GUIItem.(InventoryClickEvent) -> Unit>()
    fun pick(f: GUIItem.(InventoryClickEvent) -> Unit): ItemBuilder {
        pick.add(f)
        return this
    }

    private val move = mutableListOf<GUIItem.(InventoryClickEvent) -> Unit>()
    fun move(f: GUIItem.(InventoryClickEvent) -> Unit): ItemBuilder {
        move.add(f)
        return this
    }

    private val change = mutableListOf<GUIItem.(InventoryInteractEvent) -> Unit>()

    /**
     * @note イベントはInventoryClickedEventとInventoryDragEventのどちらかが呼ばれる
     */
    fun change(f: GUIItem.(InventoryInteractEvent) -> Unit): ItemBuilder {
        change.add(f)
        return this
    }

    private var unmovable = false

    /**
     * このスタックに関連するイベントをすべて無効化するかどうか
     */
    fun markAsUnMovable(f: Boolean = true): ItemBuilder {
        unmovable = f
        return this
    }

//    private val swap = mutableListOf<(InventoryClickEvent) -> Unit>()
//    fun swap(f: (InventoryClickEvent) -> Unit) {
//        swap.add(f)
//    }
}