import java.io.File

fun main(args: Array<String>) {
    val input = File("./inputs/input2.txt")
    a2(input)
}

fun a1(input: File) {
    input.useLines { lines ->
        val sum = lines.sumBy { line ->
            line.splitToSequence('\t').map(String::toInt).let {
                it.max()!! - it.min()!!
            }
        }
        println(sum)
    }
}

fun a2(input: File) {
    input.useLines { lines ->
        val sum = lines.sumBy { line ->
            line.splitToSequence('\t')
                    .map(String::toInt)
                    .pairs()
                    .find { it.first != it.second && it.first > it.second && it.first % it.second == 0}!!
                    .let { it.first / it.second }

        }
        println(sum)
    }
}

fun <T> Sequence<T>.pairs(): Sequence<Pair<T, T>> = this.flatMap { a -> this.map { b -> a to b }}