package uk.hotten.adventofcode.aoc16

import uk.hotten.adventofcode.AOCDay

class Day5: AOCDay(16, "day05.txt") {

    override fun part1() {
        var password = ""
        var found = false
        var currentNumber = 0
        val secret = getInput()[0]
        var hashed = ""
        while (!found) {
            hashed = "$secret$currentNumber".md5()
            var allZero = 0
            for (i in 0..4) {
                if (hashed[i] == '0')
                    allZero++
            }

            if (allZero == 5) {
                password += hashed[5]
                println("Digit found: ${hashed[5]}")

                found = password.length == 8
            }

            currentNumber++
        }

        println("The password is $password")
    }

    override fun part2() {
        var password = mutableListOf<Char>(' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ')
        var found = false
        var currentNumber = 0
        val secret = getInput()[0]
        var hashed = ""
        while (!found) {
            hashed = "$secret$currentNumber".md5()
            var allZero = 0
            for (i in 0..4) {
                if (hashed[i] == '0')
                    allZero++
            }

            if (allZero == 5) {
                if (!isInteger(hashed[5].toString())) {
                    println("bad position, not int: ${hashed[5]}")
                    currentNumber++
                    continue
                }

                val position = hashed[5].digitToInt()
                if (position > 7) {
                    println("bad position, over 7: ${hashed[5]}")
                    currentNumber++
                    continue
                }

                if (password[position] != ' ') {
                    println("bad position, already taken: ${hashed[5]}")
                    currentNumber++
                    continue
                }


                password[position] = hashed[6]
                println(password)

                var stillBlank = 0;
                for (digit in password) {
                    if (digit == ' ')
                        stillBlank++
                }

                found = stillBlank == 0
            }

            currentNumber++
        }

        println("The password is ${password.joinToString(separator = "")}")
    }

}