package com.github.bun133.guifly.sample

import com.github.bun133.guifly.*
import com.github.bun133.guifly.gui.type.InventoryType
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin

class SampleOne {
    fun main() {
        val plugin = dummyPlugin()
        val gui = gui(plugin) {
            title(Component.text("SampleOne"))
            type(InventoryType.CHEST_3)

            item(1 to 1) {
                click {
                    (it.whoClicked as Player).sendMessage("click!")
                }

                markAsUnMovable()
            }

            range(2 to 2, 4 to 4) {
                all {
                    pick {
                        (it.whoClicked as Player).sendMessage("pick! in Range Item")
                    }

                    stack {
                        ItemStack(Material.STONE, 1)
                    }
                }
            }
        }

//        gui.open(Player)
    }
}

class dummyPlugin : JavaPlugin() {
}