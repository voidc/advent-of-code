package day03

import useInput

data class Rect(val id: Int, val x: Int, val y: Int, val w: Int, val h: Int)
val rectPattern = Regex("#(\\d+) @ (\\d+),(\\d+): (\\d+)x(\\d+)")

fun main(args: Array<String>) {
    val claims = useInput { lines ->
        lines.map {
            val (id, x, y, w, h) = rectPattern.matchEntire(it)!!.destructured
            Rect(id.toInt(), x.toInt(), y.toInt(), w.toInt(), h.toInt())
        }.toList()
    }

    println(findNoOverlap(claims))

}

fun countOverlap(claims: List<Rect>): Int {
    val width = claims.map { it.x + it.w }.max()!!
    val height = claims.map { it.y + it.h }.max()!!
    println("width: $width, height: $height")
    val fabric = Array(width) { Array(height) { 0 } }
    claims.forEach {
        for (i in it.x until (it.x + it.w)) {
            for (j in it.y until (it.y + it.h)) {
                fabric[i][j] = when (fabric[i][j]) {
                    0 -> 1
                    else -> 2
                }
            }
        }
    }
    return fabric.sumBy { a -> a.count { it == 2 } }
}

fun findNoOverlap(claims: List<Rect>): List<Rect> {
    val width = claims.map { it.x + it.w }.max()!!
    val height = claims.map { it.y + it.h }.max()!!
    val fabric = Array(width) { Array(height) { 0 } }
    val noOverlap = mutableListOf<Rect>()

    claims.forEach {
        var overlap = false
        for (i in it.x until (it.x + it.w)) {
            for (j in it.y until (it.y + it.h)) {
                if (fabric[i][j] == 0) {
                    fabric[i][j] = it.id
                } else {
                    overlap = true
                    noOverlap.removeIf { it.id == fabric[i][j] }
                }
            }
        }
        if (!overlap)
            noOverlap += it
    }
    return noOverlap
}