fun main(args: Array<String>) {
    val seqA = generateSequence(722L) { (it * 16807L) % 2147483647L }
            .filter { it % 4L == 0L }
    val seqB = generateSequence(354L) { (it * 48271L) % 2147483647L }
            .filter { it % 8L == 0L }

    val pairs = seqA.zip(seqB)
    val matches = pairs.take(5000000).count { (a, b) ->
        (a and 0xffff) == (b and 0xffff)
    }

    println(matches)
}