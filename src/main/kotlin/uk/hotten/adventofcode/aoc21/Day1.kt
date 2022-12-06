package uk.hotten.adventofcode.aoc21

import uk.hotten.adventofcode.AOCDay

class Day1: AOCDay(21, "day1.txt") {

    val nums = mutableListOf<Int>()

    init {
        for (line in getInput())
            nums.add(line.toInt())
    }

    override fun part1() {
        var increased = 0
        var decreased = 0

        for (i in 1 until nums.size) { // Ignoring 0 as there is nothing before it
            val curr = nums[i]
            val before = nums[i-1]

            if (curr > before)
                increased++
            else
                decreased++
        }

        println("Increased: $increased, decreased: $decreased")
    }

    override fun part2() {
        var increased = 0
        var decreased = 0

        var previous = -1
        for (i in 0 until nums.size) {
            try {
                val a = nums[i]
                val b = nums[i+1]
                val c = nums[i+2]

                val total = a + b + c

                if (previous == -1) {
                    previous = total
                    continue
                }

                if (total > previous) {
                    increased++
                } else {
                    decreased++
                }

                previous = total
            } catch (e: Exception) {
                println("currently on $i/${nums.size}, if adding 1 and 2 is going over then this is intentional")
                break
            }
        }

        println("Increased: $increased, decreased: $decreased")
    }

}