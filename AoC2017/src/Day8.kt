import java.io.File

val registers = mutableMapOf<String, Int>()
val maxValue = 0

fun main(args: Array<String>) {
    val input = File("./inputs/input8.txt")
    val regex = Regex("([a-z]+) (inc|dec) (-?\\d+) if ([a-z]+) (<|<=|==|!=|>=|>) (-?\\d+)")

    input.useLines { lines ->
        lines.map {
            val (reg, op, value, cReg, cOp, cValue) = regex.matchEntire(it)!!.destructured
            val i = Day8.Instruction(reg, op, value.toInt(), cReg, cOp, cValue.toInt())
            Day8.execute(i)
        }.max().also(::println)
    }

    println(registers.maxBy { it.value })
}

object Day8 {
    fun execute(i: Instruction): Int {
        if(checkCondition(registers[i.condReg] ?: 0, i.condValue, i.condOp)) {
            val newValue = when(i.op) {
                "inc" -> (registers[i.reg] ?: 0) + i.value
                "dec" -> (registers[i.reg] ?: 0) - i.value
                else -> throw RuntimeException()
            }
            registers[i.reg] = newValue
            return newValue
        }
        return 0
    }

    fun checkCondition(a: Int, b: Int, op: String) = when(op) {
        "<" -> a < b
        "<=" -> a <= b
        "==" -> a == b
        "!=" -> a != b
        ">=" -> a >= b
        ">" -> a > b
        else -> throw RuntimeException()
    }

    data class Instruction(val reg: String,
                           val op: String,
                           val value: Int,
                           val condReg: String,
                           val condOp: String,
                           val condValue: Int)
}