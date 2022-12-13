package uk.hotten.adventofcode.aoc21

import uk.hotten.adventofcode.AOCDay

class Day5: AOCDay(21, "day05.txt") {

    val lines = mutableListOf<Position>()
    val posCount = mutableMapOf<Position, Int>()

    init {
        for (line in getInput()) {
            val splitToAndFrom = line.split("->")
            val from = splitToAndFrom[0].trim().split(',')
            val to = splitToAndFrom[1].trim().split(',')

            val fromX = from[0].toInt()
            val fromY = from[1].toInt()

            val toX = to[0].toInt()
            val toY = to[1].toInt()

            println(line)
            println("froms are $fromX and $fromY")
            println("tos are $toX and $toY")

            // Diagonal handling
            if (fromX != toX && fromY != toY) {
                println("Diag moment")

                continue // Comment this out to enable part 2

                lines.add(Position(fromX, fromY)) // my code doesnt include this :(

                var currX = fromX
                var currY = fromY
                while (currX != toX && currY != toY) {
                    if (currX != toX)
                        if (currX <= toX) currX++ else currX--

                    if (currY != toY)
                        if (currY <= toY) currY++ else currY--

                    println("Is now $currX,$currY")
                    lines.add(Position(currX, currY))
                }


                println("out")

                continue
            }

            if (fromX <= toX && fromY <= toY) {
                for (xCounter in fromX .. toX) {
                    for (yCounter in fromY .. toY) {
                        println("1foud inbetween of $xCounter,$yCounter")
                        lines.add(Position(xCounter, yCounter))
                    }
                }
            } else if (fromX <= toX && fromY >= toY) {
                for (xCounter in fromX .. toX) {
                    for (yCounter in fromY downTo toY) {
                        println("2foud inbetween of $xCounter,$yCounter")
                        lines.add(Position(xCounter, yCounter))
                    }
                }
            } else if (fromX >= toX && fromY <= toY) {
                for (xCounter in fromX downTo toX) {
                    for (yCounter in fromY .. toY) {
                        println("3foud inbetween of $xCounter,$yCounter")
                        lines.add(Position(xCounter, yCounter))
                    }
                }
            } else {
                for (xCounter in fromX downTo toX) {
                    for (yCounter in fromY downTo toY) {
                        println("4foud inbetween of $xCounter,$yCounter")
                        lines.add(Position(xCounter, yCounter))
                    }
                }
            }
        }
    }

    fun createTable() {
        var toIterate = lines
        for (y in 0 until 1000) {
            var string = ""
            for (x in 0 until 1000) {
                var count = 0;
                var toRemove = mutableListOf<Position>()
                for (pos in toIterate) {
                    if (pos.x == x && pos.y == y) {
                        count++
                        toRemove.add(pos)
                    }
                }
                toIterate.removeAll(toRemove)
                toRemove.clear()
                if (count == 0) string += "." else string += count
            }
            println(string)
        }
    }

    override fun part1() {
        println("line count is ${lines.size}")

        createTable()

        for (pos in lines) {
            if (posCount.containsKey(pos)) {
                var curr = posCount[pos]!!
                curr++
                posCount[pos] = curr
            } else {
                posCount[pos] = 1
            }
        }

        var moreThan = 0
        for (item in posCount) {
            if (item.value >= 2)
                moreThan++
        }

        println("idfk but it might be ${moreThan}")
    }

    override fun part2() {
        println("To run part 2, comment out the laballed continue line")
    }

    data class Position(val x: Int, val y: Int)

}