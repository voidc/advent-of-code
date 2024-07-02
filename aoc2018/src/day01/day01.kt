package day01

import java.io.File

fun main(args: Array<String>) {
    val seen = mutableSetOf<Int>()
    //File(".").list().forEach(::println)
    val sum = File("./src/day01/input.txt").useLines { lines ->
        lines.map(String::toInt)
            .repeat()
            .cumulate(0, Int::plus)
            //.onEach(::println)
            .first { !seen.add(it) }
    }
    println(sum)
}

fun <T, R> Sequence<T>.cumulate(initial: R, op: (R, T) -> R) = sequence {
    var acc = initial
    yield(acc)
    for (e in this@cumulate) {
        acc = op(acc, e)
        yield(acc)
    }
}

fun <T> Sequence<T>.repeat() = toList().let { generateSequence { it }.flatten() }
