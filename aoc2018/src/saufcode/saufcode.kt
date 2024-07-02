package saufcode

import java.io.File

val primes = readPrimes()
var goodMask: Long = -0x37bf3fbfb7bfbfc0L // 0xC840C04048404040 computed below

fun readPrimes() = File("src/saufcode/primes.txt")
    .useLines { it.map(String::toInt).toList().toIntArray() }

fun calcMask(): Long {
    var goodMask = 0L
    for (i in 0 until 64)
        goodMask = goodMask or (Long.MIN_VALUE ushr (i*i))
    return goodMask
}

fun isSquare(x: Long): Boolean {
    var x = x
    // This tests if the 6 least significant bits are right.
    // Moving the to be tested bit to the highest position saves us masking.
    if (goodMask shl x.toInt() >= 0) return false
    val numberOfTrailingZeros = java.lang.Long.numberOfTrailingZeros(x)
    // Each square ends with an even number of zeros.
    if (numberOfTrailingZeros and 1 != 0) return false
    x = x shr numberOfTrailingZeros
    // Now x is either 0 or odd.
    // In binary each odd square ends with 001.
    // Postpone the sign test until now; handle zero in the branch.
    if ((x and 7 != 1L) or (x <= 0)) return x == 0L
    // Do it in the classical way.
    // The correctness is not trivial as the conversion from long to double is lossy!
    val tst = Math.sqrt(x.toDouble()).toLong()
    return tst * tst == x
}

fun getSolution() {
    var n = 9L
    var pi = 4

    while (true) {
        if (n == primes[pi].toLong()) {
            pi++
            n += 2L
            continue
        }

        var none = true
        for (i in 0 until pi) {
            val p = primes[i]
            val a = (n - p) / 2L
            if(isSquare(a)) {
                println("$n = $p + 2 * $a")
                none = false
                break
            }
        }

        if (none)
            break

        n += 2L
    }
    print(n)
}

fun main(args: Array<String>) {
    getSolution()
}