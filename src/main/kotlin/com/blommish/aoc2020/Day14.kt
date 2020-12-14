package com.blommish.aoc2020

import com.blommish.aoc2020.util.DayT
import com.blommish.aoc2020.util.withLines

fun main() {
    withLines(::Day14, { it.split("\n") }, "14_test.txt", "14_1.txt")
}

private class Day14(val lines: List<String>) : DayT<Long> {

    companion object {
        val REGEX = """(\w+)(\[(\d+)\])? = (\w+)""".toRegex()
    }

    override fun first(): Long {
        var mask = ""
        val map = mutableMapOf<String, Long>()
        lines.forEachIndexed { i, line ->
            val (operation, _, place, value) = REGEX.matchEntire(line)!!.destructured
            if (operation == "mask") {
                mask = value
            } else {
                val binary = value.toInt().toString(2).padStart(36, '0')
                var b = "000000000000000000000000000000000000".toCharArray()
                for (i in mask.indices) {
                    val c = mask[i]
                    if (c == '1') {
                        b[i] = '1'
                    } else if (c == 'X') {
                        b[i] = binary[i]
                    }
                }
                map[place] = b.joinToString("").toLong(2)
            }
        }
        return map.values.reduce { acc, s ->
            acc + s
        }
    }

    override fun second(): Long {
        return 0
    }

}