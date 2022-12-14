package uk.hotten.adventofcode.aoc22

import uk.hotten.adventofcode.AOCDay

class Day14: AOCDay(22, "day14.txt") {

    val cave = mutableMapOf<Pair<Int, Int>, Char>()

    var overallLargestX = 0
    var overallSmallestX = 0
    var overallLargestY = 0
    var overallSmallestY = 0

    init {
        var xLocs = mutableSetOf<Int>()
        var yLocs = mutableSetOf<Int>()


        for (line in getInput()) {
            val lineSplit = line.split("->")
            for (partNum in 0 until lineSplit.size-1) {
                val partOne = lineSplit[partNum].trim().split(',')
                val p1X = partOne[0].toInt()
                val p1Y = partOne[1].toInt()
                val partTwo = lineSplit[partNum+1].trim().split(',')
                val p2X = partTwo[0].toInt()
                val p2Y = partTwo[1].toInt()

                // If the x is the same, the Y is changing
                if (p1X == p2X) {
                    if (p1Y < p2Y) {
                        for (y in p1Y .. p2Y) {
                            cave[Pair(p1X, y)] = '#'
                            yLocs.add(y)
                        }
                    } else {
                        for (y in p2Y .. p1Y) {
                            cave[Pair(p1X, y)] = '#'
                            yLocs.add(y)
                        }
                    }

                // Else the x is changing
                } else {
                    if (p1X < p2X) {
                        for (x in p1X .. p2X) {
                            cave[Pair(x, p1Y)] = '#'
                            xLocs.add(x)
                        }
                    } else {
                        for (x in p2X .. p1X) {
                            cave[Pair(x, p1Y)] = '#'
                            xLocs.add(x)
                        }
                    }
                }
            }
        }

        val xLocsSorted = xLocs.sortedDescending()
        val yLocsSorted = yLocs.sortedDescending()

        overallLargestX = xLocsSorted[0] + 1
        overallSmallestX = xLocsSorted[xLocsSorted.size-1] -1
        overallLargestY = yLocsSorted[0] + 1
        overallSmallestY = 0

    }

    override fun part1() {
        for (x in overallLargestX downTo overallSmallestX) {
            for (y in overallLargestY downTo overallSmallestY) {
                if (!cave.containsKey(Pair(x,y)))
                    cave[Pair(x,y)] = '.'
            }
        }

        // Process the sand
        var sandRestedCount = 0
        var overallFinished = false
        while (!overallFinished) {
            var rested = false
            var x = 500
            var y = 0
            while (!rested) {
                if (y >= overallLargestY) {
                    overallFinished = true
                    break
                }

                val pairDown = Pair(x, y+1)
                val pairLeftDown = Pair(x-1, y+1)
                val pairRightDown = Pair(x+1, y+1)

                // If below is free
                if (cave[pairDown] == '.') {
                    y++
                    continue
                }


                // If left and down is free
                if (cave[pairLeftDown] == '.') {
                    x--
                    y++
                    continue
                }

                // If right and down is free
                if (cave[pairRightDown] == '.') {
                    x++
                    y++
                    continue
                }

                // None is free
                cave[Pair(x,y)] = 'o'
                sandRestedCount++
                rested = true
            }
        }

        visualise(null)
        println("Total sand before void is $sandRestedCount")
    }

    // In my finite wisdom, I changed the way I did this compared to P1 as I wasn't getting the answer correct
    // turns out my parsing was wrong, and I guess i just got P1 by good luck
    // so yeah I aint changing it back so exciting times
    override fun part2() {
        overallLargestX-1
        overallSmallestX+1
        overallLargestY++

        // Process the sand
        var sandRestedCount = 0
        var overallFinished = false
        while (!overallFinished) {
            var rested = false
            var x = 500
            var y = 0
            while (!rested) {

                val pairDown = Pair(x, y+1)
                val pairLeftDown = Pair(x-1, y+1)
                val pairRightDown = Pair(x+1, y+1)

                // If below is free
                if (!cave.containsKey(pairDown) && y+1 < overallLargestY) {
                    y++
                    continue
                }


                // If left and down is free
                if (!cave.containsKey(pairLeftDown) && y+1 < overallLargestY) {
                    x--
                    y++
                    continue
                }

                // If right and down is free
                if (!cave.containsKey(pairRightDown) && y+1 < overallLargestY) {
                    x++
                    y++
                    continue
                }

                // None is free
                cave[Pair(x,y)] = 'o'
                sandRestedCount++
                rested = true
                if (x == 500 && y == 0) {
                    overallFinished = true
                    break
                }
            }
        }

        visualise(null)
        println("Total sand before 500,0 is ${sandRestedCount}")
    }

    private fun visualise(extra: Pair<Int, Int>?) {
        for (y in overallSmallestY .. overallLargestY) {
            print("${y.toString()[0]} ")
            for (x in overallSmallestX-5 .. overallLargestX+8) {
                if (x == extra?.first && y == extra?.second) {
                    print('O')
                }
                else if (x == 500 && y == 0)
                    print("+")
                else
                    if (!cave.containsKey(Pair(x,y)))
                        print('.')
                    else
                        print(cave[Pair(x,y)])
            }
            print("\n")
        }
    }

}