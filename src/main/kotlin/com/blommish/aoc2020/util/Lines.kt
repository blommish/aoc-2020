package com.blommish.aoc2020.util

abstract class Lines<T> {

    protected abstract fun first(lines: List<T>): Int
    protected abstract fun second(lines: List<T>): Int

    fun execute(mapper: (s: String) -> T, vararg filename: String) {
        val input = filename.toList().map { it to AocUtil.readLines(it).map(mapper) }
        println("First")
        input.forEach { println("${it.first} ${first(it.second)}") }
        println("\nSecond")
        input.forEach { println("${it.first} ${second(it.second)}") }
    }
}