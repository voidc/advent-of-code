import java.io.File
import kotlin.math.abs

fun main(args: Array<String>) {
    val firewall = File("./inputs/input13.txt").useLines { lines ->
        lines.associate {
            val (depth, range) = it.split(": ")
            depth.toInt() to range.toInt()
        }
    }

    val scanners = firewall.mapValuesTo(mutableMapOf()) { 0 }

    fun scannerStep(n: Int = 1) {
        scanners.forEach { k, v ->
            scanners[k] = (v + n) % (2 * firewall[k]!! - 2)
        }
    }

    fun scannerPos(depth: Int): Int {
        val max = firewall[depth]!! - 1
        return max - abs(scanners[depth]!! - max)
    }

    fun resetScanners() {
        scanners.forEach { k, _ -> scanners[k] = 0 }
    }

    val delay = generateSequence(1, Int::inc).find { d ->
        resetScanners()
        scannerStep(d)
        (0 until 100).none { step ->
            val cought = firewall.containsKey(step) && scannerPos(step) == 0
            scannerStep()
            cought
        }
    }

    println(delay)
}