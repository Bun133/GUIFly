package com.github.bun133.guifly.gui.range

import com.github.bun133.guifly.gui.item.GUIItem
import com.github.bun133.guifly.gui.item.ItemBuilder

class RangeBuilder(private val xRange: IntRange, private val yRange: IntRange) {
    internal fun build(): MutableList<GUIItem> {
        return list
    }

    private val list = mutableListOf<GUIItem>()

    fun all(f: ItemBuilder.() -> Unit) {
        for (x in xRange) {
            for (y in yRange) {
                list.add(ItemBuilder(x, y).apply(f).build())
            }
        }
    }

    fun set(x: Int, y: Int, f: ItemBuilder.() -> Unit) {
        list.add(ItemBuilder(x, y).apply(f).build())
    }
}