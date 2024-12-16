fun main() {
    fun part1(input: List<String>): Int {
        return input.size
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // Read a large test input from the `src/Day{TEMPLATE}_test1.txt` file:
    val testInput1 = readInput("Day{TEMPLATE}_test1")
    check(part1(testInput1) == 1)
    // check(part2(testInput1) == 1)

    // Read a large test input from the `src/Day{TEMPLATE}_test2.txt` file:
    // val testInput2 = readInput("Day{TEMPLATE}_test2")
    // check(part1(testInput2) == 1)

    // Read the input from the `src/Day{TEMPLATE}.txt` file.
    val input = readInput("Day{TEMPLATE}")
    println("Running input")
    part1(input).println()
    part2(input).println()
}
