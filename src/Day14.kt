data class Robot(
    var x: Int,
    var y: Int,
    val vx: Int,
    val vy: Int,
)

fun main() {
    fun convert(input: List<String>): List<Robot> {
        val inputLineRegex = """p=(-?\d+),(-?\d+) v=(-?\d+),(-?\d+)""".toRegex()
        return input.map {
            val (x, y, vx, vy) = inputLineRegex
                .matchEntire(it)
                ?.destructured
                ?: throw IllegalArgumentException("Incorrect input line $it")
            Robot(x.toInt(), y.toInt(), vx.toInt(), vy.toInt())
        }
    }

    fun move(robots: List<Robot>, maxX: Int, maxY: Int) {
        robots.forEach {
            var x = it.x + it.vx
            if (x < 0) {
                x += maxX
            } else if (x >= maxX) {
                x -= maxX
            }
            var y = it.y + it.vy
            if (y < 0) {
                y += maxY
            } else if (y >= maxY) {
                y -= maxY
            }
            it.x = x
            it.y = y
        }
    }

    fun part1(input: List<String>, maxX: Int, maxY: Int, duration: Int): Int {
        val c = convert(input)
        for (i in 0 until duration) {
            move(c, maxX, maxY)
        }
        val halfX = maxX / 2
        val halfY = maxY / 2
        var q1 = 0
        var q2 = 0
        var q3 = 0
        var q4 = 0
        c.forEach {
            when {
                it.y < halfY && it.x < halfX -> q1++
                it.y < halfY && it.x > halfX -> q2++
                it.y > halfY && it.x < halfX -> q3++
                it.y > halfY && it.x > halfX -> q4++
                else -> {}
            }
        }
        return q1 * q2 * q3 * q4
    }

    fun foundTree(robots: List<Robot>): Boolean {
        for (i in robots.indices) {
            if (
                robots.any { it.x == robots[i].x && it.y == robots[i].y + 1 } &&
                robots.any { it.x == robots[i].x - 1 && it.y == robots[i].y + 1 } &&
                robots.any { it.x == robots[i].x && it.y == robots[i].y + 1 } &&
                robots.any { it.x == robots[i].x + 1 && it.y == robots[i].y + 1 } &&
                robots.any { it.x == robots[i].x - 2 && it.y == robots[i].y + 2 } &&
                robots.any { it.x == robots[i].x - 1 && it.y == robots[i].y + 2 } &&
                robots.any { it.x == robots[i].x && it.y == robots[i].y + 2 } &&
                robots.any { it.x == robots[i].x + 1 && it.y == robots[i].y + 2 } &&
                robots.any { it.x == robots[i].x + 2 && it.y == robots[i].y + 2 }
            ) {
                return true
            }
        }
        return false
    }

    fun part2(input: List<String>, maxX: Int, maxY: Int): Int {
        val c = convert(input)
        var count = 0
        while (!foundTree(c)) {
            move(c, maxX, maxY)
            count++
        }
        for (y in 0..maxY) {
            for (x in 0..maxX) {
                if (c.any { it.x == x && it.y == y }) {
                    print("#")
                } else {
                    print(" ")
                }
            }
            println()
        }
        return count
    }

    // Read a large test input from the `src/Day14_test.txt` file:
    val testInput = readInput("Day14_test")
    check(part1(testInput, 11, 7, 100) == 12)

    // Read the input from the `src/Day14.txt` file.
    val input = readInput("Day14")
    println("Running input")
    part1(input, 101, 103, 100).println()
    part2(input, 101, 103).println()
}
