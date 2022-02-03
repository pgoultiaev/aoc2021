fun main() {
    fun getNeighbours(x: Int, y: Int): List<Pair<Int, Int>> {
        return listOf(Pair(x, y - 1), Pair(x - 1, y), Pair(x + 1, y), Pair(x, y + 1))
    }

    fun part1(input: Map<Pair<Int, Int>, Int>): Int {
        return input.map {
            Pair(it.value, getNeighbours(
                it.key.first, it.key.second
            ).mapNotNull { loc -> input[Pair(loc.first, loc.second)] })
        }.filter { it.second.all { d -> d > it.first } }.sumOf { it.first + 1 }
    }

    fun getBasin(
        p: Pair<Int, Int>,
        dMap: Map<Pair<Int, Int>, Int>,
        visited: MutableList<Pair<Int, Int>>,
        currBasin: MutableList<Pair<Int, Int>>
    ) {
        if (dMap[p] != null && dMap[p] != 9 && p !in visited) {
            visited.add(p)
            currBasin.add(p)
            getNeighbours(p.first, p.second).forEach { getBasin(it, dMap, visited, currBasin) }
        }
    }

    fun part2(input: Map<Pair<Int, Int>, Int>): Int {
        val visited = mutableListOf<Pair<Int, Int>>()
        val currBasin = mutableListOf<Pair<Int, Int>>()
        val basins = mutableListOf<Int>()
        input.forEach {
            getBasin(it.key, input, visited, currBasin)
            if (currBasin.size > 0) {
                basins.add(currBasin.size)
            }
            currBasin.clear()
        }
        return basins.sortedDescending().take(3).reduce { acc, s -> acc * s }
    }

    val depthMap = mutableMapOf<Pair<Int, Int>, Int>()
    readInput("09").mapIndexed { r, row ->
        row.chunked(1).mapIndexed { c, col -> depthMap[Pair(c, r)] = col.toInt() }
    }
    println("1: ${part1(depthMap)}")
    println("2: ${part2(depthMap)}")
}