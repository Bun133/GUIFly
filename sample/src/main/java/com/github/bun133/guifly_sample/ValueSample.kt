package com.github.bun133.guifly_sample

import com.github.bun133.guifly.gui
import com.github.bun133.guifly.gui.type.InventoryType
import com.github.bun133.guifly.item
import com.github.bun133.guifly.title
import com.github.bun133.guifly.type
import com.github.bun133.guifly.value.Value
import net.kyori.adventure.text.Component
import org.bukkit.plugin.java.JavaPlugin

class ValueSample {
    fun open(plugin: JavaPlugin){
        val gui = gui(plugin){
            title(Component.text("Value Sample"))
            type(InventoryType.CHEST_6)
        }

        val booleanValue = Value<Boolean>(true)
    }
}