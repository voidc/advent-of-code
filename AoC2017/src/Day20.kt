import java.io.File
import java.util.*
import kotlin.math.abs

fun main(args: Array<String>) {
    val regx = Regex("p=<(-?\\d+),(-?\\d+),(-?\\d+)>, v=<(-?\\d+),(-?\\d+),(-?\\d+)>, a=<(-?\\d+),(-?\\d+),(-?\\d+)>")

    val particles = File("./inputs/input20.txt").useLines { lines ->
        lines.mapIndexed { i, line ->
            val (px, py, pz, vx, vy, vz, ax, ay, az) = regx.matchEntire(line)!!.destructured
            Particle(i, px.toInt(), py.toInt(), pz.toInt(),
                    vx.toInt(), vy.toInt(), vz.toInt(),
                    ax.toInt(), ay.toInt(), az.toInt())
        }.toList()
    }

    val comp = Comparator.comparingInt<Particle> { abs(it.ax) + abs(it.ay) + abs(it.az) }
            .thenComparingInt { abs(it.vx) + abs(it.vy) + abs(it.vz) }
            .thenComparingInt { abs(it.px) + abs(it.py) + abs(it.pz) }

    particles.sortedWith(comp).take(10).forEach(::println)
}


data class Particle(val index: Int,
                    val px: Int, val py: Int, val pz: Int,
                    val vx: Int, val vy: Int, val vz: Int,
                    val ax: Int, val ay: Int, val az: Int)

