package com.github.bun133.guifly

import com.github.bun133.guifly.gui.GUI
import com.github.bun133.guifly.gui.GUIBuilder
import com.github.bun133.guifly.gui.type.InventoryType
import com.github.bun133.guifly.gui.item.ItemBuilder
import com.github.bun133.guifly.gui.listen.ListenBuilder
import com.github.bun133.guifly.gui.range.RangeBuilder
import net.kyori.adventure.text.Component
import org.bukkit.inventory.InventoryHolder
import org.bukkit.plugin.java.JavaPlugin

fun gui(plugin: JavaPlugin, f: GUIBuilder.() -> Unit): GUI {
    val builder = GUIBuilder()
    builder.f()
    return builder.build(plugin)
}

fun GUIBuilder.item(x: Int, y: Int, f: ItemBuilder.() -> Unit) {
    val builder = ItemBuilder(x, y)
    builder.f()
    this.addItem(builder.build())
}

fun GUIBuilder.item(pos: Pair<Int, Int>, f: ItemBuilder.() -> Unit) = this.item(pos.first, pos.second, f)

fun GUIBuilder.range(fromX: Int, fromY: Int, toX: Int, toY: Int, f: RangeBuilder.() -> Unit) {
    val builder = RangeBuilder(fromX..toX, fromY..toY)
    builder.f()
    this.addItem(*(builder.build().toTypedArray()))
}

fun GUIBuilder.range(from: Pair<Int, Int>, to: Pair<Int, Int>, f: RangeBuilder.() -> Unit) =
    this.range(from.first, from.second, to.first, to.second, f)

fun GUIBuilder.range(x: IntRange, y: IntRange, f: RangeBuilder.() -> Unit) =
    this.range(x.first, y.first, x.last, y.last, f)

fun GUIBuilder.title(comp: Component) {
    this.setTitle(comp)
}

fun GUIBuilder.type(type: InventoryType) {
    this.setType(type)
}

fun GUIBuilder.holder(holder: InventoryHolder) {
    this.setHolder(holder)
}

// WIP
fun GUIBuilder.listen(f: ListenBuilder.() -> Unit) {
    val builder = ListenBuilder()
    builder.f()
//    this.addListen(builder.build()) // TODO
}