package com.blommish.aoc2020

import com.blommish.aoc2020.util.Lines

fun main() {
    santaFirstSolution().execute({ it }, "5_test.txt", "5_1.txt")
    santaSecondSolution().execute({ it }, "5_test.txt", "5_1.txt")
}

private fun santaSecondSolution() = object : Lines<String>() {

    val regex0 = Regex("F|L")
    val regex1 = Regex("B|R")

    override fun first(lines: List<String>): Int {
        return seats(lines).maxOf { it.first * 8 + it.second }
    }

    override fun second(lines: List<String>): Int {
        val intRange = 0..7
        val seats = seats(lines).groupBy({ it.first }, { it.second })
        val mySeat = seats.entries.first { it.value.size != 8 }.let { k -> k.key to intRange.first { !k.value.contains(it) } }
        return mySeat.first * 8 + mySeat.second
    }

    private fun seats(lines: List<String>): List<Pair<Int, Int>> = lines
            .map { it.replace(regex0, "0").replace(regex1, "1") }
            .map { line ->
                val row = line.substring(0, 7).toInt(2)
                val column = line.substring(7, 10).toInt(2)
                Pair(row, column)
            }
}

private fun santaFirstSolution() = object : Lines<String>() {

    override fun first(lines: List<String>): Int {
        return lines.maxOf { line ->
            val row = calc(line.subSequence(0, 7), 127, 'F')
            val column = calc(line.subSequence(7, 10), 7, 'L')
            val id = row * 8 + column
            id
        }
    }

    override fun second(lines: List<String>): Int {
        val seats = lines.map { line ->
            val row = calc(line.subSequence(0, 7), 127, 'F')
            val column = calc(line.subSequence(7, 10), 7, 'L')
            Pair(row, column)
        }.groupBy({ it.first }, { it.second })
        val intRange = 0..7
        val mySeat = seats.entries.first { it.value.size != 8 }.let { k -> k.key to intRange.first { !k.value.contains(it) } }
        return mySeat.first * 8 + mySeat.second
    }

    private fun calc(charSequence: CharSequence, end: Int, lowerChar: Char) = charSequence
            .fold(Pair(0, end),
                  { acc, c ->
                      val second = (acc.second - acc.first).div(2) + acc.first
                      if (c == lowerChar)
                          Pair(acc.first, second)
                      else
                          Pair(second, acc.second)
                  }).second
}
