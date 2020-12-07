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
    input.forEach {
        println("\n ${it.first}")
        val day = dayMapper(it.second)
        println("   First ${day.first()}")
        println("   Second ${day.second()}")
    }
}
