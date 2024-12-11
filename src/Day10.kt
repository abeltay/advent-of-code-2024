fun main() {
    fun convert(input: List<String>): List<List<Int>> {
        return input.map { row -> row.map { it.digitToInt() } }
    }

    fun updateValue(
        map: List<List<Int>>,
        values: List<List<MutableSet<Pair<Int, Int>>>>,
        x: Int,
        y: Int
    ) {
        val current = map[y][x]
        for (newY in y - 1..y + 1) {
            if (newY < 0 || newY >= map.size) {
                continue
            }
            for (newX in x - 1..x + 1) {
                if (newX < 0 || newX >= map[newY].size || (newX == x && newY == y)) {
                    continue
                }
                if (newX != x && newY != y) {
                    continue
                }
                if (map[newY][newX] == current + 1)
                    values[y][x].addAll(values[newY][newX])
            }
        }
    }

    fun part1(input: List<String>): Int {
        val c = convert(input)
        val values = mutableListOf<MutableList<MutableSet<Pair<Int, Int>>>>()
        c.forEach {
            values.add(MutableList(it.size) { mutableSetOf() })
        }
        for (y in c.indices) {
            for (x in c[y].indices) {
                if (c[y][x] == 9) {
                    values[y][x].add(Pair(x, y))
                }
            }
        }
        for (level in 8 downTo 0) {
            for (y in c.indices) {
                for (x in c[y].indices) {
                    if (c[y][x] == level) {
                        updateValue(c, values, x, y)
                    }
                }
            }
        }
        var ans = 0
        for (i in c.indices) {
            for (j in c[i].indices) {
                if (c[i][j] == 0) {
                    ans += values[i][j].size
                }
            }
        }
        return ans
    }

    fun updateValue2(
        map: List<List<Int>>,
        values: List<MutableList<Int>>,
        x: Int,
        y: Int
    ) {
        val current = map[y][x]
        var possibilities = 0
        for (newY in y - 1..y + 1) {
            if (newY < 0 || newY >= map.size) {
                continue
            }
            for (newX in x - 1..x + 1) {
                if (newX < 0 || newX >= map[newY].size || (newX == x && newY == y)) {
                    continue
                }
                if (newX != x && newY != y) {
                    continue
                }
                if (map[newY][newX] == current + 1)
                    possibilities+=values[newY][newX]
            }
        }
        values[y][x] = possibilities
    }

    fun part2(input: List<String>): Int {
        val c = convert(input)
        val values = mutableListOf<MutableList<Int>>()
        c.forEach {
            values.add(MutableList(it.size) { 0 })
        }
        for (y in c.indices) {
            for (x in c[y].indices) {
                if (c[y][x] == 9) {
                    values[y][x] = 1
                }
            }
        }
        for (level in 8 downTo 0) {
            for (y in c.indices) {
                for (x in c[y].indices) {
                    if (c[y][x] == level) {
                        updateValue2(c, values, x, y)
                    }
                }
            }
        }
        var ans = 0
        for (i in c.indices) {
            for (j in c[i].indices) {
                if (c[i][j] == 0) {
                    ans += values[i][j]
                }
            }
        }
        return ans
    }

    // Test if implementation meets criteria from the description, like:
    // check(part1(listOf("test_input")) == 11)

    // Read a large test input from the `src/Day10_test.txt` file:
    val testInput = readInput("Day10_test")
    check(part1(testInput) == 36)
    check(part2(testInput) == 81)

    // Read the input from the `src/Day10.txt` file.
    val input = readInput("Day10")
    println("Running input")
    part1(input).println()
    part2(input).println()
}
