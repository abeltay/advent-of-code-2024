fun main() {
    fun convertInput(input: List<String>):List<List<Int>> {
        return input.map { ii ->
            val s = ii.split(" ")
            s.map { it.toInt() }
        }
    }

    fun isSafe(input: List<Int>): Boolean {
        val increasing = input[0] <= input[1] - 1
        for (i in 1 until input.size) {
            val diff = if (increasing) {
                input[i] - input[i - 1]
            } else {
                input[i - 1] - input[i]
            }
            if (diff < 1 || diff > 3) {
                return false
            }
        }
        return true
    }

    fun part1(input: List<String>): Int {
        val convert = convertInput(input)
        var ans = 0
        convert.forEach {
            if (isSafe(it)) {
                ans++
            }
        }
        return ans
    }

    fun part2(input: List<String>): Int {
        val convert = convertInput(input)
        var ans = 0
        convert.forEach {
            for (i in it.indices){
                val l = it.toMutableList()
                l.removeAt(i)
                if (isSafe(l)) {
                    ans++
                    break
                }
            }
        }
        return ans
    }

    // Test if implementation meets criteria from the description, like:
    // check(part1(listOf("test_input")) == 11)

    // Or read a large test input from the `src/Day02_test.txt` file:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    // Read the input from the `src/Day02.txt` file.
    val input = readInput("Day02")
    println("Running input")
    part1(input).println()
    part2(input).println()
}
