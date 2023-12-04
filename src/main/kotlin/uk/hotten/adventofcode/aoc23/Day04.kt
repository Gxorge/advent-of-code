package uk.hotten.adventofcode.aoc23

import uk.hotten.adventofcode.AOCDay

class Day04: AOCDay(23, "day04.txt") {
    override fun part1() {
        var totalPoints = 0;
        for (line in getInput()) {
            var gamePoints = 0;
            val noGame = line.split(": ");
            val split = noGame[1].split(" | ");

            val winning = mutableListOf<Int>()
            println(split[0].split(" "))
            for (s in split[0].split(" ")) {
                if (s == "")
                    continue;
                winning.add(Integer.parseInt(s));
            }

            val actual = mutableListOf<Int>()
            println(split[1].split(" "))
            for (s in split[1].split(" ")) {
                if (s == "")
                    continue;
                actual.add(Integer.parseInt(s));
            }

            for (num in actual) {
                if (winning.contains(num)) {
                    gamePoints = if (gamePoints == 0) 1 else gamePoints * 2;
                }
            }

            println("Game has $gamePoints points")

            totalPoints += gamePoints;
        }

        println("Total points from all cards is $totalPoints");
    }

    override fun part2() {
        val ogCards = mutableMapOf<Int, Pair<MutableList<Int>, MutableList<Int>>>()
        for (line in getInput()) {
            val noGame = line.split(": ");
            var gameString = "";
            for (c in noGame[0]) {
                if (isInteger(c))
                    gameString += c;
            }

            val gameNumber = Integer.parseInt(gameString);

            val split = noGame[1].split(" | ");

            val winning = mutableListOf<Int>()
            for (s in split[0].split(" ")) {
                if (s == "")
                    continue;
                winning.add(Integer.parseInt(s));
            }

            val actual = mutableListOf<Int>()
            for (s in split[1].split(" ")) {
                if (s == "")
                    continue;
                actual.add(Integer.parseInt(s));
            }

            ogCards[gameNumber] = Pair(winning,actual);
        }

        val queue = mutableMapOf<Int, Int>();
        for (k in ogCards.keys)
            queue[k] = 1;
        var cards = queue.size;


        for (k in ogCards.keys) {
            for (i in 0 until queue[k]!!) {
                val winning = ogCards[k]!!.first;
                val actual = ogCards[k]!!.second;

                var matches = 0;
                for (num in actual) {
                    if (winning.contains(num)) {
                        matches++;
                    }
                }

                for (n in 1..matches) {
                    val current = queue[k+n];
                    queue[k+n] = current!! + 1;
                    cards++;
                }
            }
        }

        println("Total cards is $cards");
    }

}