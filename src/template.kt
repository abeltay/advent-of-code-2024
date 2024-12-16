fun main() {
    fun part1(input: List<String>): Int {
        return input.size
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // Test if implementation meets criteria from the description, like:
    // val testInput1 = readInput("Day{TEMPLATE}_test1")
    // check(part1(testInput1) == 1)

    // Read a large test input from the `src/Day{TEMPLATE}_test.txt` file:
    val testInput = readInput("Day{TEMPLATE}_test")
    check(part1(testInput) == 1)
    // check(part2(testInput) == 1)

    // Read the input from the `src/Day{TEMPLATE}.txt` file.
    val input = readInput("Day{TEMPLATE}")
    println("Running input")
    part1(input).println()
    part2(input).println()
}
