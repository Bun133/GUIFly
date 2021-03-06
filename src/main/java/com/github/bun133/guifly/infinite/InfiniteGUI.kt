package com.github.bun133.guifly.infinite

import com.github.bun133.guifly.gui.GUI
import com.github.bun133.guifly.gui.GUIBuilder
import com.github.bun133.guifly.gui.item.GUIItem
import com.github.bun133.guifly.gui.item.ItemBuilder
import com.github.bun133.guifly.gui.type.IndexConverter
import com.github.bun133.guifly.gui.type.InventoryType
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.inventory.InventoryHolder
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin
import java.lang.Integer.min
import kotlin.math.max

class InfiniteGUI(
    val chestRow: Int,
    title: Component,
    holder: InventoryHolder?,
    plugin: JavaPlugin,
    notInsertable: Boolean,
    defaultIndexConverter: IndexConverter = getChestType(chestRow).indexConverter,
    private val infiniteIndex: InfiniteIndexConverter = InfiniteIndexConverter(defaultIndexConverter)
) :
    GUI(
        infiniteIndex,
        getChestType(chestRow).type,
        getChestType(chestRow).size,
        title,
        holder,
        plugin,
        notInsertable
    ) {
    init {
        initGUI()
    }

    private fun initGUI() {
        val upButton = ItemBuilder(9, 1)
            .stack(ItemStack(Material.ARROW).also { it.editMeta { i -> i.displayName(Component.text("上へ")) } })
            .click { onUpClick() }
            .markAsUnMovable()
            .build()

        infiniteIndex.forceRules[9 to 1] = 9 - 1

        val downButton = ItemBuilder(9, chestRow)
            .stack(ItemStack(Material.ARROW).also { it.editMeta { i -> i.displayName(Component.text("下へ")) } })
            .click { onDownClick() }
            .markAsUnMovable()
            .build()

        infiniteIndex.forceRules[9 to chestRow] = 9 * chestRow - 1
        val upPageButton = ItemBuilder(9, 2)
            .stack(ItemStack(Material.ARROW).also { it.editMeta { i -> i.displayName(Component.text("上のページへ")) } })
            .click { onUpPageClick() }
            .markAsUnMovable()
            .build()
        infiniteIndex.forceRules[9 to 2] = 18 - 1

        val downPageButton = ItemBuilder(9, chestRow - 1)
            .stack(ItemStack(Material.ARROW).also { it.editMeta { i -> i.displayName(Component.text("下のページへ")) } })
            .click { onDownPageClick() }
            .markAsUnMovable()
            .build()

        infiniteIndex.forceRules[9 to chestRow - 1] = 9 * (chestRow - 1) - 1

        this[Unit] = upButton
        this[Unit] = downButton
        this[Unit] = upPageButton
        this[Unit] = downPageButton
    }


    fun update() {
        val toUpdate = items.filter {
            val y = if (infiniteIndex.forceRules.contains(it.key)) it.key.second else it.key.second - infiniteIndex.y
            y in 1..chestRow
        }

        items.clear()
        gui.clear()
        set(*toUpdate.values.toTypedArray())
    }

    fun onUpClick() {
        infiniteIndex.y = max(0, infiniteIndex.y - 1)
        update()
    }

    fun onUpPageClick() {
        infiniteIndex.y = max(0, infiniteIndex.y - chestRow)
        update()
    }

    fun onDownClick() {
        infiniteIndex.y = min(maxY() - 1, infiniteIndex.y + 1)
        update()
    }

    fun onDownPageClick() {
        infiniteIndex.y = min(maxY() - 1, infiniteIndex.y + chestRow)
        update()
    }

    fun maxY(): Int {
        return items.maxOf { it.key.second }
    }

}

private fun getChestType(chestRow: Int): InventoryType {
    return when (chestRow) {
        3 -> InventoryType.CHEST_3
        4 -> InventoryType.CHEST_4
        5 -> InventoryType.CHEST_5
        6 -> InventoryType.CHEST_6
        else -> throw IllegalArgumentException("chestRow must be 3, 4, 5, or 6")
    }
}

class InfiniteGUIBuilder() : GUIBuilder() {
    override fun build(plugin: JavaPlugin): InfiniteGUI {
        if (_title == null) {
            throw IllegalStateException("$this title is null,please check code and set title")
        } else if (_type == null) {
            throw IllegalStateException("$this type is null,please check code and set type")
        } else {
            return InfiniteGUI(
                toChestRow(_type!!),
                _title!!,
                _holder,
                plugin,
                _markedNotInsertable
            ).also { it.set(*items.toTypedArray()) }
        }
    }

    private fun toChestRow(type: InventoryType): Int {
        when (type) {
            InventoryType.CHEST_3 -> return 3
            InventoryType.CHEST_4 -> return 4
            InventoryType.CHEST_5 -> return 5
            InventoryType.CHEST_6 -> return 6
            else -> throw IllegalArgumentException("type must be CHEST_3, CHEST_4, CHEST_5, or CHEST_6")
        }
    }
}