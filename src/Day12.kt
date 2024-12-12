fun main() {
    fun convert(input: List<String>): MutableList<MutableList<String>> {
        return input.map { it.map { it.toString() }.toMutableList() }.toMutableList()
    }

    fun generateCardinals(x: Int, y: Int): List<Pair<Int, Int>> {
        return listOf(
            Pair(x - 1, y),
            Pair(x + 1, y),
            Pair(x, y - 1),
            Pair(x, y + 1),
        )
    }

    fun valid(map: MutableList<MutableList<String>>, region: String, x: Int, y: Int): Pair<Int, Int>? {
        if (x < 0 || x >= map[0].size || y < 0 || y >= map.size || map[y][x] != region) {
            return null
        }
        return Pair(x, y)
    }

    fun corners(map: MutableList<MutableList<String>>, region: String, x: Int, y: Int): Int {
        val l = listOf(
            valid(map, region, x, y - 1) != null,
            valid(map, region, x + 1, y - 1) != null,
            valid(map, region, x + 1, y) != null,
            valid(map, region, x + 1, y + 1) != null,
            valid(map, region, x, y + 1) != null,
            valid(map, region, x - 1, y + 1) != null,
            valid(map, region, x - 1, y) != null,
            valid(map, region, x - 1, y - 1) != null,
        )
        var corner = 0
        if (!l[0] && !l[2]) {
            corner++
        }
        if (l[0] && l[2] && !l[1]) {
            corner++
        }
        if (!l[2] && !l[4]) {
            corner++
        }
        if (l[2] && l[4] && !l[3]) {
            corner++
        }
        if (!l[4] && !l[6]) {
            corner++
        }
        if (l[4] && l[6] && !l[5]) {
            corner++
        }
        if (!l[6] && !l[0]) {
            corner++
        }
        if (l[6] && l[0] && !l[7]) {
            corner++
        }
        return corner
    }

    fun bfs(map: MutableList<MutableList<String>>, x: Int, y: Int, part2: Boolean = false): Pair<Int, Int> {
        val queue = mutableListOf(Pair(x, y))
        var perimeter = 0
        val region = map[y][x]
        var pointer = 0
        while (pointer < queue.size) {
            val cur = queue[pointer]
            val cardinals = generateCardinals(cur.first, cur.second)
            for (cardinal in cardinals) {
                val point = valid(map, region, cardinal.first, cardinal.second)
                if (point != null) {
                    if (!queue.any { it == point }) {
                        queue.add(point)
                    }
                } else {
                    perimeter++
                }
            }
            pointer++
        }
        if (part2) {
            perimeter = 0
            queue.forEach {
                val corner = corners(map, region, it.first, it.second)
                perimeter += corner
            }
        }
        queue.forEach {
            map[it.second][it.first] = "."
        }
        return Pair(queue.size, perimeter)
    }

    fun part1(input: List<String>): Int {
        val c = convert(input)
        var ans = 0
        for (y in c.indices) {
            for (x in c[y].indices) {
                if (c[y][x] != ".") {
                    val param = bfs(c, x, y)
                    ans += param.first * param.second
                }
            }
        }
        return ans
    }

    fun part2(input: List<String>): Int {
        val c = convert(input)
        var ans = 0
        for (y in c.indices) {
            for (x in c[y].indices) {
                if (c[y][x] != ".") {
                    val param = bfs(c, x, y, true)
                    ans += param.first * param.second
                }
            }
        }
        return ans
    }

    // Test if implementation meets criteria from the description, like:
    val testInput1 = readInput("Day12_test1")
    check(part1(testInput1) == 140)
    check(part2(testInput1) == 80)

    val testInput2 = readInput("Day12_test2")
    check(part1(testInput2) == 772)
    check(part2(testInput2) == 436)

    val testInput3 = readInput("Day12_test3")
    check(part2(testInput3) == 368)

    // Read a large test input from the `src/Day12_test.txt` file:
    val testInput = readInput("Day12_test")
    check(part1(testInput) == 1930)
    check(part2(testInput) == 1206)
    // check(part2(testInput) == 1)

    // Read the input from the `src/Day12.txt` file.
    val input = readInput("Day12")
    println("Running input")
    part1(input).println()
    part2(input).println()
}
