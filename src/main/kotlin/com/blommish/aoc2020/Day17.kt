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
    private val cubeSize = initSize + cycles * 2 + 4
    private val middleIndex = (cubeSize - 1) / 2

    override fun first(): Long {
        var cube = initCube()
        for (i in 0 until cycles) {
            val range = 1..(initSize + cycles * 2 + 2)
            val tmpArray = initCubeArray()
            for (z in range) {
                for (y in range) {
                    for (x in range) {
                        for (z2 in range) {
                            val current = cube[z][y][x][z2]
                            val count = count(cube, z, y, x, z2)
                            if (current == '#' && (count == 3 || count == 4)) {
                                tmpArray[z][y][x][z2] = '#'
                            } else if (current == '.' && count == 3) {
                                tmpArray[z][y][x][z2] = '#'
                            }
                        }
                    }
                }
            }
            cube = tmpArray
            //print(cube)
        }
        return cube.flatten().flatMap { it.toList() }.flatMap { it.toList() }.count { it == '#' }.toLong()
    }

    private fun initCube(): Array<Array<Array<Array<Char>>>> {
        val cube = initCubeArray()
        lines.forEachIndexed { lineIndex, line ->
            line.toCharArray().forEachIndexed { columnIndex, s ->
                cube[middleIndex][middleIndex - 1 + lineIndex][middleIndex - 1 + columnIndex][middleIndex] = s
            }
        }
        return cube
    }

    private fun initCubeArray() = Array(cubeSize) { Array(cubeSize) { Array(cubeSize) { Array(cubeSize) { '.' } } } }

    private fun count(cube: Array<Array<Array<Array<Char>>>>, z: Int, y: Int, x: Int, z2: Int): Int {
        var count = 0
        for (zCount in neighbourIndexes) {
            for (yCount in neighbourIndexes) {
                for (xCount in neighbourIndexes) {
                    for (z2Count in neighbourIndexes) {
                        if (cube[z + zCount][y + yCount][x + xCount][z2 + z2Count] == '#') {
                            count++
                        }
                        if (count > 4) return count
                    }
                }
            }
        }
        return count
    }

    override fun second(): Long {

        return 0
    }

}