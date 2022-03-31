package com.github.bun133.guifly.value

import com.github.bun133.guifly.gui.item.ItemBuilder
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class BooleanValueItemBuilder(
    x: Int,
    y: Int,
    value: Value<Boolean>,
    falseStack: ItemStack = ItemStack(Material.GRAY_WOOL),
    trueStack: ItemStack = ItemStack(Material.LIME_WOOL)
) : ItemBuilder(x, y) {
    init {
        this.click {
            value.value = !value.value
            if (value.value) {
                this.item.value = trueStack
            } else {
                this.item.value = falseStack
            }
        }
    }
}