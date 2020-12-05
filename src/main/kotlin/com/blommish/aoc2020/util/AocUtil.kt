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

fun MatchResult?.charVal(index: Int): Char = value(index).toCharArray().single()
fun MatchResult?.value(index: Int): String = optValue(index)!!
fun MatchResult?.intValue(index: Int): Int = optIntValue(index)!!

fun MatchResult?.optIntValue(index: Int): Int? = optValue(index)?.toInt()
fun MatchResult?.optValue(index: Int): String? = this?.groups?.get(index)?.value