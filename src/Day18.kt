import java.util.*

fun main() {
    data class Finder(
        val location: Pair<Int, Int>,
        val cost: Int,
    )

    fun parse(input: List<String>): List<Pair<Int, Int>> {
        return input.map {
            val s = it.split(",")
            Pair(s[0].toInt(), s[1].toInt())
        }
    }

    fun drop(item: Pair<Int, Int>, map: List<MutableList<Char>>) {
        map[item.second][item.first] = '#'
    }

    fun allCardinals(loc: Pair<Int, Int>): List<Pair<Int, Int>> {
        return listOf(
            Pair(loc.first + 1, loc.second),
            Pair(loc.first - 1, loc.second),
            Pair(loc.first, loc.second + 1),
            Pair(loc.first, loc.second - 1),
        )
    }

    fun explore(
        map: List<List<Char>>,
        explored: List<MutableList<Boolean>>,
        queue: PriorityQueue<Finder>,
        end: Int
    ): Int {
        val finder = queue.poll()
        if (finder.location.first == end && finder.location.second == end) {
            return finder.cost
        }
        val l = allCardinals(finder.location)
        for (loc in l) {
            if (loc.first < 0 || loc.first > end || loc.second < 0 || loc.second > end) {
                continue
            }
            if (explored[loc.second][loc.first] || map[loc.second][loc.first] == '#') {
                continue
            }
            explored[loc.second][loc.first] = true
            queue.add(Finder(Pair(loc.first, loc.second), finder.cost + 1))
        }
        return -1
    }

    fun explorer(map: List<List<Char>>, size: Int): Int {
        val explored = List(size + 1) { MutableList(size + 1) { false } }
        val queue = PriorityQueue<Finder>(compareBy { c -> c.cost })
        explored[0][0] = true
        queue.add(Finder(Pair(0, 0), 0))
        while (queue.isNotEmpty()) {
            val cost = explore(map, explored, queue, size)
            if (cost != -1) {
                return cost
            }
        }
        return -1
    }

    fun part1(input: List<Pair<Int, Int>>, size: Int, iterations: Int): Int {
        val map = List(size + 1) { MutableList(size + 1) { '.' } }
        for (i in 0 until iterations) {
            drop(input[i], map)
        }
        return explorer(map, size)
    }

    fun part2(input: List<Pair<Int, Int>>, size: Int, iterations: Int): String {
        val map = List(size + 1) { MutableList(size + 1) { '.' } }
        var dropPtr = -1
        while (dropPtr < iterations) {
            dropPtr++
            drop(input[dropPtr], map)
        }
        while (true) {
            val cost = explorer(map, size)
            if (cost == -1) {
                return input[dropPtr].first.toString() + "," + input[dropPtr].second.toString()
            }
            dropPtr++
            drop(input[dropPtr], map)
        }
    }

    // Read a large test input from the `src/Day18_test1.txt` file:
    val testInput1 = readInput("Day18_test1")
    check(part1(parse(testInput1), 6, 12) == 22)
    check(part2(parse(testInput1), 6, 12) == "6,1")

    // Read the input from the `src/Day18.txt` file.
    val input = readInput("Day18")
    println("Running input")
    part1(parse(input), 70, 1024).println()
    part2(parse(input), 70, 1024).println()
}
