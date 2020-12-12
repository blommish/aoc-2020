package com.blommish.aoc2020

import com.blommish.aoc2020.util.Day
import com.blommish.aoc2020.util.withLines
import kotlin.math.abs

fun main() {
    withLines(::Day12, "12_test.txt", "12_1.txt")
}

private class Day12(val lines: List<String>) : Day {

    override fun first(): Int {
        var x = 0
        var y = 0
        var dirX = 1
        var dirY = 0
        fun move(operation: String, n: Int) = when (operation) {
            "N" -> y += n
            "S" -> y -= n
            "E" -> x += n
            "W" -> x -= n
            else -> error(operation)
        }

        fun moveShip(times: Int) {
            x += (dirX * times)
            y += (dirY * times)
        }

        fun rotate(n: Int, way: Int) {
            for (i in 1..(n / 90)) {
                val prevDirX = dirX
                dirX = dirY * way
                dirY = -prevDirX * way
            }
        }
        lines.forEach {
            val (_, operation, value) = it.split("", limit = 3)
            val n = value.toInt()
            when (operation) {
                "R" -> rotate(n, 1)
                "L" -> rotate(n, -1)
                "F" -> moveShip(n)
                else -> move(operation, n)
            }
        }
        return abs(x) + abs(y)
    }

    override fun second(): Int {
        var x = 10
        var y = 1
        var dirX = 1
        var dirY = 0
        var shipX = 0
        var shipY = 0

        fun move(operation: String, n: Int) = when (operation) {
            "N" -> y += n
            "S" -> y -= n
            "E" -> x += n
            "W" -> x -= n
            else -> error(operation)
        }

        fun moveShip(times: Int) {
            shipX += x * times
            shipY += y * times
        }

        fun rotatePointer(way: Int) {
            val prevX = x
            x = y * way
            y = -prevX * way
        }

        fun rotate(n: Int, way: Int) {
            for (i in 1..(n / 90)) {
                rotatePointer(way)
                val prevDirX = dirX
                dirX = dirY * way
                dirY = -prevDirX * way
            }
        }

        lines.forEach {
            val (_, operation, value) = it.split("", limit = 3)
            val n = value.toInt()
            when (operation) {
                "R" -> rotate(n, 1)
                "L" -> rotate(n, -1)
                "F" -> moveShip(n)
                else -> move(operation, n)
            }
        }
        return abs(shipX) + abs(shipY)
    }

}