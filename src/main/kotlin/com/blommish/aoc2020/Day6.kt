package com.blommish.aoc2020

import com.blommish.aoc2020.util.Line

fun main() {
    santa().execute({ it.split("\n\n") }, "6_test.txt", "6_1.txt")
}

private fun santa() = object : Line() {

    override fun first(lines: List<String>): Int {
        return lines.sumOf {
            it.replace("\n", "")
                    .toCharArray()
                    .distinct()
                    .size
        }
    }

    override fun second(lines: List<String>): Int {
        return lines.sumOf { it ->
            val group = it.split("\n")
            group
                    .flatMap { it.toCharArray().distinct() }
                    .groupBy { it }
                    .count { it.value.size == group.size }
        }
    }

}