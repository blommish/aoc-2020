package com.blommish.aoc2020

import com.blommish.aoc2020.util.Line

fun main() {
    santa().execute({ it.split("\n\n") }, "4_test.txt", "4_1.txt", "4_valid.txt", "4_invalid.txt")
}

private fun santa() = object : Line() {

    val byr = Regex("byr:(\\d+)(\\n| |$)")
    val iyr = Regex("iyr:(\\d+)(\\n| |$)")
    val eyr = Regex("eyr:(\\d+)(\\n| |$)")
    val hgt = Regex("hgt:(\\d+)(cm|in)(\\n| |$)")
    val hcl = Regex("hcl:#[0-9a-f]{6}(\\n| |$)")
    val ecl = Regex("ecl:(amb|blu|brn|gry|grn|hzl|oth)(\\n| |$)")
    val pid = Regex("pid:(\\d{9})(\\n| |$)")

    override fun first(lines: List<String>): Int {
        val keys = "byr iyr eyr hgt hcl ecl pid".split(" ").toSet()
        return lines.count { line -> keys.all { line.contains(it) } }
    }

    override fun second(lines: List<String>): Int {
        return lines.count { line ->
            val byrVal = byr.find(line)?.let { it.groups[1]?.value?.toInt() in 1920..2002 } ?: false
            val iyrVal = iyr.find(line)?.let { it.groups[1]?.value?.toInt() in 2010..2020 } ?: false
            val eyrVal = eyr.find(line)?.let { it.groups[1]?.value?.toInt() in 2020..2030 } ?: false
            val hgtVal = hgt.find(line)?.let {
                val toInt = it.groups[1]?.value?.toInt()
                val value = it.groups[2]?.value
                if (value == "cm") {
                    toInt in 150..193
                } else {
                    toInt in 59..76
                }
            } ?: false
            val hclVal = hcl.find(line) != null
            val eclVal = ecl.find(line) != null
            val pidVal = pid.find(line) != null

            byrVal && iyrVal && eyrVal && hgtVal && hclVal && eclVal && pidVal
        }
    }
}
