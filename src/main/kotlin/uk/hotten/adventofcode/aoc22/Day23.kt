package uk.hotten.adventofcode.aoc22

import uk.hotten.adventofcode.AOCDay
import kotlin.math.max
import kotlin.math.min

class Day23: AOCDay(22, "day23.txt") {

    var elves = mutableListOf<Pair<Int, Int>>()

    init {
        getInput().forEachIndexed { y, line ->
            line.forEachIndexed { x, char ->
                if (char == '#')
                    elves.add(Pair(x,y))
            }
        }
    }

    var currentMove = 0
    override fun part1() {
        for (round in 1 .. 10) {
            println("Round $round")

            if (currentMove == 4)
                currentMove = 0

            val proposals = mutableMapOf<Pair<Int, Int>, Pair<Int, Int>>()
            val newElves = mutableListOf<Pair<Int, Int>>()
            for (elf in elves) {

                if (!shouldMove(elf)) {
                    newElves.add(elf)
                    continue
                }

                val p = findMove(elf, currentMove)
                if (p != elf)
                    proposals[elf] = p
                else
                    newElves.add(elf)
            }

            val dontMove = mutableSetOf<Pair<Int, Int>>()
            dontMove.addAll(proposals.values.groupBy { it }.filter { it.value.size > 1 }.flatMap { it.value })


            // Move everyone
            for (p in proposals) {
                if (p.value in dontMove) {
                    newElves.add(p.key)
                    continue
                }

                newElves.add(p.value)
            }

            elves = newElves
            currentMove++
        }


        val grid = mutableMapOf<Pair<Int, Int>, Char>()

        elves.forEach { grid[it] = '#' }

        var highX = 0
        var lowX = 0
        var highY = 0
        var lowY = 0

        for (elf in elves) {
            highX = max(highX, elf.first)
            lowX = min(lowX, elf.first)

            highY = max(highY, elf.second)
            lowY = min(lowY, elf.second)
        }

        for (y in lowY .. highY) {
            for (x in lowX .. highX) {
                if (grid.contains(Pair(x,y)))
                    continue

                grid[Pair(x,y)] = '.'
            }
        }

        var emptySpaces = 0
        grid.forEach { (_, u) ->  if (u == '.') emptySpaces++ }

        visualise(grid, highX, lowX, highY, lowY)
        println("There are a total of $emptySpaces empty spaces")
    }

    override fun part2() {
        var round = 0
        while(true) {
            round++
            println("Round $round")

            if (currentMove == 4)
                currentMove = 0

            val proposals = mutableMapOf<Pair<Int, Int>, Pair<Int, Int>>()
            val newElves = mutableListOf<Pair<Int, Int>>()
            for (elf in elves) {

                if (!shouldMove(elf)) {
                    newElves.add(elf)
                    continue
                }

                val p = findMove(elf, currentMove)
                if (p != elf)
                    proposals[elf] = p
                else
                    newElves.add(elf)
            }

            val dontMove = mutableSetOf<Pair<Int, Int>>()
            dontMove.addAll(proposals.values.groupBy { it }.filter { it.value.size > 1 }.flatMap { it.value })


            // Move everyone
            var moved = false
            for (p in proposals) {
                if (p.value in dontMove) {
                    newElves.add(p.key)
                    continue
                }

                moved = true
                newElves.add(p.value)
            }

            elves = newElves
            currentMove++

            if (!moved)
                break
        }


        val grid = mutableMapOf<Pair<Int, Int>, Char>()

        elves.forEach { grid[it] = '#' }

        var highX = 0
        var lowX = 0
        var highY = 0
        var lowY = 0

        for (elf in elves) {
            highX = max(highX, elf.first)
            lowX = min(lowX, elf.first)

            highY = max(highY, elf.second)
            lowY = min(lowY, elf.second)
        }

        for (y in lowY .. highY) {
            for (x in lowX .. highX) {
                if (grid.contains(Pair(x,y)))
                    continue

                grid[Pair(x,y)] = '.'
            }
        }

        var emptySpaces = 0
        grid.forEach { (_, u) ->  if (u == '.') emptySpaces++ }

        visualise(grid, highX, lowX, highY, lowY)
        println("No elf moved at round $round.")
    }

    fun shouldMove(elf: Pair<Int, Int>): Boolean {
        val x = elf.first
        val y = elf.second

        return (
                // Left and right
                elves.contains(Pair(x-1,y)) || elves.contains(Pair(x+1,y)) ||

                        // Up and down
                        elves.contains(Pair(x,y+1)) || elves.contains(Pair(x,y-1)) ||

                        // Left diag upper and right diag upper
                        elves.contains(Pair(x-1,y+1)) || elves.contains(Pair(x+1,y+1)) ||

                        // Left diag down and right diag dopwn
                        elves.contains(Pair(x-1,y-1)) || elves.contains(Pair(x+1,y-1))
                )
    }

    fun findMove(elf: Pair<Int, Int>, wantedMove: Int, attemptsLeft: Int = 4): Pair<Int, Int> {
        val x = elf.first
        val y = elf.second

        if (attemptsLeft == 0) {
            return elf
        }

        when (wantedMove) {

            // North (up)
            0 -> {
                if (elves.contains(Pair(x,y-1)) || elves.contains(Pair(x+1,y-1)) || elves.contains(Pair(x-1,y-1))) {
                    return findMove(elf, 1, attemptsLeft-1)
                }

                return Pair(x, y-1)
            }

            // South (down)
            1 -> {
                if (elves.contains(Pair(x,y+1)) || elves.contains(Pair(x+1,y+1)) || elves.contains(Pair(x-1,y+1))) {
                    return findMove(elf, 2, attemptsLeft-1)
                }

                return Pair(x, y+1)
            }

            // West (left)
            2 -> {
                if (elves.contains(Pair(x-1,y)) || elves.contains(Pair(x-1,y+1)) || elves.contains(Pair(x-1,y-1))) {
                    return findMove(elf, 3, attemptsLeft-1)
                }

                return Pair(x-1, y)
            }

            // East (right)
            3 -> {
                if (elves.contains(Pair(x+1,y)) || elves.contains(Pair(x+1,y+1)) || elves.contains(Pair(x+1,y-1))) {
                    return findMove(elf, 0, attemptsLeft-1)
                }

                return Pair(x+1, y)
            }
        }

        return elf
    }

    private fun visualise(grid: MutableMap<Pair<Int, Int>, Char>, highX: Int, lowX: Int, highY: Int, lowY: Int) {
        for (y in lowY .. highY) {
            for (x in lowX .. highX) {
                print(grid[Pair(x,y)])
            }
            print("\n")
        }
    }

}