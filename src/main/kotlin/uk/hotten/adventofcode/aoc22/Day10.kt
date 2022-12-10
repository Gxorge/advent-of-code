package uk.hotten.adventofcode.aoc22

import uk.hotten.adventofcode.AOCDay

class Day10: AOCDay(22, "day10.txt") {

    override fun part1() {
        val toAdd = listOf<Int>(20, 60, 100, 140, 180, 220)
        val awaitingExecution = mutableMapOf<String, Int>()
        var xRegister = 1
        var twentySums = 0

        for (currentCycle in 1..220) {
            println("===== Starting cycle $currentCycle =====")

            if (currentCycle <= getInput().size) {
                val line = getInput()[currentCycle-1]
                println("Added $line to q")
                if (line.contains("noop"))
                    awaitingExecution[getRandomString(5) + line] = 1
                else
                    awaitingExecution[getRandomString(5) + line] = 2
            }

            if (toAdd.contains(currentCycle)) {
                twentySums += currentCycle * xRegister
                println("At cycle $currentCycle, ${currentCycle*xRegister} added")
            }

            val toRemove = mutableListOf<String>()
            for (entry in awaitingExecution) {
                if (entry.value == 1) {
                    println("Executing ${entry.key}")
                    if (!entry.key.contains("noop"))
                        xRegister += entry.key.split(' ')[1].toInt()
                    toRemove.add(entry.key)
                } else {
                    println("Started processing ${entry.key}")
                    awaitingExecution[entry.key] = entry.value - 1
                }
                break
            }

            toRemove.forEach {
                awaitingExecution.remove(it)
            }

            println("At end of cycle $currentCycle, x is $xRegister")
        }

        println("The sums are $twentySums")
    }

    override fun part2() {
        val awaitingExecution = mutableMapOf<String, Int>()
        var xRegister = 1
        var currentColumn = 0

        for (currentCycle in 1..240) {

            if (currentCycle <= getInput().size) {
                val line = getInput()[currentCycle-1]
                if (line.contains("noop"))
                    awaitingExecution[getRandomString(5) + line] = 1
                else
                    awaitingExecution[getRandomString(5) + line] = 2
            }

            val spriteLeft = xRegister - 1
            val spriteMiddle = xRegister
            val spriteRight = xRegister + 1

            if (currentColumn == spriteLeft || currentColumn == spriteMiddle || currentColumn == spriteRight)
                print("###")
            else
                print("...")

            val toRemove = mutableListOf<String>()
            for (entry in awaitingExecution) {
                if (entry.value == 1) {
                    if (!entry.key.contains("noop"))
                        xRegister += entry.key.split(' ')[1].toInt()
                    toRemove.add(entry.key)
                } else {
                    awaitingExecution[entry.key] = entry.value - 1
                }
                break
            }

            toRemove.forEach {
                awaitingExecution.remove(it)
            }

            currentColumn++
            if (currentColumn == 40) {
                print("\n")
                currentColumn = 0
            }
        }
    }


    fun getRandomString(length: Int) : String {
        val allowedChars = ('A'..'Z') + ('a'..'z')
        return (1..length)
            .map { allowedChars.random() }
            .joinToString("")
    }

}