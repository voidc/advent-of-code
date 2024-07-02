fun main(args: Array<String>) {
    val input = "106,118,236,1,130,0,235,254,59,205,2,87,129,25,255,118"
    //val input = "AoC 2017".split(',').map(String::toInt)

    val result = knotHash(input)
    //println(result[0] * result[1])

    val hash = condense(result)
    println(hash)
}

fun condense(hash: List<Int>) = hash
        .chunked(16) { it.fold(0) { acc, v -> acc xor v } }
        .joinToString(separator = "") { it.toString(16).padStart(2, '0') }

fun knotHash(input: String) = knotHash(input.map(Char::toInt) + listOf(17, 31, 73, 47, 23))
fun knotHash(input: List<Int>, rounds: Int = 64): List<Int> {
    val result = (0 until 256).toMutableList()
    var pos = 0
    var skip = 0
    repeat(rounds) {
        input.forEach { len ->
            result.reverseSubList(pos, pos + len)
            pos += len + skip
            skip++
        }
    }
    return result
}

fun <T> MutableList<T>.reverseSubList(from: Int, to: Int) {
    for (i in 0 until ((to - from) / 2)) {
        swap((from + i) % size, (to - i - 1) % size)
    }
}

fun <T> MutableList<T>.swap(indexA: Int, indexB: Int) {
    val temp = this[indexA]
    this[indexA] = this[indexB]
    this[indexB] = temp
}