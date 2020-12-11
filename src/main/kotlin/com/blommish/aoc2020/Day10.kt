package com.blommish.aoc2020

import com.blommish.aoc2020.util.DayT
import com.blommish.aoc2020.util.withLines

fun main() {
    withLines(::Day10, { it.split("\n").map(String::toLong) }, "10_test.txt", "10_test2.txt", "10_1.txt")
}

private class Day10(val lines: List<Long>) : DayT<Long> {

    val input = listOf(0L) + lines.sorted()

    override fun first(): Long {
        val fold = input.zipWithNext().fold(Pair(0L, 0L)) { acc, l ->
            when (l.second - l.first) {
                1L -> Pair(acc.first + 1, acc.second)
                3L -> Pair(acc.first, acc.second + 1)
                else -> error("Feil")
            }
        }
        return fold.first * (fold.second + 1)
    }

    override fun second(): Long {
        val pathsFrom = mutableMapOf(Pair((input.maxOrNull()!! + 3), 1L))
        input.reversed().forEach {
            pathsFrom[it] = (pathsFrom[it + 1] ?: 0) + (pathsFrom[it + 2] ?: 0) + (pathsFrom[it + 3] ?: 0)
        }
        return pathsFrom[0]!!
    }

}