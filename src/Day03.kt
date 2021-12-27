fun main() {
    fun findMajorityMinority(input: List<List<Int>>): Pair<List<Int>, List<Int>> {
        val size = input.size
        val freq = (input.reduce { f, it -> f.zip(it, Int::plus)})
        val majority = freq.map { if (it >= size - it) 1 else 0 }
        val minority = freq.map { if (it < size - it) 1 else 0 }
        return Pair(majority, minority)
    }

    fun searchNumberList(numbers: List<List<Int>>, index: Int, major: Boolean): List<Int> {
        return if (numbers.size == 1) {
            numbers.first()
        } else {
            val target = if (major) findMajorityMinority(numbers).first else findMajorityMinority(numbers).second
            val filtered = numbers.filter { it[index] == target[index] }
            searchNumberList(filtered, index + 1, major)
        }
    }

    fun intListToBinary(intList: List<Int>): Int = intList.joinToString("").toInt(2)

    fun part1(input: List<List<Int>>): Int {
        val pair = findMajorityMinority(input)
        val gamma = intListToBinary(pair.first)
        val epsilon = intListToBinary(pair.second)
        return gamma * epsilon
    }

    fun part2(input: List<List<Int>>): Int {
        val oxygenRating = intListToBinary(searchNumberList(input, 0, true))
        val co2Rating = intListToBinary(searchNumberList(input, 0, false))
        return oxygenRating * co2Rating
    }

    val input = readInput("03").map { s -> s.map { it.toString().toInt() } }
    println("1: ${part1(input)}")
    println("2: ${part2(input)}")
}
