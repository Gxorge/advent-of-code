package uk.hotten.adventofcode.aoc24

import uk.hotten.adventofcode.AOCDay
import kotlin.math.abs

class Day01: AOCDay(24, "day01.txt") {

    // i forget how to use kotlin every year

    var left = mutableListOf<Int>();
    var right = mutableListOf<Int>();
    init {
        for (line in getInput()) {
            val split = line.split("   ");
            val num1 = split[0].toInt();
            val num2 = split[1].toInt();

            left.add(num1);
            right.add(num2);
        }

        left.sort();
        right.sort();
    }

    override fun part1() {
        var total = 0;
        for (i in left.indices) {
            total += abs(left[i] - right[i]);
        }

        println("Total is $total");
    }

    override fun part2() {
        var total = 0;

        for (i in left) {
            var appearances = 0;
            for (r in right) {
                if (r == i) appearances++;
            }
            total += i * appearances;
        }

        println("Total sim scores is $total");
    }

}