package com.blommish.aoc2020

import com.blommish.aoc2020.util.Lines
import com.blommish.aoc2020.util.intValue
import com.blommish.aoc2020.util.value

fun main() {
    santa().execute({ it }, "2_test.txt", "2_1.txt")
}

private fun santa() = object : Lines<String>() {

    val regex = Regex("(\\d+)-(\\d+) (\\S): (\\S+)")

    override fun first(lines: List<String>): Int {
        return lines.count { line ->
            val match = regex.find(line)!!
            val first: Int = match.intValue(1)
            val second = match.intValue(2)
            val char = match.value(3).toCharArray().single()
            match.value(4).count { it == char } in first..second
        }
    }

    override fun second(lines: List<String>): Int {
        return lines.count { line ->
            val match = regex.find(line)!!
            val first: Int = match.intValue(1) - 1
            val second = match.intValue(2) - 1
            val char = match.value(3).toCharArray().single()
            val value = match.value(4)
            (char == value[first]).xor(char == value[second])
        }
    }
}
