package com.github.bun133.guifly.value

import com.github.bun133.guifly.gui.item.ItemBuilder
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class EnumValueItemBuilder<T : Enum<T>>(x: Int, y: Int, val value: Value<T>, val stackMap: Map<T, ItemStack>) :
    ItemBuilder(x, y) {
    init {
        click {
            val index = stackMap.keys.indexOf(value.value)
            if (index == -1) {
                this.item.value =
                    ItemStack(Material.WHITE_WOOL).also {
                        it.editMeta { m ->
                            m.displayName(Component.text("エラー: 値が見つかりません"))
                        }
                    }
            } else {
                val nextIndex = (index + 1) % stackMap.keys.size
                val next = stackMap.keys.elementAt(nextIndex)
                value.value = next
                this.item.value = stackMap[next]!!
            }
        }

        stack{
            val i = stackMap[value.value]
            if (i == null) {
                ItemStack(Material.WHITE_WOOL).also {
                    it.editMeta { m ->
                        m.displayName(Component.text("エラー: 値が見つかりません"))
                    }
                }
            } else {
                i
            }
        }
    }

}