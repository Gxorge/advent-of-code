package uk.hotten.adventofcode.aoc23

import uk.hotten.adventofcode.AOCDay

class Day10: AOCDay(23, "day10.txt") {

    val grid = mutableMapOf<Pair<Int, Int>, Char>();
    val past = mutableListOf<Pair<Int, Int>>()
    val dotPos = mutableListOf<Pair<Int, Int>>()

    override fun part1() {
        var startPos = Pair(0, 0);

        var gridY = 0;
        for (line in getInput()) {
            var gridX = 0;
            for (char in line) {
                grid[(Pair(gridX, gridY))] = char;

                if (char == 'S')
                    startPos = Pair(gridX, gridY);
                else if (char == '.')
                    dotPos.add(Pair(gridX, gridY));

                gridX++;
            }

            gridY++;
        }

        var currentX = startPos.first;
        var currentY = startPos.second;

        past.add(startPos);
        while (true) {
            println("[$currentX, $currentY] ${grid[Pair(currentX, currentY)]}")

            if (past.size == 1) { // At start
                if (grid[Pair(currentX, currentY+1)] == '|') {
                    currentY++;
                } else if (grid[Pair(currentX, currentY-1)] == '|') {
                    currentY--;
                }

                else if (grid[Pair(currentX+1, currentY)] == '-') {
                    currentX++;
                } else if (grid[Pair(currentX-1, currentY)] == '-') {
                    currentX--;
                }

                else if (grid[Pair(currentX-1, currentY)] == 'L') {
                    currentX--;
                } else if (grid[Pair(currentX, currentY+1)] == 'L') {
                    currentY++;
                }

                else if (grid[Pair(currentX+1, currentY)] == 'J') {
                    currentX++;
                } else if (grid[Pair(currentX, currentY+1)] == 'J') {
                    currentY++;
                }

                else if (grid[Pair(currentX+1, currentY)] == '7') {
                    currentX++;
                } else if (grid[Pair(currentX, currentY-1)] == '7') {
                    currentY--;
                }

                else if (grid[Pair(currentX-1, currentY)] == 'F') {
                    currentX--;
                } else if (grid[Pair(currentX, currentY-1)] == 'F') {
                    currentY--;
                }

                past.add(Pair(currentX, currentY));
                continue;
            }

            if (grid[Pair(currentX, currentY)] == 'S') {
                println("S found")
                break;
            }

            val curr = grid[Pair(currentX, currentY)];

            if (curr == '|') {
                if (past.contains(Pair(currentX, currentY-1)))
                    currentY++;
                else
                    currentY--;
            } else if (curr == '-') {
                if (past.contains(Pair(currentX-1, currentY)))
                    currentX++;
                else
                    currentX--;
            } else if (curr == 'L') {
                if (past.contains(Pair(currentX, currentY-1)))
                    currentX++;
                else
                    currentY--;

            } else if (curr == 'J') {
                if (past.contains(Pair(currentX-1, currentY)))
                    currentY--;
                else
                    currentX--;

            } else if (curr == '7') {
                if (past.contains(Pair(currentX-1, currentY)))
                    currentY++;
                else
                    currentX--;

            } else if (curr == 'F') {
                if (past.contains(Pair(currentX+1, currentY)))
                    currentY++;
                else
                    currentX++;
            }

            past.add(Pair(currentX, currentY));
            //println(past);
            if (past.size == 3)
                past.remove(startPos);
        }

        println("${past.size/2}");
    }

    override fun part2() {
        var enclosed = 0;
        for (dot in dotPos) {
            val x = dot.first;
            val y = dot.second;

            var emptyFound = true;
            for (newX in -1..1) {
                for (newY in -1..1) {
                    if (newX == 0 && newY == 0)
                        continue;

                    if (grid.getOrDefault(Pair(x+newX, y+newY), '.') != '.')
                        emptyFound = false;
                }
            }

            if (emptyFound)
                enclosed++;
        }

        println("Enclosed spaces: $enclosed");
    }

}