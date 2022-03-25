package com.github.bun133.guifly.gui.type

fun interface IndexConverter {
    /**
     * @note pos start from (1,1)
     */
    fun get(pos: Pair<Int, Int>): Int?
}

val chestIndexConverter = IndexConverter {
    return@IndexConverter (it.first - 1) + (it.second - 1) * 9
}

/**
 * (1,1) ~ (9,3) -> インベントリ上側
 * (1,4) ~ (9,4) -> ホットバー
 */
val inventoryIndexConverter = IndexConverter {
    if (it.first in 1..9 && it.second in 1..3) {
        return@IndexConverter (it.first - 1) + (it.second - 1) * 9
    }
    return@IndexConverter null
}

val chest3IndexConverter = IndexConverter {
    if (it.first in 1..9 && it.second in 1..7) {
        return@IndexConverter chestIndexConverter.get(it)
    }
    return@IndexConverter null
}

val chest4IndexConverter = IndexConverter {
    if (it.first in 1..9 && it.second in 1..8) {
        return@IndexConverter chestIndexConverter.get(it)
    }
    return@IndexConverter null
}

val chest5IndexConverter = IndexConverter {
    if (it.first in 1..9 && it.second in 1..9) {
        return@IndexConverter chestIndexConverter.get(it)
    }
    return@IndexConverter null
}

val chest6IndexConverter = IndexConverter {
    if (it.first in 1..9 && it.second in 1..10) {
        return@IndexConverter chestIndexConverter.get(it)
    }
    return@IndexConverter null
}

/**
 * (1,1) ~ (3,3) -> 中央の3*3の欄
 * (1,4) ~ (9,7) -> インベントリ
 */
val square3Converter = IndexConverter {
    if (it.first in 1..3 && it.second in 1..3) {
        return@IndexConverter (it.first - 1) + (it.second - 1) * 3
    } else if (it.first in 1..9 && it.second in 4..7) {
        return@IndexConverter chestIndexConverter.get(it)
    }
    return@IndexConverter null
}

/**
 * (1,1) ~ (3,3) -> クラフト欄
 * (4,1) -> 成果物欄
 * (1,4) ~ (9,7) -> インベントリ
 */
val workbenchIndexConverter = IndexConverter {
    return@IndexConverter when (it) {
        1 to 1 -> 1
        2 to 1 -> 2
        3 to 1 -> 3
        1 to 2 -> 4
        2 to 2 -> 5
        3 to 2 -> 6
        1 to 3 -> 7
        2 to 3 -> 8
        3 to 3 -> 9
        4 to 1 -> 0
        else -> inventoryIndexConverter.get(it.first to it.second - 3)
    }
}

/**
 * (1,1) -> 調理欄上側
 * (1,2) -> 調理欄下側
 * (2,1) -> 成果物欄
 * (1,3) ~ (9,6) -> インベントリ
 */
val furnaceIndexConverter = IndexConverter {
    return@IndexConverter when (it) {
        1 to 1 -> 0
        1 to 2 -> 1
        2 to 1 -> 2
        else -> inventoryIndexConverter.get(it.first to it.second - 2)
    }
}

/**
 * (1,1) ~ (2,2) -> クラフトの4つの欄
 * (3,1) -> クラフトの結果欄
 */
val craftingIndexConverter = IndexConverter {
    return@IndexConverter when (it) {
        1 to 1 -> 0
        1 to 2 -> 1
        2 to 1 -> 2
        2 to 2 -> 3
        2 to 1 -> 4
        else -> null
    }
}

/**
 * (1,1) -> エンチャントするツールを入れる欄
 * (2,1) -> ラピスラズリを入れる欄
 * (1,2) ~ (9,5) -> インベントリ
 */
val enchantingIndexConverter = IndexConverter {
    return@IndexConverter when (it) {
        1 to 1 -> 0
        2 to 1 -> 1
        else -> inventoryIndexConverter.get(it.first to it.second - 1)
    }
}

