package uk.hotten.adventofcode.aoc23

import uk.hotten.adventofcode.AOCDay

class Day02: AOCDay(23, "day02.txt") {
    override fun part1() {
        val maxRed = 12;
        val maxGreen = 13;
        val maxBlue = 14;

        var possibleCount = 0;
        var impossibleCount = 0;

        for (line in getInput()) {
            var game = Integer.parseInt(line.split(':')[0].split(' ')[1]);

            var lineNoGame = line.split(": ")[1];
            var subGames = lineNoGame.split("; ");

            var good = true;
            for (sub in subGames) {
                var dice = sub.split(", ");
                for (die in dice) {
                    var amt = Integer.parseInt(die.split(" ")[0]);
                    var colour = die.split(" ")[1];

                    if ((colour == "red" && amt > maxRed) ||
                        (colour.equals("green") && amt > maxGreen) ||
                        (colour == "blue" && amt > maxBlue)) {

                        good = false;
                        break;
                    }
                }
                if (!good)
                    break;
            }

            if (good)
                possibleCount += game;
            else
                impossibleCount += game;
        }

        println("Sum of possible games: $possibleCount");
        println("Sum of impossible games: $impossibleCount");
    }

    override fun part2() {
        var powerSums = 0;

        for (line in getInput()) {
            var game = Integer.parseInt(line.split(':')[0].split(' ')[1]);

            var lineNoGame = line.split(": ")[1];
            var subGames = lineNoGame.split("; ");

            var minRed = 0;
            var minGreen = 0;
            var minBlue = 0;

            for (sub in subGames) {
                var dice = sub.split(", ");
                for (die in dice) {
                    var amt = Integer.parseInt(die.split(" ")[0]);
                    var colour = die.split(" ")[1];

                    if (colour == "red" && amt > minRed)
                        minRed = amt;
                    else if (colour == "green" && amt > minGreen)
                        minGreen = amt;
                    else if (colour == "blue" && amt > minBlue)
                        minBlue = amt;
                }
            }

            powerSums += (minRed * minGreen * minBlue);
        }
        println("Sum of all minimum powers: $powerSums");
    }
}