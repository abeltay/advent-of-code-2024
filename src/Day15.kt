enum class Direction {
    NORTH, SOUTH, WEST, EAST
}

data class Box(
    var x: Int,
    var y: Int,
)

fun main() {
    fun convert(input: List<String>): Pair<List<MutableList<Char>>, List<String>> {
        var row = 0
        val map = mutableListOf<MutableList<Char>>()
        while (input[row].isNotBlank()) {
            map.add(input[row].toMutableList())
            row++
        }
        return Pair(map, input.subList(row + 1, input.size))
    }

    fun next(direction: Direction, x: Int, y: Int): Pair<Int, Int> {
        return when (direction) {
            Direction.NORTH -> Pair(x, y - 1)
            Direction.SOUTH -> Pair(x, y + 1)
            Direction.EAST -> Pair(x + 1, y)
            Direction.WEST -> Pair(x - 1, y)
        }
    }

    fun updateMap(map: List<MutableList<Char>>, from: Pair<Int, Int>, to: Pair<Int, Int>) {
        map[to.second][to.first] = map[from.second][from.first]
        map[from.second][from.first] = '.'
    }

    fun move(
        map: List<MutableList<Char>>,
        direction: Direction,
        x: Int,
        y: Int,
    ): Pair<Int, Int>? {
        val future = next(direction, x, y)
        if (map[future.second][future.first] == '#') {
            return null
        }
        if (map[future.second][future.first] == '.') {
            return future
        }
        val moved = move(map, direction, future.first, future.second)
        if (moved != null) {
            updateMap(map, future, moved)
            return future
        } else {
            return null
        }
    }

    fun convertToDirection(c: Char): Direction {
        return when (c) {
            '^' -> Direction.NORTH
            'v' -> Direction.SOUTH
            '>' -> Direction.EAST
            else -> Direction.WEST
        }
    }

    fun part1(input: List<String>): Int {
        val c = convert(input)
        val map = c.first
        val instructions = c.second
        var robotX = 0
        var robotY = 0
        for (y in map.indices) {
            for (x in map[0].indices) {
                if (map[y][x] == '@') {
                    robotX = x
                    robotY = y
                    break
                }
            }
        }
        instructions.forEach { instruction ->
            instruction.forEach {
                val dir = convertToDirection(it)
                val moved = move(map, dir, robotX, robotY)
                if (moved != null) {
                    updateMap(map, Pair(robotX, robotY), moved)
                    robotX = moved.first
                    robotY = moved.second
                }
            }
        }
        var ans = 0
        for (y in map.indices) {
            for (x in map[0].indices) {
                if (map[y][x] == 'O') {
                    ans += 100 * y + x
                }
            }
        }
        return ans
    }

    fun canMove(
        map: List<List<Char>>,
        direction: Direction,
        boxes: List<Box>,
        index: Int,
        affectedBoxes: MutableList<Int>
    ): Boolean {
        val future = next(direction, boxes[index].x, boxes[index].y)
        if (map[future.second][future.first] == '#' || map[future.second][future.first + 1] == '#') {
            return false
        }
        for (i in boxes.indices) {
            if (affectedBoxes.contains(i)) {
                continue
            }
            if (direction == Direction.NORTH || direction == Direction.SOUTH) {
                if (boxes[i].x >= future.first - 1 && boxes[i].x <= future.first + 1 && boxes[i].y == future.second) {
                    affectedBoxes.add(i)
                }
            } else if (direction == Direction.WEST) {
                if (boxes[i].x == future.first - 1 && boxes[i].y == future.second) {
                    affectedBoxes.add(i)
                }
            } else if (direction == Direction.EAST) {
                if (boxes[i].x == future.first + 1 && boxes[i].y == future.second) {
                    affectedBoxes.add(i)
                }
            }
        }
        return true
    }

    fun part2(input: List<String>): Int {
        val c = convert(input)
        val map = mutableListOf<MutableList<Char>>()
        var robot: Pair<Int, Int> = Pair(0, 0)
        val boxes = mutableListOf<Box>()
        for (y in c.first.indices) {
            val newRow = mutableListOf<Char>()
            for (x in c.first[0].indices) {
                if (c.first[y][x] == '#') {
                    newRow.addAll(listOf('#', '#'))
                    continue
                }
                when (c.first[y][x]) {
                    'O' -> boxes.add(Box(x * 2, y))
                    '@' -> robot = Pair(x * 2, y)
                    else -> {}
                }
                newRow.add('.')
                newRow.add('.')
            }
            map.add(newRow)
        }
        val instructions = c.second
        for (instruction in instructions) {
            instruction.forEach {
                val affectedBoxes = mutableListOf<Int>()
                val dir = convertToDirection(it)
                val n = next(dir, robot.first, robot.second)
                if (map[n.second][n.first] == '#') {
                    return@forEach
                }
                val idx1 = boxes.indexOfFirst { box -> box.x == n.first && box.y == n.second }
                if (idx1 != -1) {
                    affectedBoxes.add(idx1)
                }
                val idx2 = boxes.indexOfFirst { box -> box.x == n.first - 1 && box.y == n.second }
                if (idx2 != -1) {
                    affectedBoxes.add(idx2)
                }
                var pointer = 0
                while (pointer < affectedBoxes.size) {
                    if (!canMove(map, dir, boxes, affectedBoxes[pointer], affectedBoxes)) {
                        return@forEach
                    }
                    pointer++
                }
                affectedBoxes.forEach { index ->
                    val new = next(dir, boxes[index].x, boxes[index].y)
                    boxes[index].x = new.first
                    boxes[index].y = new.second
                }
                robot = n
            }
        }
        var ans = 0
        for (box in boxes) {
            ans += 100 * box.y + box.x
        }
        return ans
    }

// Test if implementation meets criteria from the description, like:
    val testInput1 = readInput("Day15_test1")
    check(part1(testInput1) == 2028)
    val testInput2 = readInput("Day15_test2")
    check(part2(testInput2) == 618)

// Read a large test input from the `src/Day15_test.txt` file:
    val testInput = readInput("Day15_test")
    check(part1(testInput) == 10092)
    check(part2(testInput) == 9021)

// Read the input from the `src/Day15.txt` file.
    val input = readInput("Day15")
    println("Running input")
    part1(input).println()
    part2(input).println()
}
