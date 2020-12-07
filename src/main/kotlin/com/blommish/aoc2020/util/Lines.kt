package com.blommish.aoc2020.util

abstract class Lines<T> {

    protected abstract fun first(lines: List<T>): Int
    protected abstract fun second(lines: List<T>): Int

    fun execute(mapper: (s: String) -> T, vararg filename: String) {
        val input = filename.toList().map { it to AocUtil.readLines(it).map(mapper) }
        println("Inputs: ${input.size}")
        input.forEach {
            println("\n ${it.first}")
            println("   First ${first(it.second)}")
            println("   Second ${second(it.second)}")
        }
    }
}