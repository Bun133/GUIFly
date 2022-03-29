package com.github.bun133.guifly.gui

import com.github.bun133.guifly.gui.item.GUIItem
import com.github.bun133.guifly.gui.type.InventoryType
import net.kyori.adventure.text.Component
import org.bukkit.inventory.InventoryHolder
import org.bukkit.plugin.java.JavaPlugin

open class GUIBuilder {
    open fun build(plugin: JavaPlugin): GUI {
        if (_title == null) {
            throw IllegalStateException("$this title is null,please check code and set title")
        } else if (_type == null) {
            throw IllegalStateException("$this type is null,please check code and set type")
        } else {
            return GUI(_type!!, _title!!, _holder, plugin,_markedNotInsertable).also { it.set(*items.toTypedArray()) }
        }
    }

    protected val items = mutableListOf<GUIItem>()
    fun addItem(vararg item: GUIItem) {
        items.addAll(item)
    }

    protected var _title: Component? = null
    fun setTitle(title: Component) {
        this._title = title
    }

    protected var _type: InventoryType? = null
    fun setType(type: InventoryType) {
        this._type = type
    }

    protected var _holder: InventoryHolder? = null
    fun setHolder(holder: InventoryHolder) {
        this._holder = holder
    }

    protected var _markedNotInsertable = false
    fun setMarkedNotInsertable() {
        this._markedNotInsertable = true
    }
}