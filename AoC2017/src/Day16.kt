import java.io.File

fun main(args: Array<String>) {
    val moves = File("./inputs/input16.txt").readText().split(',').map {
        when(it[0]) {
            's' -> DanceMove.Spin(it.substring(1).toInt())
            'x' -> {
                val (a, b) = it.substring(1).split('/')
                DanceMove.Exchange(a.toInt(), b.toInt())
            }
            'p' -> {
                val (a, b) = it.substring(1).split('/')
                DanceMove.Partner(a[0], b[0])
            }
            else -> throw RuntimeException("Illegal move! " + it)
        }
    }

    fun dance(order: String): String {
        val list = order.toMutableList()
        moves.forEach { move ->
            when(move) {
                is DanceMove.Spin -> {
                    list.rotateRight(move.x)
                }
                is DanceMove.Exchange -> {
                    list.swap(move.a, move.b)
                }
                is DanceMove.Partner -> {
                    list.swap(list.indexOf(move.a), list.indexOf(move.b))
                }
            }
        }
        return list.joinToString("")
    }

    var i = 0L
    var order = "abcdefghijklmnop"
    while(i < 40L) {
        order = dance(order)
        i++
        println(order)
    }
}

sealed class DanceMove {
    data class Spin(val x: Int): DanceMove()
    data class Exchange(val a: Int, val b: Int): DanceMove()
    data class Partner(val a: Char, val b: Char): DanceMove()
}

fun <T> MutableList<T>.rotateRight(n: Int) {
    repeat(n) {
        val last = removeAt(size - 1)
        add(0, last)
    }
}