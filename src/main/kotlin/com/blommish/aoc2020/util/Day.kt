package com.blommish.aoc2020.util

interface Day: DayT<Int>

interface DayT<T> {

    fun first(): T
    fun second(): T
}

fun withLines(dayMapper: (lines: List<String>) -> Day, vararg filename: String) {
    withLines(dayMapper, { it.split("\n") }, *filename)
}

fun <T, D> withLines(dayMapper: (lines: List<T>) -> DayT<D>,
                  fileMapper: (fileStr: String) -> List<T>,
                  vararg filename: String) {
    val input = filename.toList().map { it to fileMapper.invoke(AocUtil.readFile(it)) }
    println("Inputs: ${input.size}")
    fun withTimer(name: String, timed: () -> D) {
        val start = System.currentTimeMillis()
        val response = timed.invoke()
        val end = System.currentTimeMillis() - start
        println("\n====$name answer:$response time:${end}ms")
    }
    input.forEach {
        println("\n ${it.first}")
        val day = dayMapper(it.second)
        withTimer("First", day::first)
        withTimer("Second", day::second)
    }
}
