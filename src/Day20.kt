import kotlin.math.abs

fun main() {
    fun parse(input: List<String>): List<List<Char>> {
        return input.map { it.toList() }
    }

    fun findChar(map: List<List<Char>>, target: Char): Pair<Int, Int> {
        for (i in map.indices) {
            for (j in map[i].indices) {
                if (map[i][j] == target) {
                    return Pair(j, i)
                }
            }
        }
        return Pair(-1, -1)
    }

    fun move(last: Pair<Int, Int>): List<Pair<Int, Int>> {
        return listOf(
            Pair(last.first + 1, last.second),
            Pair(last.first - 1, last.second),
            Pair(last.first, last.second + 1),
            Pair(last.first, last.second - 1),
        )
    }

    fun findRaceCourse(map: List<List<Char>>): List<Pair<Int, Int>> {
        val route = mutableListOf(findChar(map, 'S'))
        val end = findChar(map, 'E')
        while (route.last().first != end.first || route.last().second != end.second) {
            val last = route.last()
            val cardinals = move(last)
            for (cardinal in cardinals) {
                if (map[cardinal.second][cardinal.first] != '#' && !route.contains(cardinal)) {
                    route.add(cardinal)
                    break
                }
            }
        }
        return route
    }

    fun cheat(course: List<Pair<Int, Int>>, target: Int, moves: Int): Int {
        var ans = 0
        for (i in course.indices) {
            for (j in i + 1 until course.size) {
                val start = course[i]
                val end = course[j]
                val manhattanDistance = abs(start.first - end.first) + abs(start.second - end.second)
                if (manhattanDistance > moves) {
                    continue
                }
                val savings = j - i - manhattanDistance
                if (savings >= target) {
                    ans++
                }
            }
        }
        return ans
    }

    fun part1(map: List<List<Char>>, target: Int): Int {
        val course = findRaceCourse(map)
        val cheats = cheat(course, target, 2)
        return cheats
    }

    fun part2(map: List<List<Char>>, target: Int): Int {
        val course = findRaceCourse(map)
        val cheats = cheat(course, target, 20)
        return cheats
    }

    // Read a large test input from the `src/Day20_test1.txt` file:
    val testInput1 = parse(readInput("Day20_test1"))
    check(part1(testInput1, 40) == 2)
    check(part2(testInput1, 76) == 3)

    // Read the input from the `src/Day20.txt` file.
    val input = parse(readInput("Day20"))
    println("Running input")
    part1(input, 100).println()
    part2(input, 100).println()
}
