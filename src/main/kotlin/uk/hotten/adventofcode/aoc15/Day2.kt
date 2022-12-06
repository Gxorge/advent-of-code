package uk.hotten.adventofcode.aoc15

import uk.hotten.adventofcode.AOCDay

class Day2: AOCDay(15, "day2.txt") {

    override fun part1() {
        var totalWrappingNeeded = 0;
        for (line in getInput()) {
            val split = line.split('x')
            val length = split[0].toInt()
            val width = split[1].toInt()
            val height = split[2].toInt()

            val equationOne = length * width
            val equationTwo = width * height
            val equationThree = height * length
            val smallestResult = listOf(equationOne, equationTwo, equationThree).sorted()[0]

            val calc = (2*equationOne + 2*equationTwo + 2*equationThree)

            totalWrappingNeeded += calc
            totalWrappingNeeded += smallestResult

            println("Added calc of $calc and smallest of $smallestResult")
        }

        println("The elves need $totalWrappingNeeded feet of wrapping paper")
    }

    override fun part2() {
        var totalRibbonNeeded = 0;
        for (line in getInput()) {
            val split = line.split('x')
            val length = split[0].toInt()
            val width = split[1].toInt()
            val height = split[2].toInt()
            val smallestSides = listOf(length, width, height).sorted()

            val ribbonAround = (smallestSides[0] * 2) + (smallestSides[1] * 2)
            val ribbonBow = (length * width * height)

            totalRibbonNeeded += ribbonAround
            totalRibbonNeeded += ribbonBow

            println("Added ribbon around of $ribbonAround and for the bow $ribbonBow")
        }

        println("The elves need $totalRibbonNeeded feet of ribbon")
    }

}