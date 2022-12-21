package uk.hotten.adventofcode.aoc22

import uk.hotten.adventofcode.AOCDay
import java.lang.Exception

class Day21: AOCDay(22, "day21.txt") {

    val monkeyNums = mutableMapOf<String, Long>()
    val monkeyEquations = mutableMapOf<String, Triple<Char, String, String>>()

    init {
        for (line in getInput()) {
            val lineSplit = line.split(": ")
            val monkey = lineSplit[0]

            if (isInteger(lineSplit[1])) {
                monkeyNums[monkey] = lineSplit[1].toLong()
                println("Monkey $monkey has ${lineSplit[1].toInt()}")
                continue
            }

            // If not an int is an equation
            val eqSplit = lineSplit[1].split(" ")
            val monkeyOne = eqSplit[0]
            val monkeyTwo = eqSplit[2]
            val operator = eqSplit[1]
            monkeyEquations[monkey] = Triple(operator[0], monkeyOne, monkeyTwo)
            println("Monkey $monkey has ${monkeyEquations[monkey]}")
        }
    }

    override fun part1() {
        while (!monkeyNums.containsKey("root")) {
            for (eq in monkeyEquations) {
                val monkey = eq.key
                val oper = eq.value.first
                val mOne = eq.value.second
                val mTwo = eq.value.third

                if (!(monkeyNums.containsKey(mOne) && monkeyNums.containsKey(mTwo)))
                    continue

                monkeyNums[monkey] = computeEquation(oper, monkeyNums[mOne]!!, monkeyNums[mTwo]!!)
                if (monkey == "root") {
                    println("Root has yelled ${monkeyNums[monkey]}")
                    break
                } else {
                    println("Monkey $monkey has yelled ${monkeyNums[monkey]}")
                }
            }
        }
    }

    private fun computeEquation(oper: Char, mOne: Long, mTwo: Long): Long {
        when (oper) {
            '+' -> return mOne + mTwo
            '-' -> return mOne - mTwo
            '*' -> return mOne * mTwo
            '/' -> return mOne / mTwo
        }

        throw Exception("Invalid equation operator.")
    }

    override fun part2() {
        println("please run the python code in the py directory xoxo")
    }

}