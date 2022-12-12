package uk.hotten.adventofcode.aoc22

import uk.hotten.adventofcode.AOCDay

// This uses the Breadth-first search algorithm
class Day12: AOCDay(22, "day12.txt") {

    val grid = mutableMapOf<Pair<Int, Int>, Char>()
    var startLocation = Pair(0,0)
    var endLocation = Pair(0,0)

    var numberOfRows = 0
    var numberOfColumns = 0

    // Put the data into a grid, with the top left corner being 0,0
    init {
        numberOfRows = getInput().size
        numberOfColumns = getInput()[0].length

        var column = 0
        var row = 0
        for (line in getInput()) {
            for (char in line) {
                grid[Pair(row,column)] = char
                if (char == 'S')
                    startLocation = Pair(row,column)
                else if (char == 'E')
                    endLocation = Pair(row,column)
                column++
            }
            column = 0
            row++
        }

        println(startLocation)
        println(endLocation)
    }

    override fun part1() {
        val rowQueue = mutableListOf<Int>()
        val columnQueue = mutableListOf<Int>()

        var moveCount = 0
        var nodesLeftInLayer = 1
        var nodesInNextLayer = 0

        var reachedEnd = false

        val visited = mutableListOf<Pair<Int, Int>>()

        val rowVectors = mutableListOf<Int>(-1, +1, 0, 0)
        val columnVectors = mutableListOf<Int>(0, 0, +1, -1)


        rowQueue.add(startLocation.first)
        columnQueue.add(startLocation.second)
        visited.add(startLocation)
        //visualise(visited)

        while (rowQueue.size > 0) {
            val row = rowQueue[0]
            val column = columnQueue[0]
            val loc = Pair(row, column)
            val locWeight = getWeight(grid[loc]!!)

            rowQueue.removeAt(0)
            columnQueue.removeAt(0)

            if (endLocation == loc) {
                reachedEnd = true
                break
            }

            // Explore neighbours
            for (i in 0 until 4) {
                val nextRow = row + rowVectors[i]
                val nextColumn = column + columnVectors[i]
                val nextLoc = Pair(nextRow, nextColumn)

                if (nextRow < 0 || nextColumn < 0) continue
                if (!grid.containsKey(nextLoc)) continue

                if (visited.contains(nextLoc)) continue

                val newWeight = getWeight(grid[nextLoc]!!)
                if (!canStepP1(locWeight, newWeight)) continue

                rowQueue.add(nextRow)
                columnQueue.add(nextColumn)
                visited.add(nextLoc)
                //visualise(visited)
                nodesInNextLayer++
            }

            nodesLeftInLayer--
            if (nodesLeftInLayer == 0) {
                nodesLeftInLayer = nodesInNextLayer
                nodesInNextLayer = 0
                moveCount++
            }
            //visualise(visited)
        }

        if (reachedEnd) {
            println("The shorted route takes $moveCount steps")
        } else {
            println("Couldn't reach end :(, $moveCount")
        }
        visualise(visited)
    }

    private fun canStepP1(currentWeight: Int, newWeight: Int): Boolean {
        if (currentWeight == newWeight) return true

        if (currentWeight + 1 == newWeight || newWeight < currentWeight) return true

        return false
    }

    // The only difference is the code is reversed, starting at E and working until first A is found, and the can step function is reversed
    override fun part2() {
        val rowQueue = mutableListOf<Int>()
        val columnQueue = mutableListOf<Int>()

        var moveCount = 0
        var nodesLeftInLayer = 1
        var nodesInNextLayer = 0

        var reachedEnd = false

        val visited = mutableListOf<Pair<Int, Int>>()

        val rowVectors = mutableListOf<Int>(-1, +1, 0, 0)
        val columnVectors = mutableListOf<Int>(0, 0, +1, -1)


        rowQueue.add(endLocation.first)
        columnQueue.add(endLocation.second)
        visited.add(endLocation)
        //visualise(visited)

        while (rowQueue.size > 0) {
            val row = rowQueue[0]
            val column = columnQueue[0]
            val loc = Pair(row, column)
            val locWeight = getWeight(grid[loc]!!)

            rowQueue.removeAt(0)
            columnQueue.removeAt(0)

            if (grid[loc] == 'a') {
                println(grid[loc])
                reachedEnd = true
                break
            }

            // Explore neighbours
            for (i in 0 until 4) {
                val nextRow = row + rowVectors[i]
                val nextColumn = column + columnVectors[i]
                val nextLoc = Pair(nextRow, nextColumn)

                if (nextRow < 0 || nextColumn < 0) continue
                if (!grid.containsKey(nextLoc)) continue

                if (visited.contains(nextLoc)) continue

                val newWeight = getWeight(grid[nextLoc]!!)
                if (!canStepP2(locWeight, newWeight)) continue

                rowQueue.add(nextRow)
                columnQueue.add(nextColumn)
                visited.add(nextLoc)
                //visualise(visited)
                nodesInNextLayer++
            }

            nodesLeftInLayer--
            if (nodesLeftInLayer == 0) {
                nodesLeftInLayer = nodesInNextLayer
                nodesInNextLayer = 0
                moveCount++
            }
            //visualise(visited)
        }

        if (reachedEnd) {
            println("The shorted route takes $moveCount steps")
        } else {
            println("Couldn't reach end :(, $moveCount")
        }
        visualise(visited)
    }

    private fun canStepP2(currentWeight: Int, newWeight: Int): Boolean {
        if (currentWeight == newWeight) return true

        if (currentWeight - 1 == newWeight || newWeight > currentWeight) return true

        return false
    }

    val elevationValues: CharArray = "abcdefghijklmnopqrstuvwxyz".toCharArray()
    private fun getWeight(char: Char): Int {
        if (char == 'E')  return elevationValues.indexOf('z') + 1
        else if (char == 'S') return elevationValues.indexOf('a') + 1

        return elevationValues.indexOf(char) + 1;
    }

    private fun visualise(visted: MutableList<Pair<Int,Int>>,) {
        println("==============================================")
        for (row in 0 until numberOfRows) {
            for (column in 0 until numberOfColumns) {
                var loc = Pair(row,column)
                if (visted.last() == loc)
                    print('@')
                else if (visted.contains(loc))
                    print('#')
                else
                    print(grid[loc])
            }
            print("\n")
        }
    }

}