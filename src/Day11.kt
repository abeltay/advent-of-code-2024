fun main() {
    tailrec fun blink(stones: Map<Long, Long>, iterations: Int): Long {
        if (iterations == 0) {
            var ans: Long = 0
            stones.forEach { ans += it.value }
            return ans
        }
        val next = mutableMapOf<Long, Long>()
        stones.forEach {
            val s = it.key.toString()
            val new = when {
                it.key == 0.toLong() -> 1
                s.length % 2 == 0 -> {
                    val i1 = s.substring(0, s.length / 2).toLong()
                    var n1 = next[i1] ?: 0
                    n1 += it.value
                    next[i1] = n1
                    val i2 = s.substring(s.length / 2).toLong()
                    i2
                }

                else -> it.key * 2024
            }
            var n = next[new] ?: 0
            n += it.value
            next[new] = n
        }
        return blink(next, iterations - 1)
    }

    fun part1(input: List<String>, rounds: Int): Long {
        val stones = mutableMapOf<Long, Long>()
        input[0].split(" ").forEach { stones[it.toLong()] = 1 }
        return blink(stones, rounds)
    }

    // Read a large test input from the `src/Day11_test.txt` file:
    val testInput = readInput("Day11_test")
    check(part1(testInput, 6) == 22.toLong())
    check(part1(testInput, 25) == 55312.toLong())

    // Read the input from the `src/Day11.txt` file.
    val input = readInput("Day11")
    println("Running input")
    part1(input, 25).println()
    part1(input, 75).println()
}
