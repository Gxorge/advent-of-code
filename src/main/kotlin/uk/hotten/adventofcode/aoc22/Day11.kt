package uk.hotten.adventofcode.aoc22

import uk.hotten.adventofcode.AOCDay
import java.math.BigInteger
import kotlin.math.floor

class Day11: AOCDay(22, "day11.txt") {

    val monkeyItems = mutableMapOf<Int, MutableList<BigInteger>>()
    val monkeyOperation = mutableMapOf<Int, String>()
    val monkeyTest = mutableMapOf<Int, Int>()
    val monkeyTrue = mutableMapOf<Int, Int>()
    val monkeyFalse = mutableMapOf<Int, Int>()

    init {
        var currentMonkey = -1
        for (line in getInput()) {
            if (line.contains("Monkey")) {
                currentMonkey++

            } else if (line.contains("Starting")) {
                val initialSplit = line.split(":")
                val numSplit = initialSplit[1].trim().split(',')
                val itemList = mutableListOf<BigInteger>()
                for (num in numSplit) itemList.add(num.trim().toBigInteger())
                monkeyItems[currentMonkey] = itemList

            } else if (line.contains("Operation")) {
                val initialSplit = line.split('=')
                val spaceSplit = initialSplit[1].split(' ')
                monkeyOperation[currentMonkey] = "${spaceSplit[2]}${spaceSplit[3]}"

            } else if (line.contains("Test")) {
                val initialSplit = line.split(' ')
                monkeyTest[currentMonkey] = initialSplit.last().toInt()

            } else if (line.contains("If true")) {
                val initialSplit = line.split(' ')
                monkeyTrue[currentMonkey] = initialSplit.last().toInt()

            } else if (line.contains("If false")) {
                val initialSplit = line.split(' ')
                monkeyFalse[currentMonkey] = initialSplit.last().toInt()
            }
        }
    }

    override fun part1() {
        val amountOfMonkeys = monkeyItems.size
        val monkeyInspects = mutableMapOf<Int, Int>()

        for (monkey in 0 until amountOfMonkeys) {
            monkeyInspects[monkey] = 0
        }

        for (round in 1..20) {
            for (monkey in 0 until amountOfMonkeys) {
                for (item in monkeyItems[monkey]!!) {
                    monkeyInspects[monkey] = monkeyInspects[monkey]!! + 1
                    val operationResult = performOperation(monkeyOperation[monkey]!!, item) / "3".toBigInteger()
                    val localWorry = floor(operationResult.toDouble()).toInt().toBigInteger()

                    if (localWorry % monkeyTest[monkey]!!.toBigInteger() == "0".toBigInteger()) {
                        val monkeyToThrow = monkeyTrue[monkey]
                        monkeyItems[monkeyToThrow]!!.add(localWorry)
                    } else {
                        val monkeyToThrow = monkeyFalse[monkey]
                        monkeyItems[monkeyToThrow]!!.add(localWorry)
                    }
                }
                monkeyItems[monkey]!!.clear()
            }
        }

        val sortedInspects = monkeyInspects.toList().sortedByDescending { (_, value) -> value }.toMap()
        var amountOne = -1
        var amountTwo = -1
        for (item in sortedInspects) {
            if (amountOne == -1) {
                amountOne = item.value
                continue
            } else {
                amountTwo = item.value
                break
            }
        }

        println(sortedInspects)
        println("Monkey business is ${amountOne * amountTwo}")
    }

    private fun performOperation(operation: String, currentWorry: BigInteger): BigInteger {
        val test = operation.removeRange(0 until 1)
        val byAmount: BigInteger = if (test == "old")
            currentWorry
        else
            test.toBigInteger()

        when (operation[0]) {
            '+' -> return (currentWorry + byAmount)
            '*' -> return (currentWorry * byAmount)
        }
        return "0".toBigInteger()
    }

    override fun part2() {
        val amountOfMonkeys = monkeyItems.size
        val monkeyInspects = mutableMapOf<Int, Int>()

        for (monkey in 0 until amountOfMonkeys) {
            monkeyInspects[monkey] = 0
        }

        for (round in 1..1000) {
            for (monkey in 0 until amountOfMonkeys) {
                for (item in monkeyItems[monkey]!!) {
                    monkeyInspects[monkey] = monkeyInspects[monkey]!! + 1
                    var localWorry = performOperation(monkeyOperation[monkey]!!, item)
                    localWorry = localWorry.mod(BigInteger.valueOf(5 * 17 * 7 * 13 * 19 * 3 * 11 * 2)) // This is all the divisions from my input, i dont know why this works, thank you subreddit

                    if (localWorry % monkeyTest[monkey]!!.toBigInteger() == "0".toBigInteger()) {
                        val monkeyToThrow = monkeyTrue[monkey]
                        monkeyItems[monkeyToThrow]!!.add(localWorry)
                    } else {
                        val monkeyToThrow = monkeyFalse[monkey]
                        monkeyItems[monkeyToThrow]!!.add(localWorry)
                    }
                }
                monkeyItems[monkey]!!.clear()
            }
        }

        val sortedInspects = monkeyInspects.toList().sortedByDescending { (_, value) -> value }.toMap()
        var amountOne: BigInteger = "0".toBigInteger()
        var amountTwo: BigInteger = "0".toBigInteger()
        for (item in sortedInspects) {
            if (amountOne == "0".toBigInteger()) {
                amountOne = item.value.toBigInteger()
                continue
            } else {
                amountTwo = item.value.toBigInteger()
                break
            }
        }

        println(sortedInspects)
        println("Monkey business is ${amountOne * amountTwo}")
    }

}