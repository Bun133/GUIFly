package com.github.bun133.guifly_sample

import com.github.bun133.guifly.gui
import com.github.bun133.guifly.gui.GUI
import com.github.bun133.guifly.gui.type.InventoryType
import com.github.bun133.guifly.item
import com.github.bun133.guifly.title
import com.github.bun133.guifly.type
import com.github.bun133.guifly.value.BooleanValueItemBuilder
import com.github.bun133.guifly.value.EnumValueItemBuilder
import com.github.bun133.guifly.value.Value
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin

class ValueSample {
    fun main(plugin: JavaPlugin) : GUI {
        val booleanValue = Value<Boolean>(true)

        booleanValue.listen {
            println("boolean value changed to $it")
        }

        val enumValue = Value<Material>(Material.WHITE_WOOL)

        enumValue.listen {
            println("enum value changed to $it")
        }

        val booleanItem = BooleanValueItemBuilder(1, 1, booleanValue).build()

        val enumItem = EnumValueItemBuilder(1, 2, enumValue, Material.values().toList().associateWith { ItemStack(it) }).build()

        val gui = gui(plugin) {
            title(Component.text("Value Sample"))
            type(InventoryType.CHEST_6)
            addItem(booleanItem)
            addItem(enumItem)
        }

        return gui
    }
}