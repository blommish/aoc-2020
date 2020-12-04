package com.blommish.aoc2020

import com.blommish.aoc2020.util.Lines

fun main() {
    santa().execute({ it }, "3_test.txt", "3_1.txt")
}

private fun santa() = object : Lines<String>() {

    override fun first(lines: List<String>): Int {
        return countTrees(lines, 3, 1)
    }

    override fun second(lines: List<String>): Int {
        return listOf(Pair(1,1), Pair(3, 1), Pair(5,1), Pair(7,1), Pair(1, 2))
                .map { countTrees(lines, it.first, it.second) }
                .reduce { acc, i -> acc*i }
    }

    private fun countTrees(lines: List<String>, xAdd: Int, yAdd: Int): Int {
        var x = 0
        var y = 0
        val length = lines.first().length
        var count = 0
        while (true) {
            y += yAdd
            if (y >= lines.size) {
                return count
            }
            x += xAdd
            if (x >= length) {
                x -= length
            }
            if (lines[y][x] == '#') {
                count++
            }
        }
    }
}
