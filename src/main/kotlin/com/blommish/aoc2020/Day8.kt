package com.blommish.aoc2020

import com.blommish.aoc2020.util.Day
import com.blommish.aoc2020.util.withLines

fun main() {
    withLines(::Day8, "8_test.txt", "8_1.txt")
}

private class Day8(val lines: List<String>) : Day {

    override fun first(): Int {
        return execute().second
    }

    override fun second(): Int {
        lines.forEachIndexed { i, s ->
            if (s.contains(Regex("jmp|nop"))) {
                val result = execute(i)
                if (result.first) {
                    return result.second
                }
            }
        }
        return 0
    }

    private fun execute(changeIndex: Int = -1): Pair<Boolean, Int> {
        var sum = 0
        val visited = mutableSetOf<Int>()
        var i = 0
        while (true) {
            if (!visited.add(i) || i >= lines.size) {
                return Pair(false, sum)
            }
            var (operation, argument) = lines[i].split(" ")
            if (changeIndex > -1 && i == changeIndex) {
                operation = when (operation) {
                    "jmp" -> "nop"
                    "nop" -> "jmp"
                    else -> operation
                }
            }
            when (operation) {
                "jmp" -> i += argument.toInt()
                "acc" -> {
                    sum += argument.toInt()
                    i++
                }
                else -> i++
            }
            if (i >= lines.size) {
                return Pair(true, sum)
            }
        }
    }
}