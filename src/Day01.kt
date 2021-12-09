fun main() {
    fun part1(input: List<Int>): Int {
        return input.windowed(2).count { (x, y) -> x < y }
    }

    fun part2(input: List<Int>): Int {
        val inputWindowed3 = input.windowed(3).map { it.sum() }
        return part1(inputWindowed3)
    }

    val input = readInput("01").map { it.toInt() }
    println("1: ${part1(input)}")
    println("2: ${part2(input)}")
}
