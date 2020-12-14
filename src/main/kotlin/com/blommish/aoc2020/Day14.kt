package com.blommish.aoc2020

import com.blommish.aoc2020.util.DayT
import com.blommish.aoc2020.util.withLines

fun main() {
    withLines(::Day14, { it.split("\n") }, "14_test_2.txt", "14_1.txt")
}

private class Day14(val lines: List<String>) : DayT<Long> {

    companion object {

        val REGEX = """(\w+)(\[(\d+)\])? = (\w+)""".toRegex()
    }

    //15514035145260
    override fun first(): Long {
        var mask = ""
        val map = mutableMapOf<String, CharArray>()
        lines.forEach { line ->
            val (operation, _, memoryIndex, value) = REGEX.matchEntire(line)!!.destructured
            if (operation == "mask") {
                mask = value
            } else {
                val binary = value.toInt().toString(2).padStart(36, '0').toCharArray()
                for (i in mask.indices) {
                    val c = mask[i]
                    if (c == '1' || c == '0') {
                        binary[i] = c
                    }
                }
                map[memoryIndex] = binary
            }
        }
        return map.values.map { it.joinToString("").toLong(2) }.sum()
    }

    //3926790061594
    override fun second(): Long {
        var mask = ""
        val map = mutableMapOf<String, Long>()
        lines.forEach { line ->
            val (operation, _, memoryIndex, value) = REGEX.matchEntire(line)!!.destructured
            if (operation == "mask") {
                mask = value
            } else {
                val binary = memoryIndex.toInt().toString(2).padStart(36, '0').toCharArray()
                val floatingIndices = mutableListOf<Int>()
                for (i in mask.indices) {
                    val c = mask[i]
                    if (c == 'X') {
                        binary[i] = c
                        floatingIndices.add(i)
                    } else if (c == '1') {
                        binary[i] = c
                    }
                }
                val list = mutableListOf(binary)
                floatingIndices.forEach {
                    for (i in list.indices) {
                        val copyOf = list[i].copyOf()
                        copyOf[it] = '0'
                        list.add(copyOf)
                        list[i][it] = '1'
                    }
                }
                list.forEach {
                    map[it.joinToString("")] = value.toLong()
                }
            }
        }
        return map.values.sum()
    }

}