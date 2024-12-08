fun main() {
    fun process(input: String): Int {
        val multiplierRegex = """mul\((\d{1,3}),(\d{1,3})\)""".toRegex()
        val result = multiplierRegex.findAll(input)
        var ans = 0
        result.forEach {
            val (a, b) = it.destructured
            ans += a.toInt() * b.toInt()
        }
        return ans
    }

    fun part1(input: List<String>): Int {
        var combined = ""
        input.forEach {
            combined += it
        }
        return process(combined)
    }

    fun shortener(input: String): String {
        val idxDont = input.indexOf("don't()")
        if (idxDont == -1) {
            return input
        }
        val leftover = input.substring(idxDont + 6)
        val idxDo = leftover.indexOf("do()")
        if (idxDo == -1) {
            return input.substring(0, idxDont)
        }
        return input.substring(0, idxDont) + shortener(leftover.substring(idxDo + 3))
    }

    fun part2(input: List<String>): Int {
        var combined = ""
        input.forEach {
            combined += it
        }
        val short = shortener(combined)
        return process(short)
    }

    // Test if implementation meets criteria from the description, like:
    // check(part1(listOf("test_input")) == 11)

    // Or read a large test input from the `src/Day03_test.txt` file:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 161)
    val testInput2 = readInput("Day03_test2")
    check(part2(testInput2) == 48)

    // Read the input from the `src/Day03.txt` file.
    val input = readInput("Day03")
    println("Running input")
    part1(input).println()
    part2(input).println()
}
