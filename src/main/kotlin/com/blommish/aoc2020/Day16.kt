package com.blommish.aoc2020

import com.blommish.aoc2020.util.DayT
import com.blommish.aoc2020.util.withLines

fun main() {
    withLines(::Day16, { it.split("\n") },
            //"16_test.txt",
            //"16_test2.txt",
              "16_1.txt")
}

private class Day16(val lines: List<String>) : DayT<Long> {

    override fun first(): Long {
        val ranges = mutableSetOf<Int>()
        var i = 0
        var line = lines[i]
        fun addRange(range: String) {
            val (start, end) = range.split("-").map { it.toInt() }
            for (ii in start..end) {
                ranges.add(ii)
            }
        }
        while (line.contains(" or ")) {
            val (range1, range2) = line.split(": ")[1].split(" or ")
            addRange(range1)
            addRange(range2)
            line = lines[++i]
        }
        while (!line.contains("nearby")) {
            line = lines[++i]
        }
        i++
        val notValid = mutableListOf<Int>()
        while (i < lines.size) {
            lines[i++].split(",").map { it.toInt() }.forEach {
                if (!ranges.contains(it)) {
                    notValid.add(it)
                }
            }
        }
        return notValid.sum().toLong()
    }

    //855275529001
    override fun second(): Long {
        var myTicket: List<Int> = emptyList()
        val rangeNumbers = mutableSetOf<Int>()
        val ranges = mutableMapOf<Int, MutableSet<Int>>()
        var i = 0
        var line = lines[i]
        fun addRange(name: Int, range: String) {
            val (start, end) = range.split("-").map { it.toInt() }
            val r = mutableSetOf<Int>()
            for (ii in start..end) {
                r.add(ii)
            }
            rangeNumbers.addAll(r)
            ranges.getOrPut(name, { mutableSetOf() }).addAll(r)
        }
        while (line.contains(" or ")) {
            val split = line.split(": ")
            val (range1, range2) = split[1].split(" or ")
            addRange(i, range1)
            addRange(i, range2)
            line = lines[++i]
        }
        while (!line.contains("nearby")) {
            line = lines[++i]
            if (line.contains(",")) {
                myTicket = line.split(",").map { it.toInt() }
            }
        }
        i++
        val validTickets = mutableListOf<List<Int>>()
        while (i < lines.size) {
            val numbers = lines[i++].split(",").map { it.toInt() }
            if (numbers.all { rangeNumbers.contains(it) }) {
                validTickets.add(numbers)
            }
        }
        val mappings = findMappings(ranges, validTickets)
        return myTicket.mapIndexed { index, i -> mappings[index]!! to i.toLong() }
                .filter { it.first < 6 }.map { it.second }
                .reduce { acc, l -> acc * l }
    }

    private fun findMappings(ranges: MutableMap<Int, MutableSet<Int>>,
                             validTickets: MutableList<List<Int>>): Map<Int, Int> {
        val result = ranges.map { it.key to mutableSetOf<Int>() }.toMap().toMutableMap()
        validTickets.forEachIndexed { ticketIndex, ticket ->
            if (ticketIndex == 0) {
                ticket.forEachIndexed { index, i ->
                    val matches = ranges.filter { it.value.contains(i) }
                    matches.keys.singleOrNull()?.let { key ->
                        result.values.forEach { v -> v.remove(key) }
                    }
                    result[index]!!.addAll(matches.keys)
                }
            } else {
                ticket.forEachIndexed { index, i ->
                    val matches = ranges.filterNot { it.value.contains(i) }
                    matches.keys.forEach { key ->
                        result[index]!!.remove(key)
                    }
                }
            }
        }
        while (result.values.any { it.size > 1 }) {
            result.forEach { outer ->
                outer.value.singleOrNull()?.let {
                    result.forEach { inner ->
                        if (outer.key != inner.key) {
                            inner.value.remove(it)
                        }
                    }
                }
            }
        }
        return result.map { it.key to it.value.single() }.toMap()
    }

    private fun printResult(result: MutableMap<Int, MutableSet<Int>>) {
        println("\n == === = == = = = ")
        result.toSortedMap().forEach {
            println("${it.key} - ${it.value.sorted()}")
        }
    }

}