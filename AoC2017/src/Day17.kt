fun main(args: Array<String>) {
    val step = 324
    //val list = mutableListOf(0)
    var pos = 1
    var size = 1
    var res = 0
    repeat(50000000) { i ->
        pos = ((pos + step) % size) + 1
        if(pos == 1)
            res = i + 1
        size++
    }
    println(res)
}