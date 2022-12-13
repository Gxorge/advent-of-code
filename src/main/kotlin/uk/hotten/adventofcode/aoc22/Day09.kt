package uk.hotten.adventofcode.aoc22

import uk.hotten.adventofcode.AOCDay
import kotlin.math.abs

class Day09: AOCDay(22, "day09.txt") {

    override fun part1() {
        var headX = 0
        var headY = 0
        var tailX = 0
        var tailY = 0

        val locsTailBeen = mutableSetOf<String>()

        for (line in getInput()) {
            val lineSplit = line.split(' ')
            val instruction = lineSplit[0][0]
            val byAmount = lineSplit[1].toInt()

            for (i in 1..byAmount) {
                // Calculate where the head moves too
                when (instruction) {
                    'U' -> headY++
                    'D' -> headY--
                    'R' -> headX++
                    'L' -> headX--
                }

                // Calculate if tail is adjacent by checking if its more than 1 coord away, use absolute to make it always positive
                if (abs(headX - tailX) > 1 || abs(headY - tailY) > 1) {
                    if (headX > tailX) tailX++
                    if (headX < tailX) tailX--
                    if (headY > tailY) tailY++
                    if (headY < tailY) tailY--
                }

                val loc = "$tailX,$tailY"
                locsTailBeen.add(loc)
                //visualise(headX, headY, tailX, tailY)
            }
        }

        println("The tail has been to ${locsTailBeen.size} unique locations")
    }

    override fun part2() {
        val knots = mutableListOf<MutableList<Int>>()
        for (i in 1..10) {
            knots.add(mutableListOf(0,0))
        }

        val locsTailBeen = mutableSetOf<String>()

        for (line in getInput()) {
            val lineSplit = line.split(' ')
            val instruction = lineSplit[0][0]
            val byAmount = lineSplit[1].toInt()

            for (i in 1..byAmount) {
                // Calculate where the head moves too
                when (instruction) {
                    'U' -> knots[0][1]++
                    'D' -> knots[0][1]--
                    'R' -> knots[0][0]++
                    'L' -> knots[0][0]--
                }


                for (knotNumber in 1 until 10) {
                    val currentKnot = knots[knotNumber]
                    val previousKnot = knots[knotNumber-1]

                    // Calculate if current is adjacent to prev by checking if its more than 1 coord away, use absolute to make it always positive
                    if (abs(previousKnot[0] - currentKnot[0]) > 1 || abs(previousKnot[1] - currentKnot[1]) > 1) {
                        if (previousKnot[0] > currentKnot[0]) currentKnot[0]++
                        if (previousKnot[0] < currentKnot[0]) currentKnot[0]--
                        if (previousKnot[1] > currentKnot[1]) currentKnot[1]++
                        if (previousKnot[1] < currentKnot[1]) currentKnot[1]--
                    }

                    knots[knotNumber] = currentKnot
                }

                val loc = "${knots[knots.size-1][0]},${knots[knots.size-1][1]}"
                locsTailBeen.add(loc)
            }
        }

        println("The tail (final knot) has been to ${locsTailBeen.size} unique locations")
    }

    // Used to visualise the test input for part 1
    private fun visualise(headX: Int, headY: Int, tailX: Int, tailY: Int) {
        for (y in 5 downTo 0) {
            for (x in 0..5) {
                if (x == headX && y == headY)
                    print("H")
                else if (x == tailX && y == tailY)
                    print("T")
                else
                    print(".")
            }
            print("\n")
        }
        println("====================================")
    }
}