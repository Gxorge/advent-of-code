package uk.hotten.adventofcode.aoc22

import uk.hotten.adventofcode.AOCDay

class Day22: AOCDay(22, "day22.txt") {

    val grid = mutableMapOf<Pair<Int, Int>, Char>()
    var furthestX = 0
    var furthestY = 0
    var firstLineFirstDot = -1

    val gridXBounds = mutableMapOf<Int, Pair<Int, Int>>()
    val gridYBounds = mutableMapOf<Int, Pair<Int, Int>>()

    init {
        getInput().forEach { if (!it.contains("\\d")) { furthestX = Math.max(furthestX, it.length) } }

        var y = 1
        var x = 1
        for (line in getInput()) {
            if (line == "") {
                println(line)
                break
            }

            var lowBound = -1
            var highBound = line.length

            for (c in 0 until furthestX) {

                if (c >= line.length) {
                    gridXBounds[y] = Pair(lowBound, highBound)
                    break
                }

                if (firstLineFirstDot == -1 && line[c] == '.')
                    firstLineFirstDot = x

                if (lowBound == -1 && line[c] != ' ')
                    lowBound = x

                grid[Pair(x,y)] = line[c]
                x++
                gridXBounds[y] = Pair(lowBound, highBound)
            }

            y++
            x = 1
        }
        furthestY = y

        // Parse the Y bounds
        for (x in 1 until furthestX) {
            var first: Int? = null
            var second: Int? = null
            for (y in 1 until furthestY) {
                if (first == null && grid.getOrDefault(Pair(x,y), ' ') != ' ') {
                    first = y
                    continue
                }

                if (first != null && second == null && grid.getOrDefault(Pair(x,y+1), ' ') == ' ') {
                    second = y
                    gridYBounds[x] = Pair(first, second)
                    continue
                }
            }
        }
    }


    override fun part1() {
        val pattern = """(\d+|[RL])""".toRegex()
        val instructions = pattern.findAll(getInput()[furthestY]).map { it.value }

        var x = firstLineFirstDot
        var y = 1
        var direction = 0

        for (ins in instructions) {
            if (isInteger(ins)) {
                val toMove = ins.toInt()
                for (curr in 0 until toMove) {
                    val move = canMove(x, y, direction)
                    if (move.first) {
                        x = move.second.first
                        y = move.second.second
                    } else {
                        break
                    }
                }
            } else {
                direction = newDirection(ins[0], direction)
            }
        }

        val row = 1000 * y
        val column = 4 * x
        val result = row + column + direction
        println("The final password is $result")
    }

    private fun canMove(x: Int, y: Int, direction: Int): Pair<Boolean, Pair<Int, Int>> {
        when (direction) {
            // Right
            0 -> {
                if (x == gridXBounds[y]!!.second)
                    return Pair(grid.getOrDefault(Pair(gridXBounds[y]!!.first, y), '#') != '#', Pair(gridXBounds[y]!!.first, y))
                else
                    return Pair(grid.getOrDefault(Pair(x+1, y), '#') != '#', Pair(x+1, y))
            }
            // Down
            1 -> {
                if (y == gridYBounds[x]!!.second)
                    return Pair(grid.getOrDefault(Pair(x, gridYBounds[x]!!.first), '#') != '#', Pair(x, gridYBounds[x]!!.first))
                else
                    return Pair(grid.getOrDefault(Pair(x, y+1), '#') != '#', Pair(x, y+1))
            }
            // Left
            2 -> {
                if (x == gridXBounds[y]!!.first)
                    return Pair(grid.getOrDefault(Pair(gridXBounds[y]!!.second, y), '#') != '#', Pair(gridXBounds[y]!!.second, y))
                else
                    return Pair(grid.getOrDefault(Pair(x-1, y), '#') != '#', Pair(x-1, y))
            }
            // Up
            3 -> {
                if (y == gridYBounds[x]!!.first)
                    return Pair(grid.getOrDefault(Pair(x, gridYBounds[x]!!.second), '#') != '#', Pair(x, gridYBounds[x]!!.second))
                else
                    return Pair(grid.getOrDefault(Pair(x, y-1), '#') != '#', Pair(x, y-1))
            }
        }
        return Pair(false, Pair(x,y))
    }

    private fun newDirection(newDirection: Char, currentDirection: Int): Int {
        when (newDirection) {
            'L' -> {
                when (currentDirection) {
                    0 -> return 3
                    1 -> return 0
                    2 -> return 1
                    3 -> return 2
                }
            }
            'R' -> {
                when (currentDirection) {
                    0 -> return 1
                    1 -> return 2
                    2 -> return 3
                    3 -> return 0
                }
            }
        }
        throw Exception("Invalid new or current direction")
    }


    override fun part2() {
        Day22P2().part2()
    }

    private fun visualise() {
        for (y in 1 .. furthestY) {
            for (x in 1 .. furthestX) {
                if (grid[Pair(x,y)] == null)
                    print("")
                else
                    print(grid[Pair(x,y)])
            }
            print("\n")
        }
    }

}