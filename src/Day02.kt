fun main() {
    fun part1(input: List<List<String>>): Int {
        var x = 0
        var y = 0
        for (i in input) {
            when (i[0]) {
                "forward" -> x += i[1].toInt()
                "down" -> y -= i[1].toInt()
                "up" -> y += i[1].toInt()
            }
        }
        return kotlin.math.abs(x * y)
    }

    fun part2(input: List<List<String>>): Int {
        var x = 0
        var y = 0
        var aim = 0
        for (i in input) {
            when (i[0]) {
                "forward" -> {
                    x += i[1].toInt()
                    y += aim * i[1].toInt()
                }
                "down" -> aim += i[1].toInt()
                "up" -> aim -= i[1].toInt()
            }
        }
        return kotlin.math.abs(x * y)
    }

    val input = readInput("02").map { it.split(" ") }
    println(part1(input))
    println(part2(input))
}