package com.blommish.aoc2020.util

interface Day {

    fun first(): Int
    fun second(): Int
}

fun withLines(dayMapper: (lines: List<String>) -> Day, vararg filename: String) {
    withFile(dayMapper, { it.split("\n") }, *filename)
}

fun withFile(dayMapper: (lines: List<String>) -> Day,
             fileMapper: (fileStr: String) -> List<String> = { it.split("\n") },
             vararg filename: String) {
    val input = filename.toList().map { it to fileMapper.invoke(AocUtil.readFile(it)) }
    println("Inputs: ${input.size}")
    fun withTimer(name: String, timed: () -> Int) {
        val start = System.currentTimeMillis()
        val response = timed.invoke()
        val end = System.currentTimeMillis() - start
        println("   $name answer:$response time:${end}ms")
    }
    input.forEach {
        println("\n ${it.first}")
        val day = dayMapper(it.second)
        withTimer("First", day::first)
        withTimer("Second", day::second)
    }
}
