package uk.hotten.adventofcode.aoc22

import uk.hotten.adventofcode.AOCDay

// Using help, took a different approach to whole puzzle so just re-doing it for P2 without loosing my part 1
class Day22P2: AOCDay(22, "day22.txt") {

    var grid = mutableListOf<String>()
    var sequence = ""

    init {
        var done = false
        for (line in getInput()) {
            if (line == "") {
                done = true
                continue
            }

            if (done) {
                sequence = line
            } else {
                grid.add(line)
            }
        }

        val width = grid.maxOf { it.length }
        val paddedGrid = grid.map { line ->
            line + " ".repeat(width - line.length)
        }

        grid = paddedGrid.toMutableList()

    }

    override fun part2() {
        var row = 0
        var column = 0
        var directionRow = 0
        var directionColumn = 1

        while (grid[row][column] != '.')
            column += 1

        val pattern = """(\d+|[RL]?)""".toRegex()
        for (item in pattern.findAll(sequence)) {
            val action = item.value
            if (isInteger(action)) {
                for (i in 0 until action.toInt()) {
                    var nextRow = row + directionRow
                    var nextColumn = column + directionColumn

                    val tempNR = nextRow
                    val tempNC = nextColumn
                    val tempDR = directionRow
                    val tempDC = directionColumn
                    if (nextRow < 0 && 50 <= nextColumn && nextColumn < 100 && directionRow == -1) {
                        directionRow = 0
                        directionColumn = 1

                        nextRow = tempNC + 100
                        nextColumn = 0
                    } else if (nextColumn < 0 && 150 <= nextRow && nextRow < 200 && directionColumn == -1) {
                        directionRow = 1
                        directionColumn = 0

                        nextRow = 0
                        nextColumn = tempNR - 100
                    } else if (nextRow < 0 && 100 <= nextColumn && nextColumn < 150 && directionRow == -1) {
                        nextRow = 199
                        nextColumn = tempNC - 100
                    } else if (nextRow >= 200 && 0 <= nextColumn && nextColumn < 50 && directionRow == 1) {
                        nextRow = 0
                        nextColumn = tempNC + 100
                    } else if (nextColumn >= 150 && 0 <= nextRow && nextRow < 50 && directionColumn == 1) {
                        directionColumn = -1

                        nextRow = 149 - tempNR
                        nextColumn = 99
                    } else if (nextColumn == 100 && 100 <= nextRow && nextRow < 150 && directionColumn == 1) {
                        directionColumn = -1

                        nextRow = 149 - tempNR
                        nextColumn = 149
                    } else if (nextRow == 50 && 100 <= nextColumn && nextColumn < 150 && directionRow == 1) {
                        directionRow = 0
                        directionColumn = -1

                        nextRow = tempNC - 50
                        nextColumn = 99
                    } else if (nextColumn == 100 && 50 <= nextRow && nextRow < 100 && directionColumn == 1) {
                        directionRow = -1
                        directionColumn = 0

                        nextRow = 49
                        nextColumn = tempNR + 50
                    } else if (nextRow == 150 && 50 <= nextColumn && nextColumn < 100 && directionRow == 1) {
                        directionRow = 0
                        directionColumn = -1

                        nextRow = tempNC + 100
                        nextColumn = 49
                    } else if (nextColumn == 50 && 150 <= nextRow && nextRow < 200 && directionColumn == 1) {
                        directionRow = -1
                        directionColumn = 0

                        nextRow = 149
                        nextColumn = tempNR - 100
                    } else if (nextRow == 99 && 0 <= nextColumn && nextColumn < 50 && directionRow == -1) {
                        directionRow = 0
                        directionColumn = 1

                        nextRow = tempNC + 50
                        nextColumn = 50
                    } else if (nextColumn == 49 && 50 <= nextRow && nextRow < 100 && directionColumn == -1) {
                        directionRow = 1
                        directionColumn = 0

                        nextRow = 100
                        nextColumn = tempNR - 50
                    } else if (nextColumn == 49 && 0 <= nextRow && nextRow < 50 && directionColumn == -1) {
                        directionColumn = 1

                        nextRow = 149 - tempNR
                        nextColumn = 0
                    } else if (nextColumn < 0 && 100 <= nextRow && nextRow < 150 && directionColumn == -1) {
                        directionColumn = 1

                        nextRow = 149 - tempNR
                        nextColumn = 50
                    }

                    println("$nextRow, $nextColumn")

                    if (grid[nextRow][nextColumn] == '#') {
                        directionRow = tempDR
                        directionColumn = tempDC
                        break
                    }

                    row = nextRow
                    column = nextColumn
                }
                continue
            }

            if (action.isEmpty())
                continue

            if (action[0] == 'R') {
                val tempDR = directionRow
                val tempDC = directionColumn

                directionRow = tempDC
                directionColumn = -tempDR

            } else if (action[0] == 'L') {
                val tempDR = directionRow
                val tempDC = directionColumn

                directionRow = -tempDC
                directionColumn = tempDR
            }
        }

        var direction = 0

        if (directionRow == 0)
            if (directionColumn == 1)
                direction = 0
            else
                direction = 2
        else
            if (directionRow == 1)
                direction = 1
            else
                direction = 3

        println(1000 * (row + 1) + 4 * (column + 1) + direction)
    }

    override fun part1() {
        throw Exception("Please use other class.")
    }
}