fun main() {
    fun convertInput(input: List<String>): Pair<MutableList<Int>, MutableList<Int>> {
        val inputLineRegex = """(\d+)\s*(\d+)""".toRegex()
        val left = mutableListOf<Int>()
        val right = mutableListOf<Int>()
        input.map {
            val (l, r) = inputLineRegex
                .matchEntire(it)
                ?.destructured
                ?: throw IllegalArgumentException("Incorrect input line $it")
            left.add(l.toInt())
            right.add(r.toInt())
        }
        return Pair(left, right)
    }

    fun part1(input: List<String>): Int {
        val pairs = convertInput(input)
        pairs.first.sort()
        pairs.second.sort()
        var ans = 0
        for (i in 0 until pairs.first.size) {
            var diff = pairs.first[i] - pairs.second[i]
            if (diff < 0) diff *= -1
            ans += diff
        }
        return ans
    }

    fun part2(input: List<String>): Int {
        val pairs = convertInput(input)
        val freq = mutableMapOf<Int, Int>()
        pairs.second.forEach {
            var n = freq[it] ?: 0
            n++
            freq[it] = n
        }
        var ans = 0
        pairs.first.forEach {
            val f = freq[it] ?: 0
            ans += it * f
        }
        return ans
    }

    // Test if implementation meets criteria from the description, like:
    // check(part1(listOf("test_input")) == 11)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 11)
    check(part2(testInput) == 31)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
