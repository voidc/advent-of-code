package day8

import useInput
import java.lang.IllegalArgumentException

data class Node(val metadata: List<Int>, val children: List<Node>)
val Node.value: Int get() =
    if (children.isEmpty()) metadata.sum()
    else metadata.sumBy { children.getOrNull(it - 1)?.value ?: 0 }

fun main(args: Array<String>) {
    val input = useInput { lines ->
        lines.first().split(" ").map { it.trim().toInt() }
    }
    val root = parse(input)
    println(sumMeta(root))
    println(root.value)
    println(Node(listOf(1, 1), listOf(Node(listOf(1, 2), listOf()))).value)
}

fun sumMeta(node: Node): Int = node.metadata.sum() + node.children.sumBy(::sumMeta)

fun parse(input: List<Int>): Node {
    fun parseNode(start: Int): Pair<Node, Int> {
        if (input.size - start < 2)
            throw IllegalArgumentException("Too few values!")

        val childCount = input[start]
        val metaCount = input[start + 1]

        val (children, metaStart) = (0 until childCount).fold(emptyList<Node>() to start + 2) { (cs, s), _ ->
            val (c, s2) = parseNode(s)
            cs + c to s2
        }

        val metadata = input.slice(metaStart until metaStart + metaCount)

        return Node(metadata, children) to metaStart + metaCount
    }

    return parseNode(0).first
}