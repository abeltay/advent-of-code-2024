fun main() {
    fun convert(input: List<String>): Map<Char, List<Pair<Int, Int>>> {
        val map = mutableMapOf<Char, MutableList<Pair<Int, Int>>>()
        for (y in input.indices) {
            for (x in input[y].indices) {
                if (input[y][x] == '.') {
                    continue
                }
                val retrieved = map[input[y][x]] ?: mutableListOf()
                retrieved.add(Pair(x, y))
                map[input[y][x]] = retrieved
            }
        }
        return map
    }

    fun move(
        start: Pair<Int, Int>?,
        distX: Int,
        distY: Int,
        xBound: Int,
        yBound: Int,
        antiNodes: MutableList<MutableList<Boolean>>,
    ): Pair<Int, Int>? {
        if (start == null){
            return null
        }
        val p1 = Pair(start.first + distX, start.second + distY)
        return if (p1.first in 0 until xBound && p1.second in 0 until yBound) {
            antiNodes[p1.second][p1.first] = true
            p1
        } else {
            null
        }
    }

    fun antiNodes(
        a: Pair<Int, Int>,
        b: Pair<Int, Int>,
        antiNodes: MutableList<MutableList<Boolean>>,
        xBound: Int,
        yBound: Int,
        unbounded: Boolean
    ) {
        val diffX = a.first - b.first
        val diffY = a.second - b.second
        if (unbounded) {
            antiNodes[a.second][a.first] = true
            antiNodes[b.second][b.first] = true
        }
        var next: Pair<Int, Int>? = a
        do {
            next = move(next, diffX, diffY, xBound, yBound, antiNodes)
        } while (unbounded && next != null)
        next = b
        do {
            next = move(next, diffX * -1, diffY * -1, xBound, yBound, antiNodes)
        } while (unbounded && next != null)
    }

    fun findAntiNodes(
        nodes: List<Pair<Int, Int>>,
        antiNodes: MutableList<MutableList<Boolean>>,
        xBound: Int,
        yBound: Int,
        unbounded: Boolean = false
    ) {
        for (i in nodes.indices) {
            for (j in i + 1 until nodes.size) {
                antiNodes(nodes[i], nodes[j], antiNodes, xBound, yBound, unbounded)
            }
        }
    }

    fun part1(input: List<String>): Int {
        val converted = convert(input)
        val antiNodes = mutableListOf<MutableList<Boolean>>()
        input.forEach {
            antiNodes.add(MutableList(it.length) { false })
        }
        converted.forEach {
            findAntiNodes(it.value, antiNodes, input[0].length, input.size)
        }
        var ans = 0
        antiNodes.forEach { row ->
            row.forEach {
                if (it) {
                    ans++
                }
            }
        }
        return ans
    }

    fun part2(input: List<String>): Int {
        val converted = convert(input)
        val antiNodes = mutableListOf<MutableList<Boolean>>()
        input.forEach {
            antiNodes.add(MutableList(it.length) { false })
        }
        converted.forEach {
            findAntiNodes(it.value, antiNodes, input[0].length, input.size, true)
        }
        var ans = 0
        antiNodes.forEach { row ->
            row.forEach {
                if (it) {
                    ans++
                }
            }
        }
        return ans
    }

    // Read a large test input from the `src/Day08_test.txt` file:
    val testInput = readInput("Day08_test")
    check(part1(testInput) == 14)
    check(part2(testInput) == 34)

    // Read the input from the `src/Day08.txt` file.
    val input = readInput("Day08")
    println("Running input")
    part1(input).println()
    part2(input).println()
}
