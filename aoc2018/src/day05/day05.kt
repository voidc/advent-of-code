package day05

import useInput

fun main(args: Array<String>) {
    val polymer = useInput { lines -> lines.first().toList() }
    val minZize = ('A'..'Z').map { c ->
        reactAll(polymer.filter { !it.equals(c, ignoreCase = true) }).size
    }.min()
    println(minZize)
}

fun reactAll(polymer: List<Char>): List<Char> {
    fun reacts(a: Char, b: Char) = a.equals(b, ignoreCase = true) && a.isLowerCase() != b.isLowerCase()
    var poly = polymer
    var reacting = true
    while (reacting) {
        reacting = false
        val newPoly = mutableListOf<Char>()
        var last: Char? = null
        for (c in poly) {
            if (last != null && reacts(last, c)) {
                reacting = true
                last = null
            } else {
                if (last != null) newPoly += last
                last = c
            }
        }
        if (last != null) newPoly += last
        poly = newPoly
    }
    return poly
}