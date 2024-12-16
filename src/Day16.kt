import java.util.*

data class Node(
    val cost: Int,
    val location: Pair<Int, Int>,
    val direction: Direction,
    val history: List<Pair<Int, Int>>
)

fun main() {
    fun convert(input: List<String>): List<MutableList<Char>> {
        return input.map { it.toMutableList() }
    }

    fun move(direction: Direction, loc: Pair<Int, Int>): Pair<Int, Int> {
        return when (direction) {
            Direction.NORTH -> Pair(loc.first, loc.second - 1)
            Direction.SOUTH -> Pair(loc.first, loc.second + 1)
            Direction.EAST -> Pair(loc.first + 1, loc.second)
            Direction.WEST -> Pair(loc.first - 1, loc.second)
        }
    }

    fun moveForward(
        map: List<MutableList<Char>>,
        queue: PriorityQueue<Node>,
        visited: MutableMap<String, Int>,
        n: Node
    ) {
        val next = move(n.direction, n.location)
        val key = next.first.toString() + "," + next.second.toString() + "," + n.direction.toString()
        if (map[next.second][next.first] != '#') {
            val prevCost = visited[key] ?: Int.MAX_VALUE
            val cost = n.cost + 1
            if (prevCost < cost) {
                return
            }
            visited[key] = cost
            val history = n.history.toMutableList()
            history.add(n.location)
            queue.add(Node(cost, next, n.direction, history))
        }
    }

    fun rotate(start: Direction): List<Pair<Direction, Int>> {
        return when (start) {
            Direction.NORTH -> listOf(
                Pair(Direction.NORTH, 0),
                Pair(Direction.EAST, 1000),
                Pair(Direction.WEST, 1000)
            )

            Direction.SOUTH -> listOf(
                Pair(Direction.SOUTH, 0),
                Pair(Direction.EAST, 1000),
                Pair(Direction.WEST, 1000)
            )

            Direction.WEST -> listOf(
                Pair(Direction.WEST, 0),
                Pair(Direction.NORTH, 1000),
                Pair(Direction.SOUTH, 1000)
            )

            Direction.EAST -> listOf(
                Pair(Direction.EAST, 0),
                Pair(Direction.NORTH, 1000),
                Pair(Direction.SOUTH, 1000)
            )
        }
    }

    fun explore(map: List<MutableList<Char>>, queue: PriorityQueue<Node>, visited: MutableMap<String, Int>): Pair<Int, List<Pair<Int, Int>>> {
        val n = queue.poll()
        if (map[n.location.second][n.location.first] == 'E') {
            return Pair(n.cost, n.history)
        }
        val directions = rotate(n.direction)
        directions.forEach {
            moveForward(map, queue, visited, Node(n.cost + it.second, n.location, it.first, n.history))
        }
        return Pair(-1, emptyList())
    }

    fun part1(input: List<String>): Int {
        val map = convert(input)
        val queue = PriorityQueue<Node>(compareBy { it.cost })
        for (y in map.indices) {
            for (x in map[y].indices) {
                if (map[y][x] == 'S') {
                    queue.add(Node(0, Pair(x, y), Direction.EAST, listOf()))
                    break
                }
            }
        }
        val visited = mutableMapOf<String, Int>()
        while (queue.isNotEmpty()) {
            val result = explore(map, queue, visited)
            if (result.first != -1) {
                return result.first
            }
        }
        return 0
    }

    fun part2(input: List<String>): Int {
        val map = convert(input)
        val queue = PriorityQueue<Node>(compareBy { it.cost })
        for (y in map.indices) {
            for (x in map[y].indices) {
                if (map[y][x] == 'S') {
                    queue.add(Node(0, Pair(x, y), Direction.EAST, listOf()))
                    break
                }
            }
        }
        val visited = mutableMapOf<String, Int>()
        var cost = Int.MAX_VALUE
        val points = mutableSetOf<Pair<Int, Int>>()
        while (queue.isNotEmpty()) {
            val result = explore(map, queue, visited)
            if (result.first != -1 && cost >= result.first) {
                cost = result.first
                points.addAll(result.second)
            }
            if (cost < result.first) {
                break
            }
        }
        return points.size +1
    }

    // Test if implementation meets criteria from the description, like:
    val testInput1 = readInput("Day16_test1")
    check(part1(testInput1) == 7036)
    check(part2(testInput1) == 45)

    // Read a large test input from the `src/Day16_test.txt` file:
    val testInput = readInput("Day16_test")
    check(part1(testInput) == 11048)
    check(part2(testInput) == 64)

    // Read the input from the `src/Day16.txt` file.
    val input = readInput("Day16")
    println("Running input")
    part1(input).println()
    part2(input).println()
}
