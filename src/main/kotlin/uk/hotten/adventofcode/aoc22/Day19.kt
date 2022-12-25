package uk.hotten.adventofcode.aoc22

import uk.hotten.adventofcode.AOCDay
import kotlin.math.ceil
import kotlin.math.max
import kotlin.math.min

class Day19: AOCDay(22, "day19.txt") {

    private fun dfs(bp: MutableList<MutableList<Pair<Int, Int>>>, maxSpend: MutableList<Int>, cache: MutableMap<Triple<Int, MutableList<Int>, MutableList<Int>>, Int>, time: Int, bots: MutableList<Int>, amount: MutableList<Int>): Int {
        if (time == 0)
            return amount[3]

        val key = Triple(time, bots, amount)
        if (key in cache) {
            return cache[key]!!
        }

        var maxval = amount[3] + bots[3] * time

        bp.forEachIndexed lbp@ { btype, recipe ->
            if (btype != 3 && bots[btype] >= maxSpend[btype]) {
                return@lbp // continue
            }

            var wait = 0
            var didBreak = false
            for ((ramount, rtype) in recipe) {
                if (bots[rtype] == 0) {
                    didBreak = true
                    break
                }

                wait = max(wait, ceil((ramount-amount[rtype]).toDouble().div(bots[rtype])).toInt())

            }

            if (!didBreak) {
                val timeRem = time - wait - 1
                if (timeRem <= 0) {
                    return@lbp // continue
                }

                val _bots = bots.toMutableList()
                val _amount = mutableListOf<Int>()

                for (i in 0 until 4) {
                    val x = amount[i]
                    val y = bots[i]

                    _amount.add(x + y * (wait + 1))
                }

                for ((ramount, rtype) in recipe)
                    _amount[rtype] -= ramount
                _bots[btype] += 1

                for (i in 0 until 3)
                    _amount[i] = min(_amount[i], maxSpend[i] * timeRem)

                maxval = max(maxval, dfs(bp, maxSpend, cache, timeRem, _bots, _amount))
            }
        }

        cache[key] = maxval
        return maxval
    }

    override fun part1() {
        var total = 0

        val pattern = "(\\d+) (\\w+)".toRegex()
        getInput().forEachIndexed { index, line ->
            val bp = mutableListOf<MutableList<Pair<Int, Int>>>()
            val maxSpend = mutableListOf<Int>(0, 0, 0)
            for (section in line.split(": ")[1].split(". ")) {
                val recipe = mutableListOf<Pair<Int, Int>>()
                for (z in pattern.findAll(section)) {
                    val x = z.value.split(" ")[0].toInt()
                    val y = mutableListOf<String>("ore", "clay", "obsidian").indexOf(z.value.split(" ")[1])

                    recipe.add(Pair(x,y))
                    maxSpend[y] = max(maxSpend[y], x)
                }
                bp.add(recipe)
            }

            val max = dfs(bp, maxSpend, mutableMapOf(), 24, mutableListOf(1, 0, 0, 0), mutableListOf(0, 0, 0, 0))
            total += (index + 1) * max
        }

        println("The total is $total")
    }

    override fun part2() {
        var total = 1

        val pattern = "(\\d+) (\\w+)".toRegex()
        getInput().subList(0, 3).forEachIndexed { index, line ->
            val bp = mutableListOf<MutableList<Pair<Int, Int>>>()
            val maxSpend = mutableListOf<Int>(0, 0, 0)
            for (section in line.split(": ")[1].split(". ")) {
                val recipe = mutableListOf<Pair<Int, Int>>()
                for (z in pattern.findAll(section)) {
                    val x = z.value.split(" ")[0].toInt()
                    val y = mutableListOf<String>("ore", "clay", "obsidian").indexOf(z.value.split(" ")[1])

                    recipe.add(Pair(x,y))
                    maxSpend[y] = max(maxSpend[y], x)
                }
                bp.add(recipe)
            }

            val max = dfs(bp, maxSpend, mutableMapOf(), 32, mutableListOf(1, 0, 0, 0), mutableListOf(0, 0, 0, 0))
            total *= max
        }

        println("The total is $total")
    }

}