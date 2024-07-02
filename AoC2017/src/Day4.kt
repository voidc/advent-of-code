import java.io.File

fun main(args: Array<String>) {
    val input = File("./inputs/input4.txt")
    input.useLines { lines ->
        val valid = lines.count { line ->
            line.split(' ').map { it.toList().sorted().joinToString() }.let {
                it.distinct().size == it.size
            }
        }
        println(valid)
    }
}