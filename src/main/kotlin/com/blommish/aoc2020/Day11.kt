package com.blommish.aoc2020

import com.blommish.aoc2020.util.Day
import com.blommish.aoc2020.util.withLines
import java.util.stream.IntStream

fun main() {
    withLines(::Day11, "11_test.txt", "11_1.txt")
}

private class Day11(val lines: List<String>) : Day {

    override fun first(): Int {
        var seats = lines.map { line -> line.toCharArray() }.toTypedArray()
        fun isEmpty(column: Int, rowNum: Int): Boolean {
            val row = seats.getOrNull(rowNum)
            return row == null || (row.getOrNull(column) != '#' &&
                                   row.getOrNull(column - 1) != '#' &&
                                   row.getOrNull(column + 1) != '#')
        }

        fun isOccupied(array: CharArray, column: Int): Int = array.getOrNull(column)?.let { if (it == '#') 1 else 0 } ?: 0

        fun countOccupied(column: Int, row: Int): Int {
            return IntStream.of(row - 1, row, row + 1)
                    .map { rowNum ->
                        seats.getOrNull(rowNum)?.let { seat ->
                            (if (row != rowNum) isOccupied(seat, column) else 0) +
                            isOccupied(seat, column - 1) + isOccupied(seat, column + 1)
                        } ?: 0
                    }
                    .sum()
        }

        var hasChanged: Boolean
        while (true) {
            hasChanged = false
            seats = seats.mapIndexed { row, rowSeats ->
                rowSeats.mapIndexed inner@{ column, seat ->
                    if (seat == 'L' && isEmpty(column, row) && isEmpty(column, row + 1) && isEmpty(column, row - 1)) {
                        hasChanged = true
                        return@inner '#'
                    } else if (seat == '#' && countOccupied(column, row) > 3) {
                        hasChanged = true
                        return@inner 'L'
                    }
                    return@inner seat
                }.toCharArray()
            }.toTypedArray()
            if (!hasChanged) {
                return seats.sumOf { it.count { it == '#' } }
            }
        }
    }

    fun nextSeat(column: Int, row: Int, columnNext: Int, rowNext: Int, seats: Array<CharArray>, returnValue: Boolean): Boolean {
        val rowSeats = seats.getOrNull(row + rowNext) ?: return returnValue
        val seat = rowSeats.getOrNull(column + columnNext) ?: return returnValue
        if (seat == 'L') return returnValue
        if (seat == '#') return !returnValue
        return nextSeat(column + columnNext, row + rowNext, columnNext, rowNext, seats, returnValue)
    }

    override fun second(): Int {
        var seats = lines.map { line -> line.toCharArray() }.toTypedArray()

        val next = listOf(Pair(1, 0), Pair(1, -1), Pair(1, 1),
                          Pair(0, -1), Pair(0, 1),
                          Pair(-1, 0), Pair(-1, -1), Pair(-1, 1))

        fun isEmpty(column: Int, row: Int): Boolean =
                next.all { nextSeat(column, row, it.first, it.second, seats, true) }

        fun countOccupied(column: Int, row: Int): Int =
                next.count { nextSeat(column, row, it.first, it.second, seats, false) }

        var hasChanged: Boolean
        while (true) {
            hasChanged = false
            seats = seats.mapIndexed { row, rowSeats ->
                rowSeats.mapIndexed inner@{ column, seat ->
                    if (seat == 'L' && isEmpty(column, row)) {
                        hasChanged = true
                        '#'
                    } else if (seat == '#' && countOccupied(column, row) > 4) {
                        hasChanged = true
                        'L'
                    } else {
                        seat
                    }
                }.toCharArray()
            }.toTypedArray()
            if (!hasChanged) {
                return seats.sumOf { it.count { it == '#' } }
            }
        }
        return 0
    }

}