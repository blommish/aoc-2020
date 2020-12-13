package com.blommish.aoc2020

import com.blommish.aoc2020.util.DayT
import com.blommish.aoc2020.util.withLines
import kotlin.math.ceil

fun main() {
    withLines(::Day13, {it.split("\n")},"13_test.txt", "13_1.txt")
}

private class Day13(val lines: List<String>) : DayT<Long> {

    override fun first(): Long {
        val (first, second) = lines
        val earliest = first.toInt()
        val activeIds = second.split(",").filterNot { it == "x" }.map { it.toInt() }
        val map = activeIds.map {
            it to (ceil(earliest / it.toDouble()) * it).toInt() - earliest
        }.minByOrNull { it.second }!!
        return (map.first * map.second).toLong()
    }

    //600689120448303
    override fun second(): Long {
        val line = lines[1]
        val timetable = line.split(",").mapIndexedNotNull { index, s ->
            if (s == "x") return@mapIndexedNotNull null
            index to s.toInt()
        }.toMap()
        val highest = timetable.maxByOrNull { it.value }!!
        var time: Long = highest.value.toLong()
        val filteredTimetable = timetable.filter { it.key != highest.key }
        while (true) {
            val all = filteredTimetable.all {
                (time - highest.key + it.key) % it.value == 0L
            }
            if (all) {
                return time - highest.key
            }
            time += highest.value
        }
    }

}