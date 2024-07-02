
import java.io.File
import kotlin.math.abs

fun main(args: Array<String>) {
    val input = File("./inputs/input11.txt").readText()
            .split(',')

    var max = p(0, 0)
    val dest = input.fold(p(0, 0)) { pos, dir ->
        max = if(pos.hexDist() > max.hexDist()) pos else max
        when(dir) {
            "ne" -> p(pos.x + 1, pos.y)
            "n" -> p(pos.x, pos.y + 1)
            "nw" -> p(pos.x - 1, pos.y + 1)
            "sw" -> p(pos.x - 1, pos.y)
            "s" -> p(pos.x, pos.y - 1)
            "se" -> p(pos.x + 1, pos.y - 1)
            else -> throw RuntimeException()
        }
    }

    println(dest.hexDist())
    println(max.hexDist())
}

fun Pos.hexDist() = if(x * y >= 0) abs(x) + abs(y) else abs(y)