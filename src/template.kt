fun main() {
    fun parse(input: List<String>): List<String> {
        return input
    }

    fun part1(input: List<String>): Long {
        return input.size.toLong()
    }

    fun part2(input: List<String>): Long {
        return input.size.toLong()
    }

    // Read a large test input from the `src/Day{TEMPLATE}_test1.txt` file:
    val testInput1 = parse(readInput("Day{TEMPLATE}_test1"))
    check(part1(testInput1) == 0L)
    // check(part2(parse(testInput1)) == 1L)

    // Read a large test input from the `src/Day{TEMPLATE}_test2.txt` file:
    // val testInput2 = parse(readInput("Day{TEMPLATE}_test2"))
    // check(part1(testInput2) == 1L)

    // Read the input from the `src/Day{TEMPLATE}.txt` file.
    val input = parse(readInput("Day{TEMPLATE}"))
    println("Running input")
    part1(input).println()
    part2(input).println()
}
