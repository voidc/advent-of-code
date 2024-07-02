
import java.io.File

fun main(args: Array<String>) {
    val input = File("./inputs/input1.txt")
            .readText()
            .map(Character::getNumericValue)

    val n = input.size / 2
    val sum = input.foldIndexed(0) { i, acc, v ->
        val next = input[(i + n) % input.size]
        if(v == next) acc + v else acc
    }

    println(sum)
}
