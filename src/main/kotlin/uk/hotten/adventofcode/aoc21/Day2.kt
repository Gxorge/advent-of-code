package uk.hotten.adventofcode.aoc21

import uk.hotten.adventofcode.AOCDay

class Day2: AOCDay(21, "day02.txt") {

    val instructions = mutableListOf<Map<String, Int>>()

    init {
        for (line in getInput()) {
            val split = line.split(' ')
            val map = mutableMapOf<String, Int>()
            map[split[0]] = split[1].toInt()
            instructions.add(map)
        }
    }

    override fun part1() {
        var depth = 0
        var horizontal = 0

        for (line in instructions) {
            for (set in line) {
                if (set.key == "forward") horizontal += set.value
                else if (set.key == "down") depth += set.value
                else if (set.key == "up") depth -= set.value
                else println("err")
            }
        }

        println("Done! Depth is $depth and the horizontal position is $horizontal")
        println("The multiplication is is ${depth * horizontal}")
    }

    override fun part2() {
        var depth = 0
        var horizontal = 0
        var aim = 0

        for (line in instructions) {
            for (set in line) {
                if (set.key == "forward") {
                    horizontal += set.value
                    if (aim != 0)
                        depth += aim * set.value
                }
                else if (set.key == "down") aim += set.value
                else if (set.key == "up") aim -= set.value
                else println("err")
            }
        }

        println("Done! Depth is $depth, the horizontal position is $horizontal and the aim is $aim")
        println("The multiplication is is ${depth * horizontal}")
    }
}