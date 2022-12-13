package uk.hotten.adventofcode.aoc16

import uk.hotten.adventofcode.AOCDay

class Day07: AOCDay(16, "day07.txt") {

    // I'll be honest I got this from chatgpt
    val abbaPattern = Regex("(.)(?!\\1)(.)\\2\\1");
    val sbPattern = Regex("\\[.*?\\]")

    override fun part1() {
        var sumOfSupportingIps = 0
        val supports = mutableListOf<String>()

        for (line in getInput()) {
            var sbHasAbba = false
            for (sb in sbPattern.findAll(line)) {
                if (sb.value.contains(abbaPattern)) {
                    sbHasAbba = true
                }
            }
            if (sbHasAbba) {
                println("sb has abba")
                continue
            }

            if (line.contains(abbaPattern)) {
                sumOfSupportingIps++
                supports.add(line)
                print("supports tls")
            }
        }

        println("A total of $sumOfSupportingIps support TLS.")
        for (line in supports) {
            println(line)
        }
    }

    override fun part2() {
        TODO("Not yet implemented")
    }

}