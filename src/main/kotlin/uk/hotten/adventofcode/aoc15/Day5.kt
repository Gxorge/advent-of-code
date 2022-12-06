package uk.hotten.adventofcode.aoc15

import uk.hotten.adventofcode.AOCDay

class Day5: AOCDay(15, "day5.txt") {

    val vowels = listOf('a', 'e', 'i', 'o', 'u')
    val disallow = listOf("ab", "cd", "pq", "xy")
    val letters = "abcdefghijklmnopqrstuvwxyz"

    override fun part1() {
        var nice = 0
        var naughty = 0
        for (line in getInput()) {
            var containsVowel = false;
            var containsDouble = false;
            var containsDisallow = false;

            // Vowel check
            var vowelCount = 0;
            for (char in line) {
                if (vowels.contains(char))
                    vowelCount++
            }
            containsVowel = vowelCount >= 3

            // Double check
            for (char in letters) {
                if (line.contains("$char$char")) {
                    containsDouble = true
                    break
                }
            }

            // Dissalow check
            for (disallowed in disallow) {
                if (line.contains(disallowed)) {
                    containsDisallow = true
                    break
                }
            }

            if (containsVowel && containsDouble && !containsDisallow) {
                nice++
                println("The string is nice!")
            } else {
                naughty++
                println("The string is naughty! vowel = $containsVowel double = $containsDouble disallow = $containsDisallow")
            }
        }

        println("There are a total of $nice nice strings and $naughty naughty strings")
    }

    override fun part2() {
        var nice = 0
        var naughty = 0
        for (line in getInput()) {
            println("----------------------------------------------------------------------------")
            var containsDouble = false;
            var containsFriendDouble = false;

            // Check for non-overlapping double
            var doublesFound = 0;
            val potentials = mutableListOf<String>()
            for (lineOne in line.indices) {
                val lineTwo = lineOne + 1
                if (lineTwo >= line.length)
                    break

                val charOne = line[lineOne]
                val charTwo = line[lineTwo]

                println("$charOne $charTwo")
                val toAdd = "$charOne$charTwo"

                if (lineOne == 0 && lineTwo == 1) {
                    if (line[lineOne+1] == line[lineTwo+1]) {
                        println("they equal")
                        continue
                    }
                    potentials.add(toAdd)
                    println("not found start")
                    continue
                }

                if (line[lineOne-1] != line[lineTwo-1]) {
                    if (potentials.contains(toAdd)) {
                        doublesFound++
                        println("double found")
                    } else {
                        potentials.add(toAdd)
                        println("not found")
                    }
                } else {
                    println("they equal")
                }
            }

            containsDouble = doublesFound >= 1

            // check for double with friends
            for (lineOne in line.indices) {
                val lineTwo = lineOne + 1
                val lineThree = lineOne + 2
                if (lineThree >= line.length)
                    break

                val charOne = line[lineOne]
                val charTwo = line[lineTwo]
                val charThree = line[lineThree]

                if (charOne == charThree) {
                    println("the two good chars are $charOne $charThree")
                    containsFriendDouble = true
                }
            }

            if (containsDouble && containsFriendDouble) {
                nice++
                println("nice")
            } else {
                naughty++
                println("naught $containsDouble $containsFriendDouble")
            }
        }

        println("There are a total of $nice nice strings and $naughty naughty strings")
    }

}