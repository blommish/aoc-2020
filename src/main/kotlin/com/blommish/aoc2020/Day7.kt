package com.blommish.aoc2020

import com.blommish.aoc2020.util.*

fun main() {
    withLines(::Day7, "7_test.txt", "7_test2.txt", "7_1.txt")
}

data class TreeNode(val color: String,
                    val children: MutableList<Pair<Int, TreeNode>> = mutableListOf())

private class Day7(val lines: List<String>): Day {
    val findColor = "shiny gold"
    val regex = Regex("(((\\d) )?(\\S+ \\S+)) bag")

    override fun first(): Int {
        return buildTree(lines).values.filter { it.color != findColor }.count { hasChild(it) }
    }

    private fun hasChild(it: TreeNode): Boolean {
        return it.color == findColor || it.children.any { hasChild(it.second) }
    }

    override fun second(): Int {
        return sumChildren(buildTree(lines)[findColor]!!) - 1
    }

    private fun sumChildren(it: TreeNode): Int {
        return 1 + it.children.sumOf { it.first * sumChildren(it.second) }
    }

    private fun buildTree(lines: List<String>): MutableMap<String, TreeNode> {
        val tree = mutableMapOf<String, TreeNode>()

        lines.forEach { line ->
            buildTree(line, tree)
        }
        return tree
    }

    private fun buildTree(line: String, tree: MutableMap<String, TreeNode>) {
        var matchResult = regex.find(line)

        fun getOrDefault(tree: MutableMap<String, TreeNode>, color: String): TreeNode = tree.getOrPut(color, { TreeNode(color) })

        val outerColor = matchResult!!.groups[1]!!.value
        val node = getOrDefault(tree, outerColor)
        while (true) {
            matchResult = matchResult!!.next()
            if (matchResult == null || matchResult.value == "no other bag") {
                break
            } else {
                node.children.add(Pair(matchResult.intValue(3), getOrDefault(tree, matchResult.value(4))))
            }
        }
        tree[outerColor] = node
    }

}
