package com.github.bun133.guifly_sample

import com.github.bun133.guifly.gui.item.ItemBuilder
import com.github.bun133.guifly.gui.type.InventoryType
import com.github.bun133.guifly.infinite.InfiniteGUI
import com.github.bun133.guifly.infinite.InfiniteGUIBuilder
import com.github.bun133.guifly.item
import com.github.bun133.guifly.type
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin

class InfiniteSample {
    fun main(plugin: JavaPlugin): InfiniteGUI {
        val infinite = InfiniteGUIBuilder()
        infinite.setTitle(Component.text("InfiniteSample"))
        infinite.type(InventoryType.CHEST_6)


        infinite.item(1 to 1) {
            stack(ItemStack(Material.STONE))
            click { e -> e.whoClicked.sendMessage("click At 1,1") }
            markAsUnMovable()
        }

        infinite.item(1 to 6) {
            stack(ItemStack(Material.OBSIDIAN))
            click { e -> e.whoClicked.sendMessage("click At 1,6") }
            markAsUnMovable()
        }

        infinite.item(1 to 10) {
            stack(ItemStack(Material.GOLD_BLOCK))
            click { e -> e.whoClicked.sendMessage("click At 1,10") }
            markAsUnMovable()
        }

        return infinite.build(plugin)
    }
}