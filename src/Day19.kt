fun main() {
    fun parse(input: List<String>): Pair<List<String>, List<String>> {
        return Pair(input[0].split(", "), input.subList(2, input.size))
    }

    fun possible(start: List<String>, possibilities: MutableSet<String>, cloth: String): Boolean {
        if (possibilities.contains(cloth)) {
            return true
        }
        for (s in start) {
            if (cloth.startsWith(s)) {
                val isPossible = possible(start, possibilities, cloth.substring(s.length))
                if (isPossible) {
                    possibilities.add(cloth)
                    return true
                }
            }
        }
        return false
    }

    fun part1(input: Pair<List<String>, List<String>>): Int {
        val possibilities = mutableSetOf<String>()
        possibilities.addAll(input.first)
        var ans = 0
        for (cloth in input.second) {
            if (possible(input.first, possibilities, cloth)) {
                ans++
            }
        }
        return ans
    }

    fun allPossibilities(start: List<String>, cloth: String, cache: MutableMap<String, Long>): Long {
        if (cloth.isEmpty()) {
            return 1
        }
        if (cache[cloth] != null) {
            return cache[cloth]!!
        }
        var p = 0L
        for (s in start) {
            if (cloth.startsWith(s)) {
                val combinations = allPossibilities(start, cloth.substring(s.length), cache)
                p += combinations
            }
        }
        if (p > 0) {
            cache[cloth] = p
        }
        return p
    }

    fun part2(input: Pair<List<String>, List<String>>): Long {
        var ans = 0L
        val cache = hashMapOf<String, Long>()
        for (cloth in input.second) {
            val p = allPossibilities(input.first, cloth, cache)
            ans += p
        }
        return ans
    }

    // Read a large test input from the `src/Day19_test1.txt` file:
    val testInput1 = readInput("Day19_test1")
    check(part1(parse(testInput1)) == 6)
    check(part2(parse(testInput1)) == 16L)

    // Read the input from the `src/Day19.txt` file.
    val input = readInput("Day19")
    println("Running input")
    part1(parse(input)).println()
    part2(parse(input)).println()
}
