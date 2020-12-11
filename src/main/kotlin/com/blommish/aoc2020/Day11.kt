package com.blommish.aoc2020

import com.blommish.aoc2020.util.Day
import com.blommish.aoc2020.util.withLines

fun main() {
    withLines(::Day11, "11_test.txt", "11_1.txt")
}

private class Day11(val lines: List<String>) : Day {

    val next = listOf(Pair(1, 0), Pair(1, -1), Pair(1, 1),
                      Pair(0, -1), Pair(0, 1),
                      Pair(-1, 0), Pair(-1, -1), Pair(-1, 1))

    override fun first(): Int {
        fun isTaken(seats: Array<CharArray>, x: Int, y: Int, nextX: Int, nextY: Int): Boolean {
            val row = seats.getOrNull(y + nextY) ?: return false
            val seat = row.getOrNull(x + nextX) ?: return false
            return when (seat) {
                '#' -> true
                else -> false
            }
        }
        return calc(3) { seats, next, column, row -> isTaken(seats, column, row, next.second, next.first) }
    }

    override fun second(): Int {
        fun isTaken(seats: Array<CharArray>, x: Int, y: Int, nextX: Int, nextY: Int): Boolean {
            val row = seats.getOrNull(y + nextY) ?: return false
            val seat = row.getOrNull(x + nextX) ?: return false
            return when (seat) {
                '#' -> true
                'L' -> false
                else -> isTaken(seats, x + nextX, y + nextY, nextX, nextY)
            }
        }
        return calc(4) { seats, next, column, row -> isTaken(seats, column, row, next.second, next.first) }
    }

    private fun calc(takenSeats: Int, isTaken: (Array<CharArray>, Pair<Int, Int>, Int, Int) -> Boolean): Int {
        var seats = lines.map { line -> line.toCharArray() }.toTypedArray()
        var hasChanged: Boolean
        while (true) {
            hasChanged = false
            seats = seats.mapIndexed { row, rowSeats ->
                rowSeats.mapIndexed inner@{ column, seat ->
                    return@inner if (seat == '.') {
                        seat
                    } else {
                        val count = next.count { next -> isTaken.invoke(seats, next, column, row) }
                        if (seat == 'L' && count == 0) {
                            hasChanged = true
                            '#'
                        } else if (seat == '#' && count > takenSeats) {
                            hasChanged = true
                            'L'
                        } else {
                            seat
                        }
                    }
                }.toCharArray()
            }.toTypedArray()
            if (!hasChanged) {
                return seats.sumOf { it.count { it == '#' } }
            }
        }
    }

}