import java.io.File
import java.util.*

fun main(args: Array<String>) {
    val input = File("./inputs/input6.txt")
    val blocks = input.readText().split('\t').map{it.trim().toInt()}.toMutableList()
    val history = mutableMapOf<Int, Int>()
    var i = 0
    while(true) {
        val hash = hashBlocks(blocks)
        println("$blocks -> $hash")
        if(hash in history) {
            println(i - history[hash]!!)
            break
        }
        history.put(hash, i)
        reallocate(blocks)
        i++
    }
}

fun reallocate(blocks: MutableList<Int>) {
    val maxIndex = blocks.foldIndexed(0) { i, max, v ->
        if(v > blocks[max]) i else max
    }
    val maxValue = blocks[maxIndex]
    blocks[maxIndex] = 0
    for(i in 1..maxValue) {
        blocks[(maxIndex + i) % blocks.size]++
    }
}

fun hashBlocks(blocks: List<Int>): Int {
    return Arrays.hashCode(blocks.toIntArray())
}