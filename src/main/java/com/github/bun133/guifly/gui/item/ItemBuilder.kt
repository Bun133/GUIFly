package com.github.bun133.guifly.gui.item

import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

class ItemBuilder(
    @Suppress("MemberVisibilityCanBePrivate") val x: Int,
    @Suppress("MemberVisibilityCanBePrivate") val y: Int,
) {
    internal fun build(): GUIItem {
        if (stack == null) {
            throw IllegalStateException("$this stack is null,please check code and set stack")
        } else {
            val item = GUIItem(x, y, stack!!)
            item.click = click
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

    private val click = mutableListOf<(InventoryClickEvent) -> Unit>()
    fun click(f: (InventoryClickEvent) -> Unit) {
        click.add(f)
    }

    private val pick = mutableListOf<(InventoryClickEvent) -> Unit>()
    fun pick(f: (InventoryClickEvent) -> Unit) {
        pick.add(f)
    }

    private val move = mutableListOf<(InventoryClickEvent) -> Unit>()
    fun move(f: (InventoryClickEvent) -> Unit) {
        move.add(f)
    }

    private val change = mutableListOf<(InventoryClickEvent) -> Unit>()
    fun change(f: (InventoryClickEvent) -> Unit) {
        change.add(f)
    }

    private var unmovable = false

    /**
     * このスタックに関連するイベントをすべて無効化するかどうか
     */
    fun markAsUnMovable(f: Boolean = true) {
        unmovable = f
    }

//    private val swap = mutableListOf<(InventoryClickEvent) -> Unit>()
//    fun swap(f: (InventoryClickEvent) -> Unit) {
//        swap.add(f)
//    }
}