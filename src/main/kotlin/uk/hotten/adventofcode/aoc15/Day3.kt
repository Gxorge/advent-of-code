package uk.hotten.adventofcode.aoc15

import uk.hotten.adventofcode.AOCDay

class Day3: AOCDay(15, "day03.txt") {

    override fun part1() {
        val visitedHouses = mutableListOf<String>()
        var x = 0
        var y = 0
        for (char in getInput()[0]) {
            when (char) {
                '>' -> x++
                '<' -> x--
                '^' -> y++
                'v' -> y--
            }

            val location = "$x,$y"

            if (!visitedHouses.contains(location))
                visitedHouses.add(location)
        }

        println("Santa has given at least one present to ${visitedHouses.size} houses")
    }

    override fun part2() {
        val visitedHouses = mutableListOf<String>()
        var santaX = 0
        var santaY = 0

        var roboX = 0;
        var roboY = 0;
        for (i in 0..getInput()[0].length) {
            if (i >= getInput()[0].length)
                break

            val char = getInput()[0][i]
            var location = ""
            if (i % 2 == 0) { // robo santa
                when (char) {
                    '>' -> roboX++
                    '<' -> roboX--
                    '^' -> roboY++
                    'v' -> roboY--
                }
                location = "$roboX,$roboY"
            } else { // normal santa
                when (char) {
                    '>' -> santaX++
                    '<' -> santaX--
                    '^' -> santaY++
                    'v' -> santaY--
                }
                location = "$santaX,$santaY"
            }

            if (!visitedHouses.contains(location))
                visitedHouses.add(location)
        }

        println("Santa and Robo-Santa have given at least one present to ${visitedHouses.size} houses")
    }

}