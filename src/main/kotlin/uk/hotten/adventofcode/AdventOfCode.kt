package uk.hotten.adventofcode

import uk.hotten.adventofcode.aoc23.Day11


fun main() {

    val day = Day11();

    val time = System.currentTimeMillis()

    day.part1()
    println("Part 1 took ${System.currentTimeMillis() - time}ms to run.")

    val timeP2 = System.currentTimeMillis()
    day.part2()
    println("Part 2 took ${System.currentTimeMillis() - timeP2}ms to run.")

    println("Both parts took ${System.currentTimeMillis() - time}ms to run.")
    
}