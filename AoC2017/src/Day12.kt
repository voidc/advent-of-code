import java.io.File

fun main(args: Array<String>) {
    val programs = mutableMapOf<Int, MutableSet<Int>>()
    val regex1 = Regex("(\\d+) <->")
    val regex2 = Regex(" (\\d+),?")

    File("./inputs/input12.txt").useLines { lines ->
        lines.forEach { line ->
            val id = regex1.find(line)!!.groupValues[1].toInt()
            val connections = regex2.findAll(line).map { it.groupValues[1].toInt() }
            programs.getOrPut(id, {mutableSetOf()}).addAll(connections)
            connections.forEach { programs.getOrPut(it, {mutableSetOf()}).add(id) }
        }
    }

    fun group(id: Int, visited: MutableSet<Int> = mutableSetOf()): Set<Int> {
        visited += id
        programs[id]!!.forEach {
            if(it !in visited)
                group(it, visited)
        }
        return visited
    }

    println(group(0).size)

    val groups = mutableListOf<Set<Int>>()
    programs.keys.forEach { id ->
        if(groups.none { id in it })
            groups.add(group(id))
    }

    println(groups.size)

}