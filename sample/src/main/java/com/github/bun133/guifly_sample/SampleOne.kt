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
    fun main(plugin: JavaPlugin): GUI {
        val gui = gui(plugin) {
            // GUIのタイトルを指定
            title(Component.text("SampleOne"))
            // GUIのタイプを指定(チェスト3段)
            type(InventoryType.CHEST_3)

            // GUI内のアイテムを変更できないようにする(推奨)
            setMarkedNotInsertable()

            // (1,1)(左上)のアイテムを指定
            item(1 to 1) {
                // クリック時の処理
                click {
                    (it.whoClicked as Player).sendMessage("click!,change!")
                    this.item.value = ItemStack(Material.values().filter { m -> m.isItem }.random())
                }

                // アイテムを指定
                stack(ItemStack(Material.STONE))

                // このアイテムに関してのイベントを全部キャンセルする(動かなくなる)
                markAsUnMovable()
            }

            // (2,2) ~ (4,4)のアイテムを指定(3段までしかないので、4段目の指定は無視されます)
            range(2 to 2, 4 to 4) {
                // すべてのアイテムを指定
                all {
                    // アイテムを取ったときの処理
                    pick {
                        (it.whoClicked as Player).sendMessage("pick! in Range Item")
                    }

                    // スロットの中身が変わったときの処理
                    change {
                        (it.whoClicked as Player).sendMessage("change! in Range Item")
                    }

                    // アイテムの指定
                    stack(ItemStack(Material.STONE))

                    // このアイテムに関してのイベントを全部キャンセルする(動かなくなる)
                    markAsUnMovable()
                }

                // (2,2)を個別に指定する
                set(2, 2) {
                    // クリック時の処理
                    click {
                        (it.whoClicked as Player).sendMessage("Hi! I'm overwritten item!")
                    }

                    shiftClick {
                        (it.whoClicked as Player).sendMessage("Shift click! at Overwritten Item")
                    }

                    // アイテムの指定
                    stack(ItemStack(Material.STONE))

                    // このアイテムに関してのイベントを全部キャンセルする(動かなくなる)
                    markAsUnMovable()
                }
            }
        }

//        gui.open(Player)
        return gui
    }
}