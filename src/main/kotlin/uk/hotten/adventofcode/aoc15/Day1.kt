package uk.hotten.adventofcode.aoc15

import uk.hotten.adventofcode.AOCDay

class Day1: AOCDay(15, "day1.txt") {

    override fun part1() {
        var currentFloor = 0;
        for (char in getInput()[0]) {
            if (char == '(')
                currentFloor++
            else if (char == ')')
                currentFloor--
        }

        println("Santa ends on floor $currentFloor")
    }

    override fun part2() {

        var currentFloor = 0;
        for (i in 0..getInput()[0].length) {
            val char = getInput()[0][i]

            if (char == '(')
                currentFloor++
            else if (char == ')')
                currentFloor--

            if (currentFloor < 0) {
                println("Santa enters floor $currentFloor at position ${i+1}")
                break
            }
        }
    }

}