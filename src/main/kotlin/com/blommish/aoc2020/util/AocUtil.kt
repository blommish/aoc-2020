package com.blommish.aoc2020.util

class AocUtil {
    companion object {

        fun readFile(name: String): String {
            println("Reading $name")
            return AocUtil::class.java.classLoader.getResource(name).readText()
        }

        fun readLines(name: String): List<String> {
            return readFile(name).split("\n").toList()
        }
    }
}

fun MatchResult?.value(int: Int): String = this!!.groups[int]!!.value
fun MatchResult?.intValue(int: Int): Int = this!!.groups[int]!!.value.toInt()
fun MatchResult?.optValue(int: Int): String? = this!!.groups[int]!!.value