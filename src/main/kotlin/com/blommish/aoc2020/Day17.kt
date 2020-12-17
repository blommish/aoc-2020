package com.blommish.aoc2020

import com.blommish.aoc2020.util.DayT
import com.blommish.aoc2020.util.withLines

fun main() {
    withLines(::Day17, { it.split("\n") }, "17_test.txt", "17_1.txt")
}

private class Day17(val lines: List<String>) : DayT<Long> {

    private val neighbourIndexes = -1..1
    private val cycles = 6
    private val initSize = lines.first().length
    private val cubeSize = initSize + cycles * 2 + 2
    private val middleIndex = (cubeSize - 1) / 2

    override fun first(): Long {
        var cube = initCube()
        for (i in 0 until cycles) {
            //println("\n#################\nCycle: ${i+1}")
            val range = (middleIndex - 2 - i)..(middleIndex + 2 + i)
            val tmpArray = initCubeArray()
            for (z in range) {
                for (y in range) {
                    for (x in range) {
                        val current = cube[z][y][x]
                        val count = count(cube, z, y, x)
                        if (current == '#' && (count == 3 || count == 4)) {
                            tmpArray[z][y][x] = '#'
                        } else if (current == '.' && count == 3) {
                            tmpArray[z][y][x] = '#'
                        }
                    }
                }
            }
            cube = tmpArray
            //print(cube)
        }
        return cube.flatten().flatMap { it.toList() }.count { it == '#' }.toLong()
    }

    private fun initCube(): Array<Array<Array<Char>>> {
        val cube = initCubeArray()
        lines.forEachIndexed { lineIndex, line ->
            line.toCharArray().forEachIndexed { columnIndex, s ->
                cube[middleIndex][middleIndex - 1 + lineIndex][middleIndex - 1 + columnIndex] = s
            }
        }
        return cube
    }

    private fun initCubeArray() = Array(cubeSize) { Array(cubeSize) { Array(cubeSize) { '.' } } }

    private fun count(cube: Array<Array<Array<Char>>>, z: Int, y: Int, x: Int): Int {
        var count = 0
        for (zCount in neighbourIndexes) {
            for (yCount in neighbourIndexes) {
                for (xCount in neighbourIndexes) {
                    if (cube[z + zCount][y + yCount][x + xCount] == '#') {
                        count++
                    }
                    if (count > 4) return count
                }
            }
        }
        return count
    }

    fun print(arrays: Array<Array<Array<Char>>>) {
        arrays.forEachIndexed { zIndex, z ->
            if (z.any { y -> y.any { x -> x == '#' } }) {
                println("z=$zIndex")
                z.forEachIndexed { yIndex, y ->
                    println(y.joinToString(""))
                }
            }
        }
    }

    override fun second(): Long {

        return 0
    }

}