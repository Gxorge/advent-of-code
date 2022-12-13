package uk.hotten.adventofcode.aoc22

import com.google.gson.JsonArray
import com.google.gson.JsonParser
import uk.hotten.adventofcode.AOCDay

class Day13: AOCDay(22, "day13.txt") {

    override fun part1() {
        val packetPairs = mutableListOf<Pair<JsonArray, JsonArray>>()
        var setupPairOne: JsonArray? = null
        var sumOfIndices = 0

        for (line in getInput()) {
            if (line == " " || line == "")
                continue

            if (setupPairOne == null) {
                setupPairOne = JsonParser.parseString(line) as JsonArray
            } else {
                val setupPairTwo = JsonParser.parseString(line) as JsonArray
                packetPairs.add(Pair(setupPairOne, setupPairTwo))
                println("$setupPairOne || $setupPairTwo")
                setupPairOne = null
            }
        }

        for (pairNumber in 0 until packetPairs.size) {
            println("====== new pair ${pairNumber+1} =====")
            val pair = packetPairs[pairNumber]
            val result = compare(pair.first, pair.second, pair, 0)
            if (result == 1) sumOfIndices += (pairNumber+1)
            println("!!PAIR ${pairNumber + 1} RESULT!!: $result")
            if (result == -1)
                return
        }
        println("The sum of the right order in $sumOfIndices")
    }

    // 1 means is correct order
    // 0 means its not
    // -1 is error
    // In my comments, I refer to the global array as the whole array from a line of the input, and the local array as what is currently being worked on
    private fun compare(leftArray: JsonArray, rightArray: JsonArray, originalPair: Pair<JsonArray, JsonArray>, currentArrayIndex: Int): Int {
        println("==new comparison=")

        // Local array is empty
        if (leftArray.isEmpty || rightArray.isEmpty) {

            // If both local arrays empty, attempt to move onto next in global array
            if (leftArray.isEmpty && rightArray.isEmpty) {
                val newIndex = currentArrayIndex+1

                // If there is nothing more in the global array, return
                if (newIndex >= originalPair.first.size()) {
                    println("left has ran out of items to move to")
                    return 1
                }
                if (newIndex >= originalPair.second.size()) {
                    println("right has ran out of items to move to")
                    return 0
                }

                // If not, move onto the next local array
                var funLeft = if (originalPair.first[newIndex].toString().startsWith('[')) JsonParser.parseString(originalPair.first[newIndex].toString()) as JsonArray else JsonParser.parseString("[${originalPair.first[newIndex]}]") as JsonArray
                var funRight = if (originalPair.second[newIndex].toString().startsWith('[')) JsonParser.parseString(originalPair.second[newIndex].toString()) as JsonArray else JsonParser.parseString("[${originalPair.second[newIndex]}]") as JsonArray

                return compare(funLeft, funRight, originalPair, newIndex)
            }

            // If only one local array is empty
            if (leftArray.isEmpty) {
                println("left has ran out of items locally")
                return 1
            }

            if (rightArray.isEmpty) {
                println("right has ran out of items locally")
                return 0
            }
        }

        var left: Any = leftArray[0]
        var right: Any = rightArray[0]

        // Check if the first item in the local array is an integer
        if (isInteger(left.toString()) && isInteger(right.toString())) {
            println("is both int: $left and $right")
            left = left.toString().toInt()
            right = right.toString().toInt()

            // If left is smaller than right, it's in the right order
            if (left < right) {
                println("left is smaller")
                return 1
            } else if (left > right) {
                println("left is bigger")
                return 0
            } else if (left == right) {
                println("equal so re-running")
                leftArray.remove(0)
                rightArray.remove(0)

                return compare(leftArray, rightArray, originalPair, currentArrayIndex)
            }
        }

        // If both are arrays, rerun the compare function where itll get the first entry until it becomes an int
        if (left is JsonArray && right is JsonArray) {
            println("both array")
            return compare(left, right, originalPair, currentArrayIndex)
        }

        // If one is an array and the other is an integer, turn the integer into an array with a single entry
        if (isInteger(left.toString()) && right is JsonArray) {
            println("left is int, right is array")
            return compare(JsonParser.parseString("[$left]") as JsonArray, right, originalPair, currentArrayIndex)
        } else if (left is JsonArray && isInteger(right.toString())) {
            println("left is array, right is int")
            return compare(left, JsonParser.parseString("[$right]") as JsonArray, originalPair, currentArrayIndex)
        }

        // if all else fails
        return -1
    }



    override fun part2() {
        val packets = mutableListOf<String>()
        for (line in getInput()) {
            if (line == "" || line == " ")
                continue

            packets.add(line)
        }
        packets.add("[[2]]")
        packets.add("[[6]]")

        // Performing a bubble sort
        var swap = true
        while (swap) {
            swap = false
            for (i in 0 until packets.size-1) {
                val pair = Pair(JsonParser.parseString(packets[i]) as JsonArray, JsonParser.parseString(packets[i+1]) as JsonArray)
                if (compare(pair.first, pair.second, pair, 0) == 0) {
                    val temp = packets[i]
                    packets[i] = packets[i+1]
                    packets[i+1] = temp

                    swap = true
                }
            }
        }

        val twoLoc = packets.indexOf("[[2]]") + 1
        val sixLoc = packets.indexOf("[[6]]") + 1
        println("The locs are $twoLoc and $sixLoc, with the product being ${twoLoc * sixLoc}")
        println(packets)
    }

}