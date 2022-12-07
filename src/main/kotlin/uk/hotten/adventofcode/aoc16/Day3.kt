package uk.hotten.adventofcode.aoc16

import uk.hotten.adventofcode.AOCDay

class Day3: AOCDay(16, "day3.txt") {

    override fun part1() {
        var possibleTriangles = 0
        for (line in getInput()) {
            val lineSplit = line.trim().split(' ').toMutableList()
            lineSplit.removeAll(listOf("", null))
            val sideOne = lineSplit[0].toInt()
            val sideTwo = lineSplit[1].toInt()
            val sideThree = lineSplit[2].toInt()

            if ((sideOne + sideTwo) > sideThree && (sideOne + sideThree) > sideTwo && (sideTwo + sideThree) > sideOne)
                possibleTriangles++

        }

        println("There are a total of $possibleTriangles possible triangles.")
    }

    override fun part2() {
        var possibleTriangles = 0
        var sideOne = -1
        var sideTwo = -1
        var sideThree = -1

        // First column
        for (line in getInput()) {
            val lineSplit = line.trim().split(' ').toMutableList()
            lineSplit.removeAll(listOf("", null))
            if (sideOne == -1)
                sideOne = lineSplit[0].toInt()
            else if (sideTwo == -1)
                sideTwo = lineSplit[0].toInt()
            else if (sideThree == -1) {
                sideThree = lineSplit[0].toInt()

                if ((sideOne + sideTwo) > sideThree && (sideOne + sideThree) > sideTwo && (sideTwo + sideThree) > sideOne)
                    possibleTriangles++

                sideOne = -1
                sideTwo = -1
                sideThree = -1
            }
        }

        // Second column
        for (line in getInput()) {
            val lineSplit = line.trim().split(' ').toMutableList()
            lineSplit.removeAll(listOf("", null))
            if (sideOne == -1)
                sideOne = lineSplit[1].toInt()
            else if (sideTwo == -1)
                sideTwo = lineSplit[1].toInt()
            else if (sideThree == -1) {
                sideThree = lineSplit[1].toInt()

                if ((sideOne + sideTwo) > sideThree && (sideOne + sideThree) > sideTwo && (sideTwo + sideThree) > sideOne)
                    possibleTriangles++

                sideOne = -1
                sideTwo = -1
                sideThree = -1
            }
        }

        // third column
        for (line in getInput()) {
            val lineSplit = line.trim().split(' ').toMutableList()
            lineSplit.removeAll(listOf("", null))
            if (sideOne == -1)
                sideOne = lineSplit[2].toInt()
            else if (sideTwo == -1)
                sideTwo = lineSplit[2].toInt()
            else if (sideThree == -1) {
                sideThree = lineSplit[2].toInt()

                if ((sideOne + sideTwo) > sideThree && (sideOne + sideThree) > sideTwo && (sideTwo + sideThree) > sideOne)
                    possibleTriangles++

                sideOne = -1
                sideTwo = -1
                sideThree = -1
            }
        }

        println("There are a total of $possibleTriangles possible triangles.")
    }

}