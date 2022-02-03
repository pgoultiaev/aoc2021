fun main() {
    fun part1(input: List<String>): Int {
        return input.sumOf {
                it.split(" | ")
                    .drop(1)
                    .first()
                    .split(" ")
                    .filter { s -> s.length in listOf(2, 3, 4, 7) }.size
            }
    }

    fun String.fullyContains(other: String): Boolean {
        return this.toSet().containsAll(other.toSet())
    }

    fun diffStrings(one: String, other: String): String {
        return one.toSet().minus(other.toSet()).joinToString("")
    }

    fun List<String>.getStringByLength(length: Int): String {
        return this.find { it.length == length }.toString()
    }

    fun determineDigit(wires: List<String>): Map<String, Int> {
        val digitMapping = mutableMapOf<String, Int>()
        wires.forEach {
            when {
                it.length == 2 -> digitMapping[it] = 1
                it.length == 3 -> digitMapping[it] = 7
                it.length == 4 -> digitMapping[it] = 4
                it.length == 7 -> digitMapping[it] = 8
                it.length == 5 && it.fullyContains(wires.getStringByLength(2)) -> digitMapping[it] = 3
                it.length == 6 && it.fullyContains(wires.getStringByLength(4)) -> digitMapping[it] = 9
                it.length == 5 && it.fullyContains(diffStrings(
                        wires.getStringByLength(4), wires.getStringByLength(2))) -> digitMapping[it] = 5
                it.length == 5 && !it.fullyContains(wires.getStringByLength(4)) -> digitMapping[it] = 2
                it.length == 6 && !it.fullyContains(wires.getStringByLength(2)) -> digitMapping[it] = 6
                it.length == 6 -> digitMapping[it] = 0
            }
        }
        return digitMapping
    }

    fun part2(input: List<String>): Int {
        return input.sumOf {
            val firstPart = it.split(" | ").first().split(" ")
            val secondPart = it.split(" | ").drop(1).first().split(" ")
            val digitMapping = determineDigit(firstPart)
            secondPart.map {
                digitMapping[digitMapping.keys.find { k -> k.length == it.length && k.fullyContains(it)}]
            }.joinToString("").toInt()
        }
    }

    val input = readInput("08")
    println("1: ${part1(input)}")
    println("2: ${part2(input)}")
}