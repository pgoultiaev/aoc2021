fun main() {
    fun doGrowthRound(input: Map<Int, Long>, roundsLeft: Int): Map<Int, Long> {
        return if (roundsLeft == 0) input
        else {
            val mMap = input.toMutableMap()
            for (k in 0..8) {
                when (k) {
                    6 -> mMap[6] = input.getOrDefault(7, 0) + input.getOrDefault(0, 0)
                    8 -> mMap[8] = input.getOrDefault(0, 0)
                    else -> mMap[k] = input.getOrDefault(k + 1, 0)
                }
            }
            doGrowthRound(mMap, roundsLeft - 1)
        }
    }

    fun solve(input: Map<Int, Long>, rounds: Int): Long {
        return doGrowthRound(input, rounds).values.reduce { acc, i -> acc + i }
    }

    val input = readInput("06").first().split(",")
        .map { s -> s.toInt() }
        .groupBy { it }
        .mapValues { (_, fish) -> fish.size.toLong() }
    println("1: ${solve(input, 80)}")
    println("2: ${solve(input, 256)}")
}
