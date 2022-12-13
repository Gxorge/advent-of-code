package uk.hotten.adventofcode.aoc16

import uk.hotten.adventofcode.AOCDay

class Day06: AOCDay(16, "day06.txt") {

    override fun part1() {
        val mostCommon = mutableListOf<Char>()
        for (charNumber in 0 until getInput()[0].length) {
            val apperances = mutableMapOf<Char, Int>()
            for (line in getInput()) {
                val char = line[charNumber]
                apperances.putIfAbsent(char, 0)
                apperances[char] = apperances[char]!! + 1
            }

            // Sort
            val sorted = apperances.toSortedMap(compareByDescending<Char> { apperances[it] }.thenBy { it })
            mostCommon.add(sorted.firstKey())
            println("The first column's most frequent is ${sorted.firstKey()}")
        }

        println("The word is ${mostCommon.joinToString(separator = "")}")
    }

    override fun part2() {
        val leastCommon = mutableListOf<Char>()
        for (charNumber in 0 until getInput()[0].length) {
            val apperances = mutableMapOf<Char, Int>()
            for (line in getInput()) {
                val char = line[charNumber]
                apperances.putIfAbsent(char, 0)
                apperances[char] = apperances[char]!! + 1
            }

            // Sort
            val sorted = apperances.toSortedMap(compareByDescending<Char> { apperances[it] }.thenBy { it })
            leastCommon.add(sorted.lastKey())
            println("The first column's least frequent is ${sorted.lastKey()}")
        }

        println("The word is ${leastCommon.joinToString(separator = "")}")
    }

}