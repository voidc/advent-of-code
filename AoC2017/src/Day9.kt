import java.io.File

fun main(args: Array<String>) {
    val input = File("./inputs/input9.txt")
    val text = input.readText()
    var level = 0L
    var garbage = false
    var garbageCount = 0
    var ignore = false
    var score = 0L
    for (c in text) {
        if (garbage) {
            if (ignore)
                ignore = false
            else when(c) {
                '!' -> ignore = true
                '>' -> garbage = false
                else -> garbageCount++
            }
        } else {
            when(c) {
                '<' -> garbage = true
                '{' -> level++
                '}' -> {
                    score += level
                    level--
                }
            }
        }
    }
    println("$score $garbageCount")
}