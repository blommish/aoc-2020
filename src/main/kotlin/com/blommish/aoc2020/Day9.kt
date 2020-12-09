package com.blommish.aoc2020

import com.blommish.aoc2020.util.DayT
import com.blommish.aoc2020.util.withLines

fun main() {
    withLines(::Day9, { it.split("\n").map { it.toLong() } }, "9_1.txt")
}

private class Day9(val lines: List<Long>) : DayT<Long> {

    override fun first(): Long {
        val size = 25
        for (i in size..(lines.size)) {
            val first = first(i, size)
            if (!first) {
                return lines[i]
            } else {
                println(" found ${lines[i]} ($i)")
            }
        }
        return 0
    }

    private fun first(index: Int, size: Int): Boolean {
        val current = lines[index]
        for (i in (index - size)..(index - 2)) {
            val first = lines[i]
            inner@ for (i2 in (i + 1) until index) {
                val second = lines[i2]
                val sum = first + second
                if (current == sum) {
                    return true
                }
            }
        }
        return false
    }

    val find = 552655238L
    override fun second(): Long {

        for (i in 0..lines.size) {
            val sum = sum(i)
            if (sum != 0L) {
                return sum
            }
        }
        return 0
    }

    private fun sum(i: Int): Long {
        val list = mutableListOf<Long>()
        lines.subList(i, lines.size)
                .fold(0L) { acc, l ->
                    list.add(l)
                    val result = acc + l
                    if (result > find) {
                        return 0
                    } else if (result == find) {
                        return list.minOrNull()!! + list.maxOrNull()!!
                    }
                    result
                }
        return 0
    }
}