/**
 * (2,2) ~ (2,4) -> ポーション入れる欄
 * (1,3) -> 材料を入れる欄
 * (1,1) ->　燃料を入れる欄
 */
val brewingIndexConverter = IndexConverter {
    when (it) {
        2 to 2 -> 0
        2 to 3 -> 1
        2 to 4 -> 2
        1 to 3 -> 3
        1 to 1 -> 4
        else -> null
    }
}

/**
 * (1,1) ~ (1,4) -> 装備欄
 * (2,4) -> オフハンド
 * (5,1) ~ (8,9) -> インベントリ
 * @note 合っているか不明
 * todo
 */
val playerIndexConverter = IndexConverter {
    when (it) {
        1 to 1 -> 5
        1 to 2 -> 6
        1 to 3 -> 7
        1 to 4 -> 8
        2 to 4 -> 45
        else -> when (it.second) {
            5, 6, 7, 8 -> inventoryIndexConverter.get(it.first to it.second - 4)
            else -> null
        }
    }
}

/**
 * (1,1) -> 取引元アイテム左側
 * (2,1) -> 取引元アイテム右側
 * (3,1) -> 取引先アイテム
 * (1,2) ~ (9,5) -> インベントリ
 */
val merchantIndexConverter = IndexConverter {
    when (it) {
        1 to 1 -> 0
        2 to 1 -> 1
        3 to 1 -> 2
        else -> inventoryIndexConverter.get(it.first to it.second - 1)
    }
}

/**
 * (1,1) -> 加工前アイテム左側
 * (2,1) -> 加工前アイテム右側
 * (3,1) -> 加工先アイテム
 * (1,2) ~ (9,5) -> インベントリ
 */
val anvilIndexConverter = IndexConverter {
    merchantIndexConverter.get(it)
}

/**
 * (1,1) -> 加工前アイテム左側
 * (2,1) -> 加工前アイテム右側
 * (3,1) -> 加工先アイテム
 * (1,2) ~ (9,5) -> インベントリ
 */
val smithIndexConverter = IndexConverter {
    merchantIndexConverter.get(it)
}

/**
 * (1,1) -> 消費スロット
 * (1,2) ~ (9,5) -> インベントリ
 */
val beaconIndexConverter = IndexConverter {
    return@IndexConverter when (it) {
        1 to 1 -> 0
        else -> inventoryIndexConverter.get(it.first to it.second - 1)
    }
}

/**
 * (1,1) ~ (5,1) -> ホッパーインベントリ
 * (1,2) ~ (9,5) -> インベントリ
 */
val hopperIndexConverter = IndexConverter {
    return@IndexConverter when (it) {
        1 to 1 -> 0
        2 to 1 -> 1
        3 to 1 -> 2
        4 to 1 -> 3
        5 to 1 -> 4
        else -> inventoryIndexConverter.get(it.first to it.second - 1)
    }
}

/**
 * (1,1) -> 本のスロット
 * (1,2) ~ (9,5) -> インベントリ
 */
val lecternIndexConverter = IndexConverter {
    beaconIndexConverter.get(it)
}

/**
 * (1,1) -> 旗の欄
 * (2,1) -> 染料の欄
 * (1,2) -> 任意の旗の模様の欄
 * (2,2) -> 成果物の欄
 * (1,3) ~ (9,6) -> インベントリ
 */
val loomIndexConverter = IndexConverter {
    return@IndexConverter when (it) {
        1 to 1 -> 0
        2 to 1 -> 1
        1 to 2 -> 2
        2 to 2 -> 3
        else -> inventoryIndexConverter.get(it.first to it.second - 2)
    }
}

/**
 * (1,1) -> 加工前のアイテムスロット
 * (2,1) -> 加工後のアイテムスロット
 * (1,2) ~ (9,5) -> インベントリ
 */
val stoneCutterIndexConverter = IndexConverter {
    return@IndexConverter when (it) {
        1 to 1 -> 0
        2 to 1 -> 1
        else -> inventoryIndexConverter.get(it.first to it.second - 1)
    }
}