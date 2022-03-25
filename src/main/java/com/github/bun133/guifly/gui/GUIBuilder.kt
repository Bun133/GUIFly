package com.github.bun133.guifly.gui

import com.github.bun133.guifly.gui.item.GUIItem
import com.github.bun133.guifly.gui.type.InventoryType
import com.github.bun133.guifly.item
import net.kyori.adventure.text.Component
import org.bukkit.inventory.InventoryHolder
import org.bukkit.plugin.java.JavaPlugin

class GUIBuilder {
    internal fun build(plugin: JavaPlugin): GUI {
        if (title == null) {
            throw IllegalStateException("$this title is null,please check code and set title")
        } else if (type == null) {
            throw IllegalStateException("$this type is null,please check code and set type")
        } else {
            return GUI(type!!, title!!, holder, plugin).also { it.set(*items.toTypedArray()) }
        }
    }

    private val items = mutableListOf<GUIItem>()

    internal fun addItem(vararg item: GUIItem) {
        items.addAll(item)
    }

    private var title: Component? = null
    internal fun setTitle(title: Component) {
        this.title = title
    }

    private var type: InventoryType? = null
    internal fun setType(type: InventoryType) {
        this.type = type
    }

    private var holder: InventoryHolder? = null
    internal fun setHolder(holder: InventoryHolder) {
        this.holder = holder
    }
}