package uk.hotten.adventofcode.aoc16

import uk.hotten.adventofcode.AOCDay

class Day1: AOCDay(16, "day1.txt") {

    override fun part1() {
        var x = 0;
        var y = 0;
        var direction = 'N'

        val line = getInput()[0].replace("\\s".toRegex(), "")
        val instructionSplit = line.split(',')

        for (instruction in instructionSplit) {
            println(instruction)
            val moveLeft = instruction[0] == 'L'
            val number = instruction.drop(1)
            val toMove = number.toInt()
            when (direction) {
                'N' -> {
                    direction = if (moveLeft) 'W' else 'E';
                    x += if(moveLeft) (toMove*-1) else toMove;
                }
                'E' -> {
                    direction = if (moveLeft) 'N' else 'S';
                    y += if(moveLeft) toMove else (toMove*-1);
                }
                'S' -> {
                    direction = if (moveLeft) 'E' else 'W';
                    x += if(moveLeft) toMove else (toMove*-1);
                }
                'W' -> {
                    direction = if (moveLeft) 'S' else 'N';
                    y += if(moveLeft) (toMove*-1) else toMove;
                }
            }
        }

        println(x+y)
    }

    override fun part2() {

    }

}