package uk.hotten.adventofcode.aoc15

import uk.hotten.adventofcode.AOCDay

class Day6: AOCDay(15, "day06.txt") {

    override fun part1() {
        val grid = mutableMapOf<String, Int>()
        for (x in 0 until 1000) {
            for (y in 0 until 1000) {
                grid["$x,$y"] = 0
                println("$x,$y")
            }
        }

        for (line in getInput()) {
            val lineSplit = line.split(" ")

            var startX = 0;
            var startY = 0;
            var endX = 0;
            var endY = 0;

            if (!line.startsWith("toggle")) {
                var changeTo = 0;

                if (line.startsWith("turn on"))
                    changeTo = 1

                val startPos = lineSplit[2].split(',')
                val endPos = lineSplit[4].split(',')

                startX = startPos[0].toInt()
                startY = startPos[1].toInt()
                endX = endPos[0].toInt()
                endY = endPos[1].toInt()

                for (x in startX .. endX) {
                    for (y in startY .. endY) {
                        grid["$x,$y"] = changeTo
                    }
                }
            } else {
                val startPos = lineSplit[1].split(',')
                val endPos = lineSplit[3].split(',')

                startX = startPos[0].toInt()
                startY = startPos[1].toInt()
                endX = endPos[0].toInt()
                endY = endPos[1].toInt()

                for (x in startX .. endX) {
                    for (y in startY .. endY) {
                        if (grid["$x,$y"] == 1)
                            grid["$x,$y"] = 0
                        else
                            grid["$x,$y"] = 1
                    }
                }
            }
        }

        var amountOn = 0;
        for (item in grid.entries) {
            if (item.value == 1)
                amountOn++
        }
        println("There are a total of $amountOn lights on!")
    }

    override fun part2() {
        val grid = mutableMapOf<String, Int>()
        for (x in 0 until 1000) {
            for (y in 0 until 1000) {
                grid["$x,$y"] = 0
                println("$x,$y")
            }
        }

        for (line in getInput()) {
            val lineSplit = line.split(" ")

            var startX = 0;
            var startY = 0;
            var endX = 0;
            var endY = 0;

            if (!line.startsWith("toggle")) {
                var increase = false

                if (line.startsWith("turn on"))
                    increase = true

                val startPos = lineSplit[2].split(',')
                val endPos = lineSplit[4].split(',')

                startX = startPos[0].toInt()
                startY = startPos[1].toInt()
                endX = endPos[0].toInt()
                endY = endPos[1].toInt()

                for (x in startX .. endX) {
                    for (y in startY .. endY) {
                        val currentVal = grid["$x,$y"]!!
                        if (increase)
                            grid["$x,$y"] = currentVal + 1
                        else {
                            if (currentVal != 0)
                                grid["$x,$y"] = currentVal - 1
                        }
                    }
                }
            } else {
                val startPos = lineSplit[1].split(',')
                val endPos = lineSplit[3].split(',')

                startX = startPos[0].toInt()
                startY = startPos[1].toInt()
                endX = endPos[0].toInt()
                endY = endPos[1].toInt()

                for (x in startX .. endX) {
                    for (y in startY .. endY) {
                        val currentVal = grid["$x,$y"]!!
                        grid["$x,$y"] = currentVal + 2
                    }
                }
            }
        }

        var brightness = 0;
        for (item in grid.entries) {
            brightness += item.value
        }
        println("The total brightness of the lights are $brightness !")
    }

}