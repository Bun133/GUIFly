package com.github.bun133.guifly_sample

import com.github.bun133.guifly.gui
import com.github.bun133.guifly.gui.GUI
import com.github.bun133.guifly.title
import com.github.bun133.guifly.type
import com.github.bun133.guifly.gui.type.InventoryType
import com.github.bun133.guifly.item
import com.github.bun133.guifly.range
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin

class SampleOne {
    fun main(plugin:JavaPlugin) : GUI {
        val gui = gui(plugin) {
            title(Component.text("SampleOne"))
            type(InventoryType.CHEST_3)

            item(1 to 1) {
                click {
                    (it.whoClicked as Player).sendMessage("click!")
                }

                stack(ItemStack(Material.STONE))

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
        return gui
    }
}