package uk.hotten.adventofcode.aoc24

import uk.hotten.adventofcode.AOCDay

class Day03: AOCDay(24, "day03.txt") {

    override fun part1() {
        var total = 0;

        for (line in getInput()) {
            val regex = "mul\\(\\d{1,3},\\d{1,3}\\)".toRegex();
            val matches = regex.findAll(line).map { it.value }.toList();

            for (m in matches) {
                val split = m.split(",");
                var first = split[0].replace("[^0-9]".toRegex(), "")
                val second = split[1].replace("[^0-9]".toRegex(), "")

                total += first.toInt() * second.toInt();
            }
        }

        println("Total: $total")
    }

    override fun part2() {
        var total = 0;

        var enabled = true;
        for (line in getInput()) {
            val regex = "(do\\(\\)|don't\\(\\)|mul\\(\\d{1,3},\\d{1,3}\\))".toRegex();
            val matches = regex.findAll(line).map { it.value }.toList();

            for (m in matches) {
                if (m == "do()") {
                    enabled = true;
                    continue;
                } else if (m == "don't()") {
                    enabled = false;
                    continue;
                }

                if (!enabled)
                    continue;
                val split = m.split(",");
                var first = split[0].replace("[^0-9]".toRegex(), "")
                val second = split[1].replace("[^0-9]".toRegex(), "")

                total += first.toInt() * second.toInt();
            }
        }

        println("Total: $total")
    }

}