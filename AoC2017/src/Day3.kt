import kotlin.coroutines.experimental.buildSequence

fun main(args: Array<String>) {
    val spiral: Sequence<Pos> = buildSequence {
        var pos = p(0, 0)
        yield(pos)
        var side = 0
        while(true) {
            for(i in 0..(side / 2)) {
                pos += dirs[side % 4]
                yield(pos)
            }
            side++
        }
    }

    val map = mutableMapOf(p(0, 0) to 1)
    val nb = dirs + listOf(p(1, 1), p(-1, 1), p(-1, -1), p(1, -1))
    spiral.drop(1).forEach { pos ->
        val sum = nb.mapNotNull { map[pos + it] }.sum()
        map[pos] = sum
        //println("$pos -> $sum")
        if(sum >= 289326) {
            println(sum)
            return
        }
    }
}

val dirs = listOf(p(1, 0), p(0, 1), p(-1, 0), p(0, -1))
data class Pos(val x: Int, val y: Int)
fun p(x: Int, y: Int) = Pos(x, y)
operator fun Pos.plus(p: Pos) = Pos(this.x + p.x, this.y + p.y)