package uk.hotten.adventofcode.aoc16

import uk.hotten.adventofcode.AOCDay

class Day04: AOCDay(16, "day04.txt") {

    val listOfValids = mutableListOf<String>()
    val alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray()

    override fun part1() {
        var sectorIdSum = 0;

        for (line in getInput()) {
            // Split everything
            val wholeLineSplit = line.split('[')
            val sectorLineSplit = wholeLineSplit[0].split('-').toMutableList()
            val sectorId = sectorLineSplit[sectorLineSplit.size-1].toInt()
            sectorLineSplit.removeLast()

            val sectorLine = sectorLineSplit.joinToString(separator = "");
            val providedChecksum = wholeLineSplit[1].removeSuffix(']'.toString())

            val apperances = mutableMapOf<Char, Int>()
            for (char in sectorLine) {
                apperances.putIfAbsent(char, 0)
                apperances[char] = apperances[char]!! + 1
            }

            // Sort based on alphabet and value
            val sorted = apperances.toSortedMap(compareByDescending<Char> { apperances[it] }.thenBy { it })

            var correctChecksum = ""
            var done = 0;
            for (entry in sorted.entries) {
                if (done == 5)
                    break
                correctChecksum += entry.key
                done++
            }

            if (correctChecksum == providedChecksum) {
                listOfValids.add(line)
                sectorIdSum += sectorId
            }
        }

        println("The sum of sector ids of valid rooms are $sectorIdSum")
    }

    override fun part2() {
        if (listOfValids.isEmpty()) {
            println("List of valid rooms isn't populated, running part one...")
            part1()
            println("============== DONE ==============")
        }

        val shiftedLines = mutableMapOf<String, Int>() // the shifted and its sector id

        for (line in listOfValids) {
            // Split everything
            val wholeLineSplit = line.split('[')
            val sectorLineSplit = wholeLineSplit[0].split('-').toMutableList()
            val sectorId = sectorLineSplit[sectorLineSplit.size-1].toInt()
            sectorLineSplit.removeLast()

            val sectorLine = sectorLineSplit.joinToString(separator = "");

            var newLine = ""
            var workingLine = sectorLine
            for (i in 1..sectorId) {
                for (char in workingLine) {
                    if (char == '-')
                        newLine += " "
                    else {
                        var charToAdd = '!';
                        charToAdd = if (alphabet.indexOf(char)+1 >= alphabet.size)
                            'a'
                        else
                            alphabet[alphabet.indexOf(char)+1]

                        newLine += charToAdd
                    }
                }
                workingLine = newLine
                if (i != sectorId)
                    newLine = ""
            }

            shiftedLines[newLine] = sectorId
        }

        for (entry in shiftedLines) {
            if (entry.key == "northpoleobjectstorage") { // Found by finding one that contains "objects"
                println("FOUND: ${entry.key} with sector id ${entry.value}")
                break
            }
        }
    }

}