import kotlin.math.floor
import kotlin.math.min

data class Machine(
    var a: Pair<Int, Int> = Pair(0, 0),
    var b: Pair<Int, Int> = Pair(0, 0),
    var prize: Pair<Long, Long> = Pair(0, 0)
)

fun main() {
    fun extractXY(s: String, splitter: String): Pair<Int, Int> {
        val pos = s.split(": ")[1].split(", ")
        return Pair(pos[0].split(splitter)[1].toInt(), pos[1].split(splitter)[1].toInt())
    }

    fun dist(m: Machine, pressA: Long, pressB: Long): Pair<Long, Long> {
        val x = m.a.first.toLong() * pressA + m.b.first.toLong() * pressB
        val y = m.a.second.toLong() * pressA + m.b.second.toLong() * pressB
        return Pair(x, y)
    }

    fun minAB(m: Machine): Long {
        var pressB = min(m.prize.first / m.b.first, m.prize.second / m.b.second)
        var travelled = dist(m, 0, pressB)
        var pressA =
            min((m.prize.first - travelled.first) / m.a.first, (m.prize.second - travelled.second) / m.a.second)
        travelled = dist(m, pressA, pressB)
        while (travelled.first != m.prize.first || travelled.second != m.prize.second) {
            if (travelled.first > m.prize.first || travelled.second > m.prize.second) {
                pressB--
            } else {
                pressA++
            }
            if (pressB < 0 || pressA > 100) {
                return 0
            }
            travelled = dist(m, pressA, pressB)
        }
        return pressA * 3 + pressB
    }

    fun math(m: Machine): Long {
        val ax = m.a.first.toDouble()
        val ay = m.a.second.toDouble()
        val bx = m.b.first.toDouble()
        val by = m.b.second.toDouble()
        val px = m.prize.first.toDouble()
        val py = m.prize.second.toDouble()
        val b = (ay * px - ax * py) / ((ay * bx - ax * by))
        val a = (px - bx * b) / ax

        val ra = floor(a).toLong()
        val rb = floor(b).toLong()
        if (ax * ra + bx * rb == px && ay * ra + by * rb == py && ra > 0 && rb > 0) {
            return ra * 3 + rb
        }
        return 0
    }

    fun part1(input: List<String>): Long {
        var m = Machine()
        var pos = 0
        val machines = mutableListOf<Machine>()
        input.forEach {
            when (pos) {
                0 -> m.a = extractXY(it, "+")
                1 -> m.b = extractXY(it, "+")
                2 -> {
                    val prize = extractXY(it, "=")
                    m.prize = Pair(prize.first.toLong(), prize.second.toLong())
                }

                else -> {
                    machines.add(m)
                    pos = -1
                    m = Machine()
                }
            }
            pos++
        }
        machines.add(m)

        var ans: Long = 0
        machines.forEach {
            val result = math(it)
            ans += result
        }
        return ans
    }

    fun part2(input: List<String>): Long {
        var m = Machine()
        var pos = 0
        val machines = mutableListOf<Machine>()
        input.forEach {
            when (pos) {
                0 -> m.a = extractXY(it, "+")
                1 -> m.b = extractXY(it, "+")
                2 -> {
                    val prize = extractXY(it, "=")
                    m.prize = Pair(prize.first + 10000000000000, prize.second + 10000000000000)
                }

                else -> {
                    machines.add(m)
                    pos = -1
                    m = Machine()
                }
            }
            pos++
        }
        machines.add(m)

        var ans: Long = 0
        machines.forEach {
            val result = math(it)
            ans += result
        }
        return ans
    }

    // Read a large test input from the `src/Day13_test.txt` file:
    val testInput = readInput("Day13_test")
    check(part1(testInput) == 480.toLong())
    part2(testInput)

    // Read the input from the `src/Day13.txt` file.
    val input = readInput("Day13")
    println("Running input")
    part1(input).println()
    part2(input).println()
}
