import java.io.File

fun main(args: Array<String>) {
    data class Instruction(val ins: String, val op1: String, val op2: String?)

    val code = File("./inputs/input18.txt").useLines { lines ->
        lines.map {
            val parts = it.split(' ')
            Instruction(parts[0], parts[1], parts.getOrNull(2))
        }.toList()
    }

    //code.forEach(::println)

    val registers = mutableMapOf<String, Long>()
    var sound = 0L
    fun valOrReg(s: String) = s.toLongOrNull() ?: registers[s] ?: 0L
    code.progress { i, (ins, op1, op2) ->
        println("$i: $ins $op1 $op2 $registers")
        when(ins) {
            "set" -> registers[op1] = valOrReg(op2!!)
            "add" -> registers[op1] = valOrReg(op1) + valOrReg(op2!!)
            "mul" -> registers[op1] = valOrReg(op1) * valOrReg(op2!!)
            "mod" -> registers[op1] = valOrReg(op1) % valOrReg(op2!!)
            "snd" -> sound = valOrReg(op1)
            "rcv" -> {
                if(valOrReg(op1) != 0L)
                    return@progress -1
            }
            "jgz" -> {
                if(valOrReg(op1) > 0)
                    return@progress i + valOrReg(op2!!).toInt()
            }
            else -> throw RuntimeException()
        }
        i + 1
    }

    println(sound)
}

fun <T> List<T>.progress(start: Int = 0, f: (Int, T) -> Int) {
    var i = start
    while(i in indices) {
        i = f(i, this[i])
    }
}