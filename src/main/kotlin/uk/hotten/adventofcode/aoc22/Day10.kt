package uk.hotten.adventofcode.aoc22

import uk.hotten.adventofcode.AOCDay

class Day10: AOCDay(22, "day10.txt") {

    override fun part1() {
        val awaitingExecution = mutableMapOf<String, Int>()
        var xRegister = 1
        var currentCycle = 1
        var twentySums = 0

        for (line in getInput()) {
            println(line)
            if (currentCycle > 220) {
                println("breaking $currentCycle")
                break
            }

            for (entry in awaitingExecution) {
                if (entry.value == 1) {
                    xRegister += entry.key.split(' ')[1].toInt()
                } else {
                    awaitingExecution[entry.key] = entry.value - 1
                }
            }

            if (!line.startsWith("noop")) {
                awaitingExecution[getRandomString(5) + line] = 2
            }

            if ((currentCycle % 20) == 0) {
                twentySums += currentCycle * xRegister
                println("At cycle $currentCycle, ${currentCycle*xRegister} added")
            }

            currentCycle++
        }

        println("The sums are $twentySums")
    }

    override fun part2() {
        TODO("Not yet implemented")
    }

    fun getRandomString(length: Int) : String {
        val allowedChars = ('A'..'Z') + ('a'..'z')
        return (1..length)
            .map { allowedChars.random() }
            .joinToString("")
    }

}