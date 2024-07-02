fun main(args: Array<String>) {
    val input = "xlqgujun"
    val grid = Array(128) { i ->
        val hash = knotHashBinary(input + "-" + i)
        Array(128) { j -> Character.getNumericValue(hash[j]) }
    }

    fun fill(row: Int, col: Int, group: Int) {
        grid[row][col] = group
        for(dir in dirs) {
            val r = row + dir.y
            val c = col + dir.x
            if(r in grid.indices && c in grid[row].indices && grid[r][c] == 1)
                fill(r, c, group)
        }
    }

    var groups = 0
    for(row in grid.indices)
        for(col in grid[row].indices)
            if(grid[row][col] == 1)
                fill(row, col, 2 + groups++)

    println(groups)
}



fun knotHashBinary(input: String): String {
    return knotHash(input)
            .chunked(16) { it.fold(0) { acc, v -> acc xor v } }
            .joinToString(separator = "") {
                it.toString(2).padStart(8, '0')
            }
}