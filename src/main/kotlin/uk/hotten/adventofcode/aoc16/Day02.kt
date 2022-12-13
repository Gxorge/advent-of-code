package uk.hotten.adventofcode.aoc16

import uk.hotten.adventofcode.AOCDay

class Day02: AOCDay(16, "day02.txt") {

    val top = listOf<Int>(1, 2, 3)
    val middle = listOf<Int>(4, 5, 6)
    val bottom = listOf<Int>(7, 8, 9)

    override fun part1() {
        var row = 2 // 123, 456, etc
        var column = 2 // not zero index

        val code = mutableListOf<Int>()
        for (line in getInput()) {
            for (char in line) {
                if (char == 'U' && row != 1)
                    row--
                else if (char == 'D' && row != 3)
                    row++
                else if (char == 'R' && column != 3)
                    column++
                else if (char == 'L' && column != 1)
                    column--
            }

            val codeNum = getListP1(row)[column-1]
            code.add(codeNum)
            println("One digit is $codeNum")
        }

        println("The code is $code")
    }

    val lineOne = listOf<Char>(' ', ' ', '1', ' ', ' ')
    val lineTwo = listOf<Char>(' ', '2', '3', '4', ' ')
    val lineThree = listOf<Char>('5', '6', '7', '8', '9')
    val lineFour = listOf<Char>(' ', 'A', 'B', 'C', ' ')
    val lineFive = listOf<Char>(' ', ' ', 'D', ' ', ' ')

    override fun part2() {
        var row = 3 // 123, 456, etc
        var column = 0

        val code = mutableListOf<Char>()
        for (line in getInput()) {
            for (char in line) {
                println("$row and $column and $char")
                if (char == 'U' && row != 1 && getListP2(row-1)[column] != ' ')
                    row--
                else if (char == 'D' && row != 5 && getListP2(row+1)[column] != ' ')
                    row++
                else if (char == 'R' && column != 4 && getListP2(row)[column+1] != ' ')
                    column++
                else if (char == 'L' && column != 0 && getListP2(row)[column-1] != ' ')
                    column--
            }

            val codeNum = getListP2(row)[column]
            code.add(codeNum)
            println("One digit is $codeNum")
        }

        println("The code is $code")
    }

    private fun getListP1(row: Int): List<Int> {
        when (row) {
            1 -> return top
            2 -> return middle
            3 -> return bottom
        }

        return emptyList()
    }

    private fun getListP2(row: Int): List<Char> {
        when (row) {
            1 -> return lineOne
            2 -> return lineTwo
            3 -> return lineThree
            4 -> return lineFour
            5 -> return lineFive
        }

        return emptyList()
    }

}