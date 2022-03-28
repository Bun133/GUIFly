package com.github.bun133.guifly.infinite

import com.github.bun133.guifly.gui.type.IndexConverter

class InfiniteIndexConverter(private val defaultConverter: IndexConverter) : IndexConverter {
    var y = 0

    val forceRules = mutableMapOf<Pair<Int, Int>, Int?>()

    override fun get(pos: Pair<Int, Int>): Int? {
        return if (forceRules.containsKey(pos)) {
            forceRules[pos]
        } else {
            defaultConverter.get(pos.first to pos.second - y)
        }
    }
}