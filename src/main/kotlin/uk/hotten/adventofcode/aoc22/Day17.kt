package uk.hotten.adventofcode.aoc22

import uk.hotten.adventofcode.AOCDay

class Day17: AOCDay(22, "day17test.txt") {

    val grid = mutableMapOf<Pair<Int, Int>, Boolean>()
    var mostRecentRock = 0

    val movements = mutableListOf<Char>()

    var highestY = 0

    init {
        for (char in getInput()[0])
            movements.add(char)
    }

    // The leftest variable is the location that meets the left critertia
    private fun getNextRock(leftest: Pair<Int, Int>): List<Pair<Int, Int>> {
        mostRecentRock++
        if (mostRecentRock == 6)
            mostRecentRock = 1

        val toReturn = mutableListOf<Pair<Int, Int>>()

        val x = leftest.first
        val y = leftest.second

        when (mostRecentRock) {
            1 -> { // The straight time
                toReturn.add(Pair(x,y))
                toReturn.add(Pair(x+1,y))
                toReturn.add(Pair(x+2,y))
                toReturn.add(Pair(x+3,y))
            }
            2 -> { // THe plus
                toReturn.add(Pair(x,y+1)) // Up the Y for the - in the +
                toReturn.add(Pair(x+1,y+1))
                toReturn.add(Pair(x+2,y+1))
                toReturn.add(Pair(x+1,y+2)) // For the |
                toReturn.add(Pair(x+1,y))
            }
            3 -> { // The backwards L
                toReturn.add(Pair(x,y)) // For the bottom line
                toReturn.add(Pair(x+1,y))
                toReturn.add(Pair(x+2,y))
                toReturn.add(Pair(x+2,y+1)) // For the stem
                toReturn.add(Pair(x+2,y+2))
            }
            4 -> { // Straight line
                toReturn.add(Pair(x,y))
                toReturn.add(Pair(x,y+1))
                toReturn.add(Pair(x,y+2))
                toReturn.add(Pair(x,y+3))
            }
            5 -> { // The square
                toReturn.add(Pair(x,y))
                toReturn.add(Pair(x+1,y))
                toReturn.add(Pair(x,y+1))
                toReturn.add(Pair(x+1,y+1))
            }
        }

        return toReturn.toList()
    }

    override fun part1() {

        var currentRock = 0
        var currentMovement = 0
        var movementsSize = movements.size
        while (currentRock != 2022) {
            currentRock++
            var settled = false

            var rocks = getNextRock(Pair(2, highestY+4))

            //visualise(highestY, rocks)
            var turn = 1
            while (!settled) {
                if (turn == 1) {
                    if (currentMovement == movementsSize)
                        currentMovement = 0

                    val newRocks = rocks.toMutableList()
                    var canMove = true
                    val modifier = if (movements[currentMovement] == '>') +1 else -1
                    for (i in rocks.size-1 downTo 0) {
                        val rock = rocks[i]
                        if ((modifier == +1 && rock.first == 6) || (modifier == -1 && rock.first == 0)) {
                            canMove = false
                            break
                        }

                        if (grid.contains(Pair(rock.first + modifier, rock.second))) {
                            canMove = false
                            break
                        }

                        newRocks[i] = Pair(rock.first + modifier, rock.second)
                    }

                    turn = 2
                    rocks = if (canMove) newRocks else rocks
                    currentMovement++
                    //visualise(highestY, rocks)
                    continue
                }

                // Turn 2
                val newRocks = rocks.toMutableList()
                var canMove = true
                for (i in rocks.size-1 downTo 0) {
                    val rock = rocks[i]
                    if (rock.second == 1) {
                        canMove = false
                        break
                    }

                    if (grid.contains(Pair(rock.first, rock.second - 1))) {
                        canMove = false
                        break
                    }

                    newRocks[i] = Pair(rock.first, rock.second - 1)
                }

                turn = 1
                //visualise(highestY, rocks)
                if (canMove) {
                    rocks = newRocks
                    continue
                }

                settled = true
            }

            // If it can't move

            var localHighestY = -1

            for (rock in rocks) {
                grid[rock] = true
                localHighestY = if (localHighestY <= rock.second) rock.second else localHighestY
            }

            highestY = if (localHighestY > highestY) localHighestY else highestY
        }

        //visualise(highestY, mutableListOf())
        println("After 2022 rocks, the tower is $highestY units tall.")
    }


    override fun part2() {

    }
    

    private fun visualise(highestY: Int, activeRocks: List<Pair<Int, Int>>) {
        for (y in highestY+6 downTo 1) {
            print("$y ")
            for (x in 0 .. 6) {

                var pleaseContinue = false
                for (rock in activeRocks) {
                    if (rock.first == x && rock.second == y) {
                        print("@")
                        pleaseContinue = true
                        break
                    }
                }

                if (pleaseContinue)
                    continue

                if (grid.contains(Pair(x,y)))
                    print("#")
                else
                    print(".")
            }
            print("\n")
        }
        println("-------")
    }

}