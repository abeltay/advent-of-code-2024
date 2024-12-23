fun main() {
    fun parse(input: List<String>): List<Pair<String, String>> {
        val connections: MutableList<Pair<String, String>> = mutableListOf()
        input.forEach { line ->
            val s = line.split("-")
            connections.add(Pair(s[0], s[1]))
            connections.add(Pair(s[1], s[0]))
        }
        return connections.toList()
    }

    fun key(input: List<String>): String {
        val sorted = input.toMutableList()
        sorted.sort()
        return sorted.joinToString(",")
    }

    fun explore(
        input: List<Pair<String, String>>,
        current: String,
        end: String,
        steps: Int,
        prev: List<String>,
        result: MutableSet<String>
    ) {
        if (prev.size in 1..steps && current == prev.firstOrNull()) {
            return
        }
        if (prev.size == steps + 1) {
            if (current == end) {
                result.add(key(prev))
            }
            return
        }
        val f = input.filter { it.first == current }
        val new = prev.toMutableList()
        new.add(current)
        f.forEach {
            explore(input, it.second, end, steps, new.toList(), result)
        }
    }

    fun part1(input: List<Pair<String, String>>): Int {
        val allComputers = hashSetOf<String>()
        for (i in input) {
            allComputers.add(i.first)
        }
        val tComputers = allComputers.filter { it.startsWith("t") }
        val routes = hashSetOf<String>()
        for (t in tComputers) {
            explore(input, t, t, 2, listOf(), routes)
        }
        return routes.size
    }

    fun cliques(
        connections: Map<String, Set<String>>,
        limit: Int = Int.MAX_VALUE,
    ): Set<List<String>> = buildSet {
        fun recurse(node: String, clique: Set<String>) {
            clique.sorted()
                .takeUnless { it in this }
                ?.also(::add)
                ?.takeIf { it.size < limit }
                ?: return

            for (neighbor in connections.getValue(node)) {
                if (neighbor in clique) continue
                if (!connections.getValue(neighbor).containsAll(clique)) continue
                recurse(neighbor, clique + neighbor)
            }
        }

        for (x in connections.keys) recurse(x, setOf(x))
    }

    fun part2(input: List<Pair<String, String>>): String {
        val vertices = hashMapOf<String, MutableSet<String>>()
        input.forEach {
            vertices.getOrPut(it.first) { mutableSetOf() }.add(it.second)
        }
        return cliques(vertices).maxBy { it.size }.joinToString (",")
    }

    // Read a large test input from the `src/Day23_test1.txt` file:
    val testInput1 = parse(readInput("Day23_test1"))
    check(part1(testInput1) == 7)
    check(part2(testInput1) == "co,de,ka,ta")

    // Read the input from the `src/Day23.txt` file.
    val input = parse(readInput("Day23"))
    println("Running input")
    part1(input).println()
    part2(input).println()
}
