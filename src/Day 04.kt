fun main() {
    fun parseBoards(input: List<String>): List<Board> {
        val boards = input.drop(1).chunked(6).map { board ->
            Board(board.filter { line ->
                line.isNotBlank()
            }.map {
                it.replace("\\s+".toRegex(), " ").trimStart().split(" ").map { i ->
                    Field(i.toInt(), false)
                }
            }, -1)
        }
        return boards
    }

    fun part1(input: List<String>, draws: List<Int>): Int {
        val boards = parseBoards(input)
        var currentDraw = 0
        draws.takeWhile {
            currentDraw = it
            boards.forEach { board -> board.markDraw(it) }
            boards.none { board -> board.isWinner() }
        }
        return currentDraw * (boards.find { it.isWinner() }?.getSumUnmarked() ?: 0)
    }

    fun part2(input: List<String>, draws: List<Int>): Int {
        val boards = parseBoards(input)
        val winners = ArrayList<Board>()

        for (draw in draws) {
            boards.map { board ->
                if (!board.isWinner()) {
                    board.markDraw(draw)
                    if (board.isWinner()) {
                        board.winningDraw = draw
                        winners.add(board)
                    }
                }
            }

        }
        return winners.last().winningDraw * winners.last().getSumUnmarked()
    }

    val input = readInput("04")
    val draws = input.first().split(",").map { it.toInt() }
    println("1: ${part1(input, draws)}")
    println("2: ${part2(input, draws)}")
}

data class Board(val numbers: List<List<Field>>, var winningDraw: Int) {
    fun isWinner(): Boolean {
        return this.hasFullRow() || this.rotate().hasFullRow()
    }

    private fun hasFullRow(): Boolean {
        for (line in numbers) {
            if (line.all { it.marked }) return true
        }
        return false
    }

    private fun rotate(): Board {
        val rotated = ArrayList<ArrayList<Field>>()
        for (i in numbers.indices) {
            val r = ArrayList<Field>()
            for (j in numbers.indices) {
                r.add(numbers[j][i])
            }
            rotated.add(r)
        }
        return Board(rotated, -1)
    }

    fun markDraw(draw: Int) {
        this.numbers.flatten().forEach {
            if (it.nr == draw) {
                it.marked = true
            }
        }
    }

    fun getSumUnmarked(): Int =
        numbers.flatten().filter { field -> !field.marked }.fold(0) { acc, field -> acc + field.nr }
}

data class Field(val nr: Int, var marked: Boolean)
