package com.blommish.aoc2020

import com.blommish.aoc2020.util.Lines

fun main() {
    santa().execute(String::toInt, "1_test.txt", "1_1.txt")
}

private fun santa() = object : Lines<Int>() {

    override fun first(lines: List<Int>): Int {
        lines.forEachIndexed { index1, s1 ->
            lines.forEachIndexed { index2, s2 ->
                if (index1 != index2) {
                    if (s1 + s2 == 2020) {
                        return s1 * s2
                    }
                }
            }
        }
        return 0
    }

    override fun second(lines: List<Int>): Int {
        lines.forEachIndexed { index1, s1 ->
            lines.forEachIndexed { index2, s2 ->
                lines.forEachIndexed { index3, s3 ->
                    if (index1 != index2 && index1 != index3 && index2 != index3) {
                        if (s1 + s2 + s3 == 2020) {
                            return s1 * s2 * s3
                        }
                    }
                }
            }
        }
        return 0
    }
}
