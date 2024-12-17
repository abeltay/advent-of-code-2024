import kotlin.math.pow

fun main() {
    fun convert(input: List<String>): Pair<List<Long>, Long> {
        return Pair(
            input[4].split(": ")[1].split(",").map { it.toLong() },
            input[0].split(": ")[1].toLong()
        )
    }

    fun runProgram(program: List<Long>, startA: Long, noLoop: Boolean = false): List<Long> {
        var a: Long = startA
        var b: Long = 0
        var c: Long = 0
        var pointer = 0
        val out = mutableListOf<Long>()
        while (pointer < program.size) {
            val combo = when (program[pointer + 1]) {
                4L -> a
                5L -> b
                6L -> c
                else -> program[pointer + 1]
            }
            when (program[pointer]) {
                0L -> {
                    val power = 2.toDouble().pow(combo.toDouble())
                    val result = a / power
                    a = result.toLong()
                }

                1L -> {
                    val result = b xor program[pointer + 1]
                    b = result
                }

                2L -> {
                    val result = combo % 8
                    b = result
                }

                3L -> {
                    if (a != 0.toLong() && !noLoop) {
                        pointer = program[pointer + 1].toInt() - 2
                    }
                }

                4L -> {
                    val result = b xor c
                    b = result
                }

                5L -> {
                    val op = when (program[pointer + 1]) {
                        4.toLong() -> a
                        5.toLong() -> b
                        else -> c
                    }
                    out.add(op % 8)
                }

                6L -> {
                    val result = a / 2.toDouble().pow(combo.toDouble())
                    b = result.toLong()
                }

                else -> {
                    val result = a / 2.toDouble().pow(combo.toDouble())
                    c = result.toLong()
                }
            }
            pointer += 2
        }
        return out
    }

    fun part1(input: List<String>): String {
        val c = convert(input)
        val out = runProgram(c.first, c.second)
        return out.joinToString(",") { it.toString() }
    }

    fun solver(program: List<Long>, end: List<Long>): Long {
        val a = if (end.size > 1) {
            8 * solver(program, end.subList(1, end.size))
        } else {
            0
        }
        var smallA = 0
        while (true) {
            val curA = a + smallA
            val out = runProgram(program, curA)
            if (end == out) {
                return curA
            }
            smallA++
        }
    }

    /*
        B = A % 8
        Other commands
        C = assigned from A and B
        A = A / 8
        A != 0 Jump to beginning
    */
    fun part2(input: List<String>): Long {
        val c = convert(input)
        return solver(c.first, c.first)
    }

    // Read a large test input from the `src/Day17_test1.txt` file:
    val testInput1 = readInput("Day17_test1")
    check(part1(testInput1) == "4,6,3,5,6,3,5,2,1,0")

    // Read a large test input from the `src/Day17_test2.txt` file:
     val testInput2 = readInput("Day17_test2")
     check(part2(testInput2) == 117440.toLong())

    // Read the input from the `src/Day17.txt` file.
    val input = readInput("Day17")
    println("Running input")
    part1(input).println()
    part2(input).println()
}