package uk.hotten.adventofcode.aoc22

import uk.hotten.adventofcode.AOCDay

class Day24: AOCDay(22, "day24.txt") {

    val blizzard = mutableMapOf<Int, MutableSet<Pair<Int, Int>>>()
    var lastR = 0
    var lastC = 0

    init {
        for (i in 0 until 4) {
            blizzard[i] = mutableSetOf()
        }

        getInput().drop(1).forEachIndexed { row, line ->
            lastR = row
            line.substring(1).forEachIndexed { column, char ->
                print(char)
                lastC = column
                if (char in "<>^v") {
                    blizzard["<>^v".indexOf(char)]!!.add(Pair(row, column))
                }
            }
            print("\n")
        }

        println("$lastR and $lastC")
    }

    override fun part1() {
        val queue = mutableListOf<Triple<Int, Int, Int>>(Triple(0, -1, 0))
        val seen = mutableSetOf<Triple<Int, Int, Int>>()
        val moves = mutableListOf<Pair<Int, Int>>(Pair(0, 1), Pair(0, -1), Pair(-1, 0), Pair(1, 0), Pair(0, 0))
        val blizzardMoves = mutableListOf<Triple<Int, Int, Int>>(Triple(0, 0, -1),
            Triple(1, 0, 1), Triple(2, -1, 0), Triple(3, 1, 0))

        val target = Pair(lastR, lastC - 1)

        val lcm = (lastR * lastC).div(gcd(lastR, lastC))
        println(lcm)

        while (queue.isNotEmpty()) {
            val q = queue[0]
            queue.removeAt(0)
            var time = q.first
            val currentRow = q.second
            val currentColumn = q.third

            time++

            for (m in moves) {
                val dr = m.first
                val dc = m.second

                val nextRow = currentRow + dr
                val nextColumn = currentColumn + dc

                if (Pair(nextRow, nextColumn) == target) {
                    println("The fewest minutes required is $time")
                    return
                }

                if ((nextRow < 0 || nextColumn < 0 || nextRow >= lastR || nextColumn >= lastC) && Pair(nextRow, nextColumn) != Pair(-1, 0)) {
                    continue
                }

                var didBreak = false
                if (Pair(nextRow, nextColumn) != Pair(-1, 0)) {
                    for (bm in blizzardMoves) {
                        val ti = bm.first
                        val tr = bm.second
                        val tc = bm.third

                        if (blizzard[ti]!!.contains(Pair((nextRow - tr * time).mod(lastR), (nextColumn - tc * time).mod(lastC)))) {
                            didBreak = true
                            break
                        }
                    }
                }

                if (!didBreak) {
                    val key = Triple(nextRow, nextColumn, (time % lcm))
                    if (key in seen)
                        continue

                    seen.add(key)
                    queue.add(Triple(time, nextRow, nextColumn))
                }
            }
        }
    }


    private data class Quadruple(val first: Int, val second: Int, val third: Int, val forth: Int)
    override fun part2() {
        val queue = mutableListOf<Quadruple>(Quadruple(0, -1, 0, 0))
        val seen = mutableSetOf<Quadruple>()
        val moves = mutableListOf<Pair<Int, Int>>(Pair(0, 1), Pair(0, -1), Pair(-1, 0), Pair(1, 0), Pair(0, 0))
        val blizzardMoves = mutableListOf<Triple<Int, Int, Int>>(Triple(0, 0, -1),
            Triple(1, 0, 1), Triple(2, -1, 0), Triple(3, 1, 0))

        val targets = mutableListOf<Pair<Int, Int>>(Pair(lastR, lastC - 1), Pair(-1, 0))

        val lcm = (lastR * lastC).div(gcd(lastR, lastC))
        println(lcm)

        while (queue.isNotEmpty()) {
            val q = queue[0]
            queue.removeAt(0)
            var time = q.first
            val currentRow = q.second
            val currentColumn = q.third
            val stage = q.forth

            time++

            for (m in moves) {
                val dr = m.first
                val dc = m.second

                val nextRow = currentRow + dr
                val nextColumn = currentColumn + dc
                var nextStage = stage

                if (Pair(nextRow, nextColumn) == targets[stage % 2]) {
                    if (stage == 2) {
                        println("The fewest minutes required is $time")
                        return
                    }
                    nextStage++
                }

                if ((nextRow < 0 || nextColumn < 0 || nextRow >= lastR || nextColumn >= lastC) && !targets.contains(Pair(nextRow, nextColumn))) {
                    continue
                }

                var didBreak = false
                if (!targets.contains(Pair(nextRow, nextColumn))) {
                    for (bm in blizzardMoves) {
                        val ti = bm.first
                        val tr = bm.second
                        val tc = bm.third

                        if (blizzard[ti]!!.contains(Pair((nextRow - tr * time).mod(lastR), (nextColumn - tc * time).mod(lastC)))) {
                            didBreak = true
                            break
                        }
                    }
                }

                if (!didBreak) {
                    val key = Quadruple(nextRow, nextColumn, nextStage, (time % lcm))
                    if (key in seen)
                        continue

                    seen.add(key)
                    queue.add(Quadruple(time, nextRow, nextColumn, nextStage))
                }
            }
        }
    }

    private fun gcd(first: Int, second: Int): Int {
        var i = 1
        var gcd = 1
        while (i <= first && i <= second) {
            if (first % i == 0 && second % i == 0)
                gcd = i

            i++
        }

        return gcd
    }

}