package day02

import id
import useInput

fun main(args: Array<String>) {
    println(useInput(::findIDs))
}

fun checksum(lines: Sequence<String>): Int {
    val (two, three) = lines
        .map { it.groupingBy(::id).eachCount() }
        .fold(0 to 0) { (two, three), map ->
            two + (if (2 in map.values) 1 else 0) to three + (if (3 in map.values) 1 else 0)
        }

    return two * three
}

fun hammingDistance(a: String, b: String) = a.zip(b).count { (c1, c2) -> c1 != c2 }

fun <T> Sequence<T>.choose2() = sequence {
    val l = mutableListOf<T>()
    for (e in this@choose2) {
        yieldAll(l.map { it to e })
        l += e
    }
}

fun findIDs(lines: Sequence<String>): Pair<String, String>? {
    return lines.choose2().find { (a, b) -> hammingDistance(a, b) == 1 }
}