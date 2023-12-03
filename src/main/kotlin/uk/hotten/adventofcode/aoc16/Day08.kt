package uk.hotten.adventofcode.aoc16

import uk.hotten.adventofcode.AOCDay

class Day08: AOCDay(16, "day08.txt") {
    override fun part1() {
        var grid = mutableMapOf<Pair<Int, Int>, Boolean>();
        for (x in 0 until 50) {
            for (y in 0 until 6) {
                grid[Pair(x,y)] = false;
            }
        }

        for (line in getInput()) {
            val split = line.split(" ");
            println(line)
            if (split[0] == "rect") {
                val coords = split[1].split('x');
                val width = Integer.parseInt(coords[0]);
                val height = Integer.parseInt(coords[1]);

                for (x in 0 until width) {
                    for (y in 0 until height) {
                        grid[Pair(x,y)] = true;
                    }
                }
            } else {
                if (split[1] == "row") { // right
                    val row = Integer.parseInt("" + split[2][2]);
                    val by = Integer.parseInt(split[4]);
                    val on = mutableListOf<Pair<Int, Int>>();

                    for (x in 0 until 50) {
                        if (!grid[Pair(x, row)]!!)
                            continue;

                        var newX = x;
                        for (i in 0 until by) {
                            newX++;
                            if (newX > 49)
                                newX = 0;
                        }

                        on.add(Pair(newX, row));
                    }

                    for (x in 0 until 50) {
                        grid[Pair(x, row)] = false;
                    }

                    for (x in 0 until 50) {
                        grid[Pair(x, row)] = on.contains(Pair(x, row));
                    }
                } else {
                    val column = Integer.parseInt("" + split[2][2]);
                    val by = Integer.parseInt(split[4]);
                    val on = mutableListOf<Pair<Int, Int>>();
                    for (y in 0 until 6) {
                        if (!grid[Pair(column, y)]!!)
                            continue;

                        var newY = y;
                        for (i in 0 until by) {
                            newY++;
                            if (newY > 5)
                                newY = 0;
                        }

                        on.add(Pair(column, newY));
                    }

                    for (y in 0 until 6) {
                        grid[Pair(column, y)] = false;
                    }

                    for (y in 0 until 6) {
                        grid[Pair(column, y)] = on.contains(Pair(column, y));
                    }
                }
            }

            var litUp = 0;
            for (y in 0 until 6) {
                var s = "";
                for (x in 0 until 50) {
                    if (grid[Pair(x, y)]!!) {
                        s += "#";
                        litUp++;
                    } else
                        s += ".";
                }
                println(s);
            }
            println("Total lit-up pixels: $litUp");
        }

    }

    override fun part2() {
        //TODO("Not yet implemented")
    }
}