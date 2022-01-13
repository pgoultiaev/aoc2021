import kotlin.math.abs

fun main() {
    fun calculateCostToAlignOnPos(positions: List<Int>, pos: Int): Int {
        return positions.sumOf { abs(it - pos) }
    }

    fun calculateCostToAlignOnPos2(positions: List<Int>, pos: Int): Int {
        return positions.sumOf { (1..abs(it - pos)).sum() }
    }

    fun part1(input: List<Int>): Int? {
        return (0..input.maxByOrNull { it }!!)
            .map { calculateCostToAlignOnPos(input, it) }
            .minByOrNull { it }
    }

    fun part2(input: List<Int>): Int? {
        return (0..input.maxByOrNull { it }!!)
            .map { calculateCostToAlignOnPos2(input, it) }
            .minByOrNull { it }
    }

    val input = readInput("07").first().split(",").map { it.toInt() }
    println("1: ${part1(input)}")
    println("2: ${part2(input)}")
}