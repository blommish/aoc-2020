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
            return row == null || (row.getOrNull(column) != '#' && row.getOrNull(column - 1) != '#' && row.getOrNull(column + 1) != '#')
        }

        fun isOccupied(array: CharArray, column: Int): Int = array.getOrNull(column)?.let { if (it == '#') 1 else 0 } ?: 0

        fun countOccupied(column: Int, row: Int): Int {
            return IntStream.of(row - 1, row, row + 1)
                    .map { rowNum ->
                        seats.getOrNull(rowNum)?.let { seat ->
                            (if (row != rowNum) isOccupied(seat, column) else 0) + isOccupied(seat, column - 1) + isOccupied(seat,
                                                                                                                             column + 1)
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

    private fun print(map: Map<Int, Map<Int, Char>>) {
        println("\n-----------------\n")
        map.toSortedMap().forEach {
            println(it.value.toSortedMap().values.joinToString(""))
        }
    }

    override fun second(): Int {
        var seats = lines.map { line -> line.toCharArray() }.toTypedArray()

        fun isEmpty(column: Int, row: Int, columnNext: Int, rowNext: Int): Boolean {
            val rowSeats = seats.getOrNull(row + rowNext) ?: return true
            val seat = rowSeats.getOrNull(column + columnNext) ?: return true
            if (seat == 'L') return true
            if (seat == '#') return false
            return isEmpty(column + columnNext, row + rowNext, columnNext, rowNext)
        }

        fun isEmpty(column: Int, row: Int): Boolean {
            return isEmpty(column, row, 1, 0)
                   && isEmpty(column, row, 1, 1)
                   && isEmpty(column, row, 1, -1)
                   && isEmpty(column, row, 0, 1)
                   && isEmpty(column, row, 0, -1)
                   && isEmpty(column, row, -1, -1)
                   && isEmpty(column, row, -1, 0)
                   && isEmpty(column, row, -1, 1)
        }

        fun isOccupied(column: Int, row: Int, columnNext: Int, rowNext: Int): Boolean {
            val rowSeats = seats.getOrNull(row + rowNext) ?: return false
            val seat = rowSeats.getOrNull(column + columnNext) ?: return false
            if (seat == 'L') return false
            if (seat == '#') return true
            return isOccupied(column + columnNext, row + rowNext, columnNext, rowNext)
        }

        fun countOccupied(column: Int, row: Int): Int {
            return listOf(isOccupied(column, row, 1, 0),
                          isOccupied(column, row, 1, 1),
                          isOccupied(column, row, 1, -1),
                          isOccupied(column, row, 0, 1),
                          isOccupied(column, row, 0, -1),
                          isOccupied(column, row, -1, -1),
                          isOccupied(column, row, -1, 0),
                          isOccupied(column, row, -1, 1)).count { it }
        }

        var hasChanged: Boolean
        while (true) {
            hasChanged = false
            seats = seats.mapIndexed { row, rowSeats ->
                rowSeats.mapIndexed inner@{ column, seat ->
                    if (seat == 'L' && isEmpty(column, row)) {
                        hasChanged = true
                        return@inner '#'
                    } else if (seat == '#' && countOccupied(column, row) > 4) {
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
        return 0
    }

}