package uk.hotten.adventofcode.aoc22

import uk.hotten.adventofcode.AOCDay

class Day8: AOCDay(22, "day8.txt") {

    override fun part1() {
        val lineLength = getInput()[0].length
        var visibleTrees = 0;
        for (lineNo in 1 until getInput().size-1) { // Run all lines apart from the first and last
            val line = getInput()[lineNo]
            for (charNo in 1 until lineLength-1) { // Run all chars apart from first and last, will always be visible
                val height = line[charNo].digitToInt()

                var topInvisible = false;
                var bottomInvisible = false;
                var leftInvisible = false;
                var rightInvisible = false;

                for (heightAboveCheck in lineNo-1 downTo  0) {
                    val heightAbove = getInput()[heightAboveCheck][charNo].digitToInt()
                    if (heightAbove >= height) {
                        topInvisible = true
                        break
                    }
                }

                for (heightBelowCheck in lineNo+1 until getInput().size) {
                    val heightBelow = getInput()[heightBelowCheck][charNo].digitToInt()
                    if (heightBelow >= height) {
                        bottomInvisible = true
                        break
                    }
                }

                for (heightLeftCheck in charNo-1 downTo 0) {
                    val heightLeft= line[heightLeftCheck].digitToInt()
                    if (heightLeft >= height) {
                        leftInvisible = true
                        break
                    }
                }

                for (heightRightCheck in charNo+1 until lineLength) {
                    val heightRight = line[heightRightCheck].digitToInt()
                    if (heightRight >= height) {
                        rightInvisible = true
                        break
                    }
                }

                if (!(topInvisible && bottomInvisible && leftInvisible && rightInvisible)) {
                    visibleTrees++
                }
            }
        }

        // Add the trees around the exterior
        visibleTrees += (lineLength * 2) // Top and bottom
        println("linelenght x2 is ${lineLength * 2}")
        visibleTrees += (getInput().size * 2) // Left and right
        println("sides x2 is ${getInput().size * 2}")
        visibleTrees -= 4 // Remove the edges as will have been counted twice

        println("There are a total of $visibleTrees visible trees.")
    }

    override fun part2() {
        val lineLength = getInput()[0].length
        var scenicScores = mutableListOf<Int>()
        // not point running edges as it will always be 0
        for (lineNo in 1 until getInput().size-1) { // Run all lines apart from the first and last
            val line = getInput()[lineNo]
            for (charNo in 1 until lineLength-1) { // Run all chars apart from first and last, will always be visible N
                val height = line[charNo].digitToInt()

                var topVisible = 0;
                var bottomVisible = 0;
                var leftVisible = 0;
                var rightVisible = 0;

                for (heightAboveCheck in lineNo-1 downTo  0) {
                    val heightAbove = getInput()[heightAboveCheck][charNo].digitToInt()
                    topVisible++
                    if (heightAbove >= height)
                        break
                }

                for (heightBelowCheck in lineNo+1 until getInput().size) {
                    val heightBelow = getInput()[heightBelowCheck][charNo].digitToInt()
                    bottomVisible++
                    if (heightBelow >= height)
                        break
                }

                for (heightLeftCheck in charNo-1 downTo 0) {
                    val heightLeft= line[heightLeftCheck].digitToInt()
                    leftVisible++
                    if (heightLeft >= height)
                        break
                }

                for (heightRightCheck in charNo+1 until lineLength) {
                    val heightRight = line[heightRightCheck].digitToInt()
                    rightVisible++
                    if (heightRight >= height)
                        break
                }

                scenicScores.add((topVisible * bottomVisible * leftVisible * rightVisible))
            }
        }

        scenicScores = scenicScores.sortedDescending().toMutableList()
        println("The highest scenic score is ${scenicScores[0]}")
    }

}