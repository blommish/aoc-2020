package com.blommish.aoc2020

import com.blommish.aoc2020.util.DayT
import com.blommish.aoc2020.util.withLines

fun main() {
    withLines(::Day15, { it.split("\n") }, "15_1.txt")
}

private class Day15(val lines: List<String>) : DayT<Long> {

    //639
    override fun first(): Long {
        return memory(lines.first(), 2020).toLong()
    }

    //266
    override fun second(): Long {
        return memory(lines.first(), 30000000).toLong()
    }

    private fun memory(line: String, iterations: Int): Int {
        val split = line.split(",")
        val map = split.mapIndexed { index, s -> s.toInt() to index }.toMap().toMutableMap()
        var previous = split.last().toInt()
        var previousIndex: Int? = null
        for (i in split.size until iterations) {
            val value = if(previousIndex == null) 0 else map[previous]!! - previousIndex
            previousIndex = map.put(value, i)
            previous = value
        }
        return previous
    }

}