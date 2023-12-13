package uk.hotten.adventofcode.aoc23

import uk.hotten.adventofcode.AOCDay
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

class Day11: AOCDay(23, "day11.txt") {

    override fun part1() {
        val galaxies = mutableListOf<Pair<Int, Int>>();
        val grid = mutableMapOf<Pair<Int, Int>, Char>();

        var gridY = 0;
        var maxGridX = 0;
        for (line in getInput()) {
            var gridX = 0;
            for (char in line) {
                grid[Pair(gridX, gridY)] = char;

                if (char == '#')
                    galaxies.add(Pair(gridX, gridY));

                gridX++;
            }
            gridY++;
            maxGridX = gridX;
        }

        val emptyRows = mutableListOf<Int>();
        for (y in 0 until gridY) {
            if (!grid.any { it.key.second == y && it.value == '#' }) {
                emptyRows.add(y);
            }
        }

        val emptyColumns = mutableListOf<Int>()
        for (x in 0 until maxGridX) {
            if (!grid.any { it.key.first == x && it.value == '#' }) {
                emptyColumns.add(x);
            }
        }


        var totalSteps = 0;
        val pairDone = mutableListOf<Pair<Pair<Int, Int>, Pair<Int, Int>>>()
        for (i in 0 until galaxies.size) {
            for (j in 0 until galaxies.size) {
                val first = galaxies[i];
                val second = galaxies[j];

                if (first == second)
                    continue;

                if (pairDone.contains(Pair(first, second)) || pairDone.contains(Pair(second, first)))
                    continue;


                var steps = abs(first.first - second.first) + abs(first.second - second.second);
                var columnsDone = mutableListOf<Int>();
                var rowsDone = mutableListOf<Int>();
                for (x in min(first.first, second.first) .. max(first.first, second.first)) {
                    if (emptyColumns.contains(x) && !columnsDone.contains(x)) {
                        steps++; columnsDone.add(x);
                    }
                    for (y in min(first.second, second.second) .. max(first.second, second.second)) {
                        if (emptyRows.contains(y) && !rowsDone.contains(y)) {
                            steps++; rowsDone.add(y);
                        }
                    }
                }
                totalSteps += steps;

                pairDone.add(Pair(first, second));
            }
        }

        println(pairDone.size);
        println("The total amount of steps of the shortest paths is $totalSteps");
    }

    private fun visualise(grid: MutableMap<Pair<Int, Int>, Char>) {
        val maxX = grid.keys.maxByOrNull { it.first }?.first ?: 0
        val maxY = grid.keys.maxByOrNull { it.second }?.second ?: 0

        for (y in 0..maxY) {
            for (x in 0..maxX) {
                print(grid[Pair(x, y)]);
            }
            println();
        }
    }

    override fun part2() {
        val galaxies = mutableListOf<Pair<Int, Int>>();
        val grid = mutableMapOf<Pair<Int, Int>, Char>();

        var gridY = 0;
        var maxGridX = 0;
        for (line in getInput()) {
            var gridX = 0;
            for (char in line) {
                grid[Pair(gridX, gridY)] = char;

                if (char == '#')
                    galaxies.add(Pair(gridX, gridY));

                gridX++;
            }
            gridY++;
            maxGridX = gridX;
        }

        val emptyRows = mutableListOf<Int>();
        for (y in 0 until gridY) {
            if (!grid.any { it.key.second == y && it.value == '#' }) {
                emptyRows.add(y);
            }
        }

        val emptyColumns = mutableListOf<Int>()
        for (x in 0 until maxGridX) {
            if (!grid.any { it.key.first == x && it.value == '#' }) {
                emptyColumns.add(x);
            }
        }


        var totalSteps: Long = 0;
        val pairDone = mutableListOf<Pair<Pair<Int, Int>, Pair<Int, Int>>>()
        for (i in 0 until galaxies.size) {
            for (j in 0 until galaxies.size) {
                val first = galaxies[i];
                val second = galaxies[j];

                if (first == second)
                    continue;

                if (pairDone.contains(Pair(first, second)) || pairDone.contains(Pair(second, first)))
                    continue;


                var steps = abs(first.first - second.first) + abs(first.second - second.second);
                var columnsDone = mutableListOf<Int>();
                var rowsDone = mutableListOf<Int>();
                for (x in min(first.first, second.first) .. max(first.first, second.first)) {
                    if (emptyColumns.contains(x) && !columnsDone.contains(x)) {
                        steps += 999999; columnsDone.add(x);
                    }
                    for (y in min(first.second, second.second) .. max(first.second, second.second)) {
                        if (emptyRows.contains(y) && !rowsDone.contains(y)) {
                            steps += 999999; rowsDone.add(y);
                        }
                    }
                }
                totalSteps += steps;

                pairDone.add(Pair(first, second)); // 603050444
                                                   // 82000210
            }
        }

        println(pairDone.size);
        println("The total amount of steps of the shortest paths is $totalSteps");
    }

}