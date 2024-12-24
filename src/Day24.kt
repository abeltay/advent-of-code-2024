import kotlin.random.Random

fun main() {
    data class Operation(
        val op1: String,
        val op2: String,
        val operator: String,
        val result: String
    )

    fun parse(input: List<String>): Pair<List<Pair<String, Boolean>>, List<Operation>> {
        val initial = mutableListOf<Pair<String, Boolean>>()
        var i = 0
        while (input[i].isNotEmpty()) {
            input[i].split(": ").let {
                initial.add(Pair(it[0], it[1] == "1"))
            }
            i++
        }
        i++
        val operations = mutableListOf<Operation>()
        while (i < input.size) {
            val s = input[i].split(" -> ")
            val s1 = s[0].split(" ")
            operations.add(Operation(s1[0], s1[2], s1[1], s[1]))
            i++
        }
        return Pair(initial, operations)
    }

    fun result(initial: List<Pair<String, Boolean>>, operations: List<Operation>, key: String): Boolean {
        val find = initial.firstOrNull { it.first == key }
        if (find != null) {
            return find.second
        }
        val op = operations.firstOrNull { it.result == key }
        if (op == null) {
            throw Error("Operation $key not found")
        }
        val op1 = result(initial, operations, op.op1)
        val op2 = result(initial, operations, op.op2)
        return when (op.operator) {
            "AND" -> op1 and op2
            "OR" -> op1 or op2
            else -> op1 xor op2
        }
    }

    fun part1(input: Pair<List<Pair<String, Boolean>>, List<Operation>>): Long {
        var zCount = 0
        val output = mutableListOf<Boolean>()
        while (true) {
            val next = "z" + zCount.toString().padStart(2, '0')
            val nextOp = input.second.firstOrNull { it.result == next }
            if (nextOp == null) {
                break
            }
            output.add(result(input.first, input.second, next))
            zCount++
        }
        var num = ""
        for (one in output.reversed()) {
            num += if (one) {
                "1"
            } else {
                "0"
            }
        }
        return num.toLong(2)
    }

    fun findNum(input: List<Pair<String, Boolean>>, start: String): Long {
        var count = 0
        val output = mutableListOf<Boolean>()
        while (true) {
            val next = start + count.toString().padStart(2, '0')
            val nextOp = input.firstOrNull { it.first == next }
            if (nextOp == null) {
                break
            }
            output.add(nextOp.second)
            count++
        }
        var num = ""
        for (one in output.reversed()) {
            num += if (one) {
                "1"
            } else {
                "0"
            }
        }
        return num.toLong(2)
    }

    fun print(operations: List<Operation>, base: String, indent: Int, steps: Int) {
        if (steps == 0) {
            return
        }
        val op = operations.firstOrNull { it.result == base } ?: return
        for (i in 0 until indent) {
            print("  ")
        }
        println(op.op1 + " " + op.operator + " " + op.op2 + " " + op.result)
        if (!op.op1.startsWith("x") && !op.op1.startsWith("y")) {
            print(operations, op.op1, indent + 1, steps - 1)
        }
        if (!op.op2.startsWith("x") && !op.op2.startsWith("y")) {
            print(operations, op.op2, indent + 1, steps - 1)
        }
    }

    fun convertToBinary(prefix: String, num: Long): List<Pair<String, Boolean>> {
        val binary = num.toString(2).padStart(45,'0')
        val list = mutableListOf<Pair<String, Boolean>>()
        for (i in binary.indices.reversed()) {
            val count = binary.length - i - 1
            val next = prefix + count.toString().padStart(2, '0')
            val bool = binary[i] == '1'
            list.add(Pair(next, bool))
        }
        return list
    }

    fun compare(x: Long, y: Long, newOperations: List<Operation>): Boolean {
        val rightAnswer = x + y
        val list = mutableListOf<Pair<String, Boolean>>()
        list.addAll(convertToBinary("x", x))
        list.addAll(convertToBinary("y", y))
        val operationAnswer = part1(Pair(list, newOperations))
        if (rightAnswer != operationAnswer) {
            val rightBinary = rightAnswer.toString(2)
            val wrongBinary = operationAnswer.toString(2)
            for (i in rightBinary.indices.reversed()) {
                if (rightBinary[i] != wrongBinary[i]) {
                    val zCount = rightBinary.length - i - 1
                    val next = "z" + zCount.toString().padStart(2, '0')
                    print(newOperations, next, 0, 7)
                    return false
                }
            }
        }
        return true
    }

    fun part2(input: Pair<List<Pair<String, Boolean>>, List<Operation>>): String {
        val swaps = listOf(
            Pair("bjm", "z07"),
            Pair("hsw", "z13"),
            Pair("skf", "z18"),
            Pair("wkr", "nvr"),
        )
        val swapMap = mutableMapOf<String, String>()
        swaps.forEach {
            swapMap[it.first] = it.second
            swapMap[it.second] = it.first
        }
        val newOperations = input.second.map {
            val toSwap = swapMap[it.result]
            if (toSwap == null) {
                it
            } else {
                Operation(it.op1, it.op2, it.operator, toSwap)
            }
        }
        var x = findNum(input.first, "x")
        var y = findNum(input.first, "y")
        for (i in 0 until 10) {
            val success = compare(x, y, newOperations)
            if (!success) {
                break
            }
            val max = List(43) { '1' }.joinToString("").toLong(2)
            x = Random.nextLong(1, max)
            y = Random.nextLong(1, max)
        }
        return swapMap.keys.toList().sorted().joinToString(",")
    }

    // Read a large test input from the `src/Day24_test1.txt` file:
    val testInput1 = parse(readInput("Day24_test1"))
    check(part1(testInput1) == 4L)

    // Read a large test input from the `src/Day24_test2.txt` file:
    val testInput2 = parse(readInput("Day24_test2"))
    check(part1(testInput2) == 2024L)

    // Read the input from the `src/Day24.txt` file.
    val input = parse(readInput("Day24"))
    println("Running input")
    part1(input).println()
    part2(input).println()
}
