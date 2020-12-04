package com.blommish.aoc2020.util

abstract class Line {

    protected abstract fun first(lines: List<String>): Int
    protected abstract fun second(lines: List<String>): Int

    fun execute(mapper: (s: String) -> List<String>, vararg filename: String) {
        val input = filename.toList().map { it to mapper.invoke(AocUtil.readFile(it)) }
        println("Inputs: ${input.size}")
        input.forEach {
            println("\n ${it.first}")
            println("   First ${first(it.second)}")
            println("   Second ${second(it.second)}")
        }
    }
}