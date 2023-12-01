package uk.hotten.adventofcode.aoc23

import uk.hotten.adventofcode.AOCDay

class Day01: AOCDay(23, "day01.txt") {

    // Find first and last digit in line and combine to make number.
    override fun part1() {
        val numbers = mutableListOf<Int>();

        for (line in getInput()) {
            var ints = "";
            for (char in line) {
                if (isInteger(char)) {
                    ints += char;
                }
            }

            var newInts = ints[0] + "" + ints[ints.length-1];
            numbers.add(Integer.parseInt(newInts));
            println(Integer.parseInt(newInts));
        }

        var count = 0;
        for (num in numbers) {
            count += num;
        }

        println("Sum of all first and list digits is $count")
    }

    // The same as part 1, however now "one", "two", "three", etc is counted
    override fun part2() {
        val numbers = mutableListOf<Int>();

        for (line in getInput()) {
            println("New line: " + line);
            val ints = mutableMapOf<Int, Int>();

            val stringDigits = findAllStringDigits(line);
            for (out in stringDigits) {
                println("(D) ${out.first} is at ${out.second}");
                ints.put(out.second, digitToNumber(out.first));
            }

            for (i in line.indices) {
                val char = line[i];
                if (isInteger(char)) {
                    ints.put(i, Integer.parseInt(char.toString()));
                    println("(N) $char is at $i")
                }
            }

            val lowest = getLowestOrHighest(ints, true);
            val highest = getLowestOrHighest(ints, false);
            val newInts = "$lowest$highest";
            numbers.add(Integer.parseInt(newInts));
            println(Integer.parseInt(newInts));
        }

        var count = 0;
        for (num in numbers) {
            count += num;
        }

        println("Sum of all first and list digits is $count")
    }

    private fun findAllStringDigits(line: String): MutableList<Pair<String, Int>> {
        val digits = mutableListOf<String>("one", "two", "three", "four", "five", "six", "seven", "eight", "nine");

        val toReturn = mutableListOf<Pair<String, Int>>();
        for (dig in digits) {
            val instances = line.lowercase().split(dig);


            if (instances.size == 2) {
                val index = line.indexOf(dig, ignoreCase = true);
                toReturn.add(Pair(dig, index));
            } else if (instances.size > 2) {
                var offset = 0;
                for (i in instances.indices) {
                    val index = line.indexOf(dig, offset, ignoreCase = true);
                    if (index == -1)
                        continue;
                    toReturn.add(Pair(dig, index));
                    offset += (index + dig.length);
                }
            }
        }

        return toReturn;
    }

    private fun digitToNumber(digit: String): Int {
        return when (digit) {
            "one" -> 1;
            "two" -> 2;
            "three" -> 3;
            "four" -> 4;
            "five" -> 5;
            "six" -> 6;
            "seven" -> 7;
            "eight" -> 8;
            "nine" -> 9;
            else -> 0;
        }
    }

    private fun getLowestOrHighest(list: MutableMap<Int, Int>, lowest: Boolean): Int {
        println(list);
        if (lowest) {
            var current = Integer.MAX_VALUE;
            var value = 0;
            for (entry in list) {
                if (entry.key < current) {
                    current = entry.key;
                    value = entry.value;
                }
            }

            return value;
        }

        var current = Integer.MIN_VALUE;
        var value = 0;
        for (entry in list) {
            if (entry.key > current) {
                current = entry.key;
                value = entry.value;
            }
        }

        return value;
    }

}