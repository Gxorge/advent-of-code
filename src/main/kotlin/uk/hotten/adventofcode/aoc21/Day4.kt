package uk.hotten.adventofcode.aoc21

import uk.hotten.adventofcode.AOCDay

class Day4: AOCDay(21, "day04.txt") {

    val draw = mutableListOf<Int>()
    val boards = mutableListOf<Board>()

    init {
        for (num in getInput()[0].split(',')) {
            draw.add(num.toInt())
        }

        var currentBoard = Board(mutableListOf())
        var currentLine = 0
        for (line in getInput()) {
            if (line == getInput()[0])
                continue

            if (line == "" || line == " ") {
                if (currentBoard.rows.isNotEmpty())
                    boards.add(currentBoard)
                currentBoard = Board(mutableListOf())
                currentLine = 0
                continue
            }

            val entryMap = mutableMapOf<Int, BoardNumber>()

            entryMap[0] = BoardNumber(getNum(line[0], line[1]), false)
            entryMap[1] = BoardNumber(getNum(line[3], line[4]), false)
            entryMap[2] = BoardNumber(getNum(line[6], line[7]), false)
            entryMap[3] = BoardNumber(getNum(line[9], line[10]), false)
            entryMap[4] = BoardNumber(getNum(line[12], line[13]), false)
            currentBoard.rows.add(entryMap)
            currentLine++
        }
        boards.add(currentBoard) // commits the last board as there isnt an empty line to trigger the commit

    }

    fun getNum(one: Char, two: Char): Int {
        var combined = ""
        if (one == ' ') combined += "0" else combined += one
        combined += two

        return combined.toInt()
    }

    override fun part1() {
        for (toCall in draw) {
            callNumber(toCall)

            val winners = checkWin()
            if (winners.isNotEmpty()) {
                if (winners.size == 1) println("----WINNER FOUND----") else println("----MULTIPLE WINNERS FOUND----")
                // Get sum of unmarked
                for (winner in winners) {
                    var unmarked = 0
                    for (row in winner.rows)
                        for (num in row)
                            if (!num.value.selected) unmarked += num.value.number
                    println("Total of unmarked is $unmarked, and multiplied by $toCall, the answer is ${unmarked * toCall}")
                }
                break
            }
        }
    }

    fun callNumber(number: Int) {
        println("Calling Number $number!")
        for (board in boards) {
            for (row in board.rows) {
                for (num in row) {
                    if (num.value.number == number) {
                        num.value.selected = true
                    }
                }
            }
        }
    }

    fun checkWin(): List<Board> {
        val toReturn = mutableListOf<Board>()

        for (board in boards) {
            // Check the horizontal
            for (row in board.rows) {
                var allSelected = true
                for (item in row) {
                    if (!item.value.selected) {
                        allSelected = false
                    }
                }
                if (allSelected) {
                    println("Found a completed row horizontally")
                    toReturn.add(board)
                }
            }

            // Check vertical
            for (column in 0 until board.rows[0].size) {
                var allSelected = true
                for (rowNum in 0 until board.rows.size) {
                    if (!board.rows[rowNum][column]!!.selected)
                        allSelected = false
                }
                if (allSelected) {
                    println("Found a completed row vertically")
                    toReturn.add(board)
                }
            }
        }
        return toReturn
    }

    // Find out which will win last!
    override fun part2() {
        for (toCall in draw) {
            callNumber(toCall)

            val winners = checkWin()
            if (winners.isNotEmpty()) {
                if (boards.size != 1) {
                    if (winners.size == 1) println("----NON-FINAL WINNER FOUND----") else println("----MULTIPLE NON-FINAL WINNERS FOUND----")

                    for (winner in winners)
                        boards.remove(winner)

                    println("${winners.size} board(s) removed.")
                    continue
                }

                if (winners.size == 1) println("----WINNER FOUND----") else println("----MULTIPLE WINNERS FOUND----")
                // Get sum of unmarked
                for (winner in winners) {
                    var unmarked = 0
                    for (row in winner.rows)
                        for (num in row)
                            if (!num.value.selected) unmarked += num.value.number
                    println("Total of unmarked is $unmarked, and multiplied by $toCall, the answer is ${unmarked * toCall}")
                }
                break
            }
        }
    }

    data class Board(val rows: MutableList<MutableMap<Int, BoardNumber>>)
    data class BoardNumber(val number: Int, var selected: Boolean)
}