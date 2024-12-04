package uk.hotten.adventofcode.aoc24

import uk.hotten.adventofcode.AOCDay

class Day04: AOCDay(24, "day04.txt") {

    val inputList: List<String>;

    init {
        inputList = getInput();
    }

    override fun part1() {
        var totalXmas = 0;

        for (row in inputList.indices) {
            for (column in inputList[0].indices) {
                if (inputList[row][column] != 'X') continue;

                for (rowCounter in -1 .. 1) {
                    for (columnCounter in -1 .. 1) {
                        if (row+3*rowCounter < 0 || column+3*columnCounter < 0) continue;
                        if (row+3*rowCounter >= inputList.size || column+3*columnCounter >= inputList[0].length) continue;
                        if (rowCounter == 0 && columnCounter == 0) continue;

                        if (inputList[row+rowCounter][column+columnCounter] == 'M'
                            && inputList[row+2*rowCounter][column+2*columnCounter] == 'A'
                            && inputList[row+3*rowCounter][column+3*columnCounter] == 'S')
                            totalXmas++;
                    }
                }
            }
        }

        println("Total XMAS: $totalXmas")
    }

    override fun part2() {
        var totalXmas = 0;

        for (row in 1 until inputList.size-1) {
            for (column in 1 until inputList[0].length-1) {
                if (inputList[row][column] != 'A') continue;


                if (inputList[row-1][column-1] == 'M'
                    && inputList[row-1][column+1] == 'S'
                    && inputList[row+1][column-1] == 'M'
                    && inputList[row+1][column+1] == 'S') {
                    totalXmas++;
                    continue;
                }

                if (inputList[row-1][column-1] == 'S'
                    && inputList[row-1][column+1] == 'M'
                    && inputList[row+1][column-1] == 'S'
                    && inputList[row+1][column+1] == 'M') {
                    totalXmas++;
                    continue;
                }

                if (inputList[row-1][column-1] == 'S'
                    && inputList[row-1][column+1] == 'S'
                    && inputList[row+1][column-1] == 'M'
                    && inputList[row+1][column+1] == 'M') {
                    totalXmas++;
                    continue;
                }

                if (inputList[row-1][column-1] == 'M'
                    && inputList[row-1][column+1] == 'M'
                    && inputList[row+1][column-1] == 'S'
                    && inputList[row+1][column+1] == 'S') {
                    totalXmas++;
                    continue;
                }
            }
        }

        println("Total XMAS: $totalXmas")
    }

}