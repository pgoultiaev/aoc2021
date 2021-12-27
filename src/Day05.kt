fun main() {
    fun String.toPoint(): Point {
        val ss = this.split(",")
        return Point(ss.first().toInt(), ss.last().toInt())
    }

    @Throws(Exception::class)
    fun defineDirection(p1: Point, p2: Point): Direction {
        return when {
            p1.x == p2.x && p1.y > p2.y -> Direction.S
            p1.x == p2.x && p1.y < p2.y -> Direction.N
            p1.x < p2.x && p1.y == p2.y -> Direction.E
            p1.x > p2.x && p1.y == p2.y -> Direction.W
            p1.x < p2.x && p1.y > p2.y -> Direction.SE
            p1.x < p2.x && p1.y < p2.y -> Direction.NE
            p1.x > p2.x && p1.y > p2.y -> Direction.SW
            p1.x > p2.x && p1.y < p2.y -> Direction.NW
            else -> throw Exception("something is wrong here")
        }
    }

    fun generateLineFromTwoPoints(p1: Point, p2: Point): Line {
        val direction = defineDirection(p1, p2)
        val pointList = ArrayList<Point>()
        when (direction) {
            Direction.S -> (p2.y..p1.y).forEach { pointList.add(Point(p1.x, it)) }
            Direction.N -> (p1.y..p2.y).forEach { pointList.add(Point(p1.x, it)) }
            Direction.E -> (p1.x..p2.x).forEach { pointList.add(Point(it, p1.y)) }
            Direction.W -> (p2.x..p1.x).forEach { pointList.add(Point(it, p1.y)) }
            Direction.SE -> (p1.x..p2.x).forEachIndexed{ i, _ -> pointList.add(Point(p1.x + i, p1.y - i)) }
            Direction.NE -> (p1.x..p2.x).forEachIndexed{ i, _ -> pointList.add(Point(p1.x + i, p1.y + i)) }
            Direction.SW -> (p2.x..p1.x).forEachIndexed{ i, _ -> pointList.add(Point(p1.x - i, p1.y - i)) }
            Direction.NW -> (p2.x..p1.x).forEachIndexed{ i, _ -> pointList.add(Point(p1.x - i, p1.y + i)) }
        }
        return Line(pointList)
    }

    fun String.toLine(): Line {
        val ss = this.split(" ")
        val p1 = ss.first().toPoint()
        val p2 = ss.last().toPoint()
        return generateLineFromTwoPoints(p1, p2)
    }

    fun part1(input: List<String>): Int {
        val filteredLines = input.map { it.toLine() }.filter { line -> !line.isDiagonal() }
        return filteredLines.flatMap { line -> line.points.map { it } }.groupingBy { it }.eachCount().count { e -> e.value > 1 }
    }

    fun part2(input: List<String>): Int {
        return input.map { it.toLine() }.flatMap { line -> line.points.map { it } }.groupingBy { it }.eachCount().count { e -> e.value > 1 }
    }

    val input = readInput("05")
    println("1: ${part1(input)}")
    println("2: ${part2(input)}")
}

data class Line(val points: List<Point>) {
    fun isDiagonal(): Boolean {
        return when {
            this.points.first().x != this.points.last().x && this.points.first().y != this.points.last().y -> true
            else -> false
        }
    }
}
data class Point(val x: Int, val y: Int)

enum class Direction {
    N, S, W, E, SW, SE, NW, NE
}
