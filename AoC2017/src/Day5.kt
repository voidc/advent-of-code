import java.io.File

fun main(args: Array<String>) {
    val input = File("./inputs/input5.txt")
    input.useLines { lines ->
        val list = lines.map(String::toInt).toMutableList()
        generateSequence(0) {
            val j = list[it]
            list[it] = when {
                j >= 3 -> j - 1
                else -> j + 1
            }
            it + j
        }.takeWhile { it in list.indices }
                .count()
                .also(::println)
    }
}