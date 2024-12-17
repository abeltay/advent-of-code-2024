fun main() {
    fun parse(input: List<String>): List<MutableList<Char>> {
        return input.map { it.toMutableList() }
    }

    fun part1(input: List<MutableList<Char>>): Int {
        return input.size
    }

    fun part2(input: List<MutableList<Char>>): Int {
        return input.size
    }

    // Read a large test input from the `src/Day{TEMPLATE}_test1.txt` file:
    val testInput1 = readInput("Day{TEMPLATE}_test1")
    check(part1(parse(testInput1)) == 1)
    // check(part2(parse(testInput1)) == 1)

    // Read a large test input from the `src/Day{TEMPLATE}_test2.txt` file:
    // val testInput2 = readInput("Day{TEMPLATE}_test2")
    // check(part1(parse(testInput2)) == 1)

    // Read the input from the `src/Day{TEMPLATE}.txt` file.
    val input = readInput("Day{TEMPLATE}")
    println("Running input")
    part1(parse(input)).println()
    part2(parse(input)).println()
}
