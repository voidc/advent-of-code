import java.io.File

val programs = mutableMapOf<String,Program7>()
fun main(args: Array<String>) {
    val input = File("./inputs/input7.txt")
    val programRegex = Regex("([a-z]+) \\((\\d+)\\)")
    val childRegex = Regex(" ([a-z]+),?")
    input.useLines { lines ->
        lines.forEach { line ->
            val (name, weight) = programRegex.find(line)!!.destructured
            val children = childRegex.findAll(line).map{ it.groupValues[1] }
            programs.put(name, Program7(name, weight.toInt(), children.toList()))
        }
    }

    val root = programs["eqgvf"]!!
    println(root.totalWeight())
}

data class Program7(val name: String, val weight: Int, val children: List<String>)

fun Program7.totalWeight(): Int {
    if(children.isEmpty())
        return weight

    val childrenWeights = children.map { programs[it]!!.totalWeight() }
    val oio = childrenWeights.oddIndexOut()
    if(oio != null) {
        val w = childrenWeights[oio]
        val c = programs[children[oio]]!!
        println("Error at $name: Child $c has weight $w ($childrenWeights)")
    }
    return weight + childrenWeights.sum()
}

fun <T> List<T>.allEqual() = this.isEmpty() || this.all { this[0]!! == it }
fun <T> List<T>.oddOneOut(): T? = when {
    this.size < 3 -> null
    this[0] == this[1] -> this.find { it != this[0] }
    this[0] == this[2] -> this[1]
    this[1] == this[2] -> this[0]
    else -> null
}

fun <T> List<T>.oddIndexOut(): Int? = when {
    this.size < 3 -> null
    this[0] == this[1] -> this.indexOfFirst { it != this[0] }.takeIf { it >= 0 }
    this[0] == this[2] -> 1
    this[1] == this[2] -> 0
    else -> null
}