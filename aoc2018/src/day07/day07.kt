package day07

import useInput

data class Vertex(val id: Char,
                  val outE: MutableList<Char> = mutableListOf(),
                  val inE: MutableList<Char> = mutableListOf())

fun main(args: Array<String>) {
    val vertices = ('A'..'Z').associateWith { Vertex(it) }
    useInput { lines ->
        val pattern = Regex("Step ([A-Z]) must be finished before step ([A-Z]) can begin.")
        lines.forEach {
            val (from, to) = pattern.matchEntire(it)!!.destructured
            vertices[from[0]]!!.outE += to[0]
            vertices[to[0]]!!.inE += from[0]
        }
    }

    print(topSort(vertices).joinToString(separator = ""))
}

fun topSort(vertices: Map<Char, Vertex>): List<Char> {
    val sorted = mutableSetOf<Char>()
    val sources = vertices.values.filter { it.inE.isEmpty() }.mapTo(mutableSetOf()) { it.id }

    while(sources.isNotEmpty()) {
        val s = sources.min()!!
        sources.remove(s)

        sorted.add(s)

        vertices[s]!!.outE.map { vertices[it]!! }.forEach { u ->
            u.inE.remove(s)
            if (u.inE.isEmpty())
                sources.add(u.id)
        }

    }

    return sorted.toList()
}

//fun topSort(vertices: Map<Char, Vertex>): MutableSet<Char> {
//    val sorted = mutableSetOf<Char>()
//    val sources = vertices.values.filter { it.inE.isEmpty() }
//
//    sources.flatMapAppend { v ->
//        if (sorted.add(v.id)) {
//            v.outE.map { vertices[it]!! }.mapNotNull { u ->
//                u.inE.remove(v.id)
//                u.takeIf { u.inE.isEmpty() }
//            }
//        } else emptyList()
//
//    }
//
//    return sorted
//}
//
//fun <T> Iterable<T>.flatMapAppend(op: (T) -> Iterable<T>) =
//    generateSequence(this) { l ->
//        l.flatMap { op(it) }.takeIf { it.isNotEmpty() }
//    }.flatten()