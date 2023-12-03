package uk.hotten.adventofcode.aoc23

import uk.hotten.adventofcode.AOCDay

class Day03: AOCDay(23, "day03.txt") {
    override fun part1() {
        val grid = mutableMapOf<Pair<Int, Int>, Char>();

        // Put data into a grid
        var gridY = 0;
        var maxGridX = 0;
        for (line in getInput()) {
            var gridX = 0;
            for (char in line) {
                grid[Pair(gridX, gridY)] = char;
                gridX++;
            }
            gridY++;
            maxGridX = gridX;
        }

        // Parse the numbers and check if they are valid part numbers
        var partNumberSum = 0;
        for (y in 0..gridY) {
            var stringNums = "";
            val locs = mutableListOf<Pair<Int, Int>>();

            for (x in 0..maxGridX) {
                val char = grid[Pair(x, y)];

                if (isInteger(char)) {
                    stringNums += char;
                    locs.add(Pair(x, y));
                } else if (stringNums != "") {
                    val part = Integer.parseInt(stringNums);
                    partNumberSum += checkPartNumber(grid, part, locs);
                    stringNums = "";
                    locs.clear();
                }
            }
            if (stringNums != "") {
                val part = Integer.parseInt(stringNums);
                partNumberSum += checkPartNumber(grid, part, locs);
                stringNums = "";
                locs.clear();
            }
        }

        println("The sum of all valid part numbers is $partNumberSum");
    }

    private fun checkPartNumber(grid: MutableMap<Pair<Int, Int>, Char>, part: Int, locs: MutableList<Pair<Int, Int>>): Int {
        for (loc in locs) {
            val x = loc.first;
            val y = loc.second;

            if (isValidAdjacent(grid[Pair(x+1, y)]) || isValidAdjacent(grid[Pair(x-1, y)]) // Left right
                || isValidAdjacent(grid[Pair(x, y+1)]) || isValidAdjacent(grid[Pair(x, y-1)]) // Up down
                || isValidAdjacent(grid[Pair(x-1, y-1)]) || isValidAdjacent(grid[Pair(x-1, y+1)]) // Left diagonal
                || isValidAdjacent(grid[Pair(x+1, y-1)]) || isValidAdjacent(grid[Pair(x+1, y+1)])) // Right diagonal
            {
                return part;
            }
        }

        return 0;
    }

    private fun isValidAdjacent(char: Char?): Boolean {
        if (char == null)
            return false;

        return !isInteger(char) && char != '.';
    }

    override fun part2() {
        val grid = mutableMapOf<Pair<Int, Int>, Char>();
        val gearLocations = mutableMapOf<Pair<Int, Int>, MutableList<Int>>();

        // Put data into a grid
        var gridY = 0;
        var maxGridX = 0;
        for (line in getInput()) {
            var gridX = 0;
            for (char in line) {
                grid[Pair(gridX, gridY)] = char;
                if (char == '*') {
                    gearLocations[Pair(gridX, gridY)] = mutableListOf();
                }
                gridX++;
            }
            gridY++;
            maxGridX = gridX;
        }

        // Parse the numbers and check if they have an adjacent gear
        for (y in 0..gridY) {
            var stringNums = "";
            val locs = mutableListOf<Pair<Int, Int>>();

            for (x in 0..maxGridX) {
                val char = grid[Pair(x, y)];

                if (isInteger(char)) {
                    stringNums += char;
                    locs.add(Pair(x, y));
                } else if (stringNums != "") {
                    val part = Integer.parseInt(stringNums);
                    val gearLoc = checkPartNumberForGear(grid, locs);
                    if (gearLoc != null) {
                        gearLocations[Pair(gearLoc.first, gearLoc.second)]!!.add(part);
                    }
                    stringNums = "";
                    locs.clear();
                }
            }
            if (stringNums != "") {
                val part = Integer.parseInt(stringNums);
                val gearLoc = checkPartNumberForGear(grid, locs);
                if (gearLoc != null) {
                    gearLocations[Pair(gearLoc.first, gearLoc.second)]!!.add(part);
                }
                stringNums = "";
                locs.clear();
            }
        }

        var sumOfGearRatios = 0;
        for (gear in gearLocations) {
            if (gear.value.size == 2) {
                sumOfGearRatios += gear.value[0] * gear.value[1];
            }
        }

        println("Sum of all gear ratios is $sumOfGearRatios");
    }

    private fun checkPartNumberForGear(grid: MutableMap<Pair<Int, Int>, Char>, locs: MutableList<Pair<Int, Int>>): Pair<Int, Int>? {
        for (loc in locs) {
            val x = loc.first;
            val y = loc.second;

            // Left right
            if (isValidAdjacentForGear(grid[Pair(x+1, y)]))
                return Pair(x+1, y);
            else if (isValidAdjacentForGear(grid[Pair(x-1, y)]))
                return Pair(x-1, y);

            // Up down
            else if (isValidAdjacentForGear(grid[Pair(x, y+1)]))
                return Pair(x, y+1);
            else if (isValidAdjacentForGear(grid[Pair(x, y-1)]))
                return Pair(x, y-1);

            // Left diagonal
            else if (isValidAdjacentForGear(grid[Pair(x-1, y-1)]))
                return Pair(x-1, y-1);
            else if (isValidAdjacentForGear(grid[Pair(x-1, y+1)]))
                return Pair(x-1, y+1);

            // Right diagonal
            else if (isValidAdjacentForGear(grid[Pair(x+1, y-1)]))
                return Pair(x+1, y-1);
            else if (isValidAdjacentForGear(grid[Pair(x+1, y+1)]))
                return Pair(x+1, y+1);
        }

        return null;
    }

    private fun isValidAdjacentForGear(char: Char?): Boolean {
        if (char == null)
            return false;

        return char == '*';
    }
}