package uk.hotten.adventofcode.aoc21

import uk.hotten.adventofcode.AOCDay

class Day6: AOCDay(21, "day6.txt") {

    val fishList = mutableListOf<Byte>()

    init {
        val toAdd = getInput()[0].split(',')
        for (item in toAdd) {
            fishList.add(item.toByte())
        }
    }

    fun execute(days: Int) {
        for (day in 1 .. days) {
            println("\nStarting day $day with ${fishList.size} fish")
            var fishCreated = 0

            for (i in 0 until fishList.size) {
                fishList[i]--

                if (fishList[i] < 0) {
                    fishList[i] = 6
                    fishCreated++
                }
            }

            var debug = 0
            for (i in 0 until fishCreated) {
                fishList.add(8)
                debug++
            }

            println("Created $fishCreated fish! ($debug)")
            fishCreated = 0
            debug = 0
        }

        println("After $days days, there is now a total of ${fishList.size} fishes!")
    }

    override fun part1() {
        execute(80)
    }

    override fun part2() {
        execute(256)
    }

    data class Fish(var clock: Int)

}