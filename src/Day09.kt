data class File9(
    var id: Int,
    var size: Int
)

fun main() {
    fun convert(input: String): Pair<MutableList<Int>, MutableList<Int>> {
        var isSpace = false
        val items = mutableListOf<Int>()
        val spaces = mutableListOf<Int>()
        input.forEach {
            if (isSpace) {
                isSpace = false
                spaces.add(it.digitToInt())
            } else {
                isSpace = true
                items.add(it.digitToInt())
            }
        }
        return Pair(items, spaces)
    }

    fun checksum(items: MutableList<Int>, spaces: MutableList<Int>): Long {
        var sum: Long = 0
        var pointer = 0
        var ptr = 0
        while (ptr < items.size) {
            for (i in 0 until items[ptr]) {
                sum += pointer * ptr
                items[ptr]--
                pointer++
            }
            var space = spaces[ptr]
            while (space > 0 && items.size > 0) {
                val last = items.size - 1
                if (items[last] == 0) {
                    items.removeLast()
                    continue
                }
                sum += pointer * last
                pointer++
                items[last]--
                space--
            }
            ptr++
        }
        return sum
    }

    fun calculateChecksum(files: List<File9>): Long {
        var sum: Long = 0
        var pointer = 0
        for (i in files.indices) {
            var size = files[i].size
            while (size > 0) {
                if (files[i].id != -1) {
                    sum += pointer * files[i].id
                }
                size--
                pointer++
            }
        }
        return sum
    }

    fun part1(input: List<String>): Long {
        val c = convert(input[0])
        return checksum(c.first, c.second)
    }

    fun convert2(input: String): MutableList<File9> {
        var isSpace = false
        val items = mutableListOf<File9>()
        var id = 0
        input.forEach {
            if (isSpace) {
                items.add(File9(-1, it.digitToInt()))
            } else {
                items.add(File9(id, it.digitToInt()))
                id++
            }
            isSpace = !isSpace
        }
        return items
    }

    fun compress(files: MutableList<File9>): Long {
        var id = files.last().id
        while (id > 0) {
            for (i in files.indices.reversed()) {
                if (files[i].id != id) {
                    continue
                }
                for (j in 0 until i) {
                    if (files[j].id == -1 && files[j].size >= files[i].size) {
                        val leftover = files[j].size - files[i].size
                        files[j].id = files[i].id
                        files[j].size = files[i].size
                        files[i].id = -1
                        if (leftover > 0) {
                            files.add(j + 1, File9(-1, leftover))
                        }
                        var k = 1
                        while (k < files.size) {
                            if (files[k - 1].id == -1 && files[k].id == -1) {
                                files[k - 1].size += files[k].size
                                files.removeAt(k)
                            } else {
                                k++
                            }
                        }
                        break
                    }
                }
            }
            id--
        }
        return calculateChecksum(files)
    }

    fun part2(input: List<String>): Long {
        val c = convert2(input[0])
        return compress(c)
    }

// Read a large test input from the `src/Day09_test.txt` file:
    val testInput = readInput("Day09_test")
    check(part1(testInput) == 1928.toLong())
    check(part2(testInput) == 2858.toLong())

// Read the input from the `src/Day09.txt` file.
    val input = readInput("Day09")
    println("Running input")
    part1(input).println()
    part2(input).println()
}
