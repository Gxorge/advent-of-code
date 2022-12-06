package uk.hotten.adventofcode.aoc15

import uk.hotten.adventofcode.AOCDay

class Day4: AOCDay(15, "day4.txt") {

    override fun part1() {
        var found = false
        var currentNumber = 1
        val secret = getInput()[0]
        var hashed = ""
        while (!found) {
            hashed = "$secret$currentNumber".md5()
            var allZero = 0
            for (i in 0..4) {
                if (hashed[i] == '0')
                    allZero++
            }

            found = allZero == 5

            currentNumber++
        }

        println("The hash santa needs is $hashed and number ${currentNumber-1}")
    }

    override fun part2() {
        var found = false
        var currentNumber = 1
        val secret = getInput()[0]
        var hashed = ""
        while (!found) {
            hashed = "$secret$currentNumber".md5()
            var allZero = 0
            for (i in 0..5) {
                if (hashed[i] == '0')
                    allZero++
            }

            found = allZero == 6

            currentNumber++
        }

        println("The hash santa needs is $hashed and number ${currentNumber-1}")
    }

}