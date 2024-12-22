fun main() {
    fun parse(input: List<String>): List<Long> {
        return input.map { it.toLong() }
    }

    fun mix(given: Long, secret: Long): Long {
        return given xor secret
    }

    fun prune(secret: Long): Long {
        return secret % 16777216
    }

    tailrec fun pseudoRandomProcess(
        secret: Long,
        iterations: Int,
        last: Int,
        history: MutableList<Int>,
        sequences: HashMap<List<Int>, Int>,
        seen: HashSet<List<Int>>
    ): Long {
        if (iterations <= 0) {
            return secret
        }
        val result = prune(mix(secret * 64, secret))
        val result1 = prune(mix(result / 32, result))
        val result2 = prune(mix(result1 * 2048, result1))
        val currentLast = result2.toString().last().digitToInt()
        history.add(currentLast - last)
        if (history.size == 4) {
            if (!seen.contains(history)) {
                sequences[history.toList()] = (sequences[history] ?: 0) + currentLast
            }
            seen.add(history.toList())
            history.removeFirst()
        }
        return pseudoRandomProcess(result2, iterations - 1, currentLast, history, sequences, seen)
    }

    fun part1(input: List<Long>): Long {
        return input.sumOf {
            pseudoRandomProcess(it, 2000, Int.MAX_VALUE, mutableListOf(), hashMapOf(), HashSet())
        }
    }

    fun part2(input: List<Long>): Int {
        val sequences = hashMapOf<List<Int>, Int>()
        input.forEach {
            val seen = hashSetOf<List<Int>>()
            pseudoRandomProcess(it, 2000, Int.MAX_VALUE, mutableListOf(), sequences, seen)
        }
        return sequences.values.max()
    }

    // Read a large test input from the `src/Day22_test1.txt` file:
    val testInput1 = parse(readInput("Day22_test1"))
    check(part1(testInput1) == 37327623L)

    // Read a large test input from the `src/Day22_test2.txt` file:
    val testInput2 = parse(readInput("Day22_test2"))
    check(part2(testInput2) == 23)

    // Read the input from the `src/Day22.txt` file.
    val input = parse(readInput("Day22"))
    println("Running input")
    part1(input).println()
    part2(input).println()
}
