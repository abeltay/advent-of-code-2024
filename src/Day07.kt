data class Function(
    val total: Long,
    val values: List<Long>
)

fun main() {
    fun convert(input: String): Function {
        val s = input.split(": ")
        return Function(
            total = s[0].toLong(),
            values = s[1].split(" ").map { it.toLong() }
        )
    }

    fun isValid(target: Long, values: List<Long>, includeConcat: Boolean = false): Boolean {
        if (values.size == 1) {
            return values[0] == target
        }
        val add = values[0] + values[1]
        val newAdd = mutableListOf(add)
        newAdd.addAll(values.drop(2))
        if (isValid(target, newAdd, includeConcat)) {
            return true
        }
        val mult = values[0] * values[1]
        val newMult = mutableListOf(mult)
        newMult.addAll(values.drop(2))
        if (isValid(target, newMult, includeConcat)) {
            return true
        }
        if (!includeConcat) {
            return false
        }
        val concat = (values[0].toString() + values[1].toString()).toLong()
        val newConcat = mutableListOf(concat)
        newConcat.addAll(values.drop(2))
        return isValid(target, newConcat, includeConcat)
    }

    fun part1(input: List<String>): Long {
        var ans: Long = 0
        input.forEach {
            val c = convert(it)
            if (isValid(c.total, c.values)) {
                ans += c.total
            }
        }
        return ans
    }

    fun part2(input: List<String>): Long {
        var ans: Long = 0
        input.forEach {
            val c = convert(it)
            if (isValid(c.total, c.values, includeConcat = true)) {
                ans += c.total
            }
        }
        return ans
    }

    // Test if implementation meets criteria from the description, like:
    // check(part1(listOf("test_input")) == 11)

    // Or read a large test input from the `src/Day07_test.txt` file:
    val testInput = readInput("Day07_test")
    check(part1(testInput) == 3749.toLong())
    check(part2(testInput) == 11387.toLong())

    // Read the input from the `src/Day07.txt` file.
    val input = readInput("Day07")
    println("Running input")
    part1(input).println()
    part2(input).println()
}
