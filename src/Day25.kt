fun main() {
    fun parseItem(input: List<String>, keys: MutableList<List<Int>>, locks: MutableList<List<Int>>) {
        val key = input[0] != "#####"
        val range = if (key) {
            input.indices.reversed()
        } else {
            input.indices
        }
        val height = MutableList(5) { 0 }
        var first = true
        for (row in range) {
            if (first) {
                first = false
                continue
            }
            for (column in input[row].indices) {
                if (input[row][column] == '#') {
                    height[column] = height[column] + 1
                }
            }
        }
        if (key) {
            keys.add(height)
        } else {
            locks.add(height)
        }
    }

    fun doesFit(lock: List<Int>, key: List<Int>): Boolean {
        for (i in lock.indices) {
            if (lock[i] + key[i] > 5) {
                return false
            }
        }
        return true
    }

    fun part1(input: List<String>): Int {
        val keys = mutableListOf<List<Int>>()
        val locks = mutableListOf<List<Int>>()
        var i = 0
        while (i < input.size) {
            parseItem(input.subList(i, i + 7), keys, locks)
            i += 8
        }

        var ans = 0
        for (lock in locks) {
            for (key in keys) {
                if(doesFit(lock, key)){
                    ans++
                }
            }
        }
        return ans
    }

    // Read a large test input from the `src/Day25_test1.txt` file:
    val testInput1 = readInput("Day25_test1")
    check(part1(testInput1) == 3)

    // Read a large test input from the `src/Day25_test2.txt` file:
    // val testInput2 = parse(readInput("Day25_test2"))
    // check(part1(testInput2) == 1L)

    // Read the input from the `src/Day25.txt` file.
    val input = readInput("Day25")
    println("Running input")
    part1(input).println()
}
