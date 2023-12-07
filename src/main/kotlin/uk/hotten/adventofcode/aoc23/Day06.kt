package uk.hotten.adventofcode.aoc23

import uk.hotten.adventofcode.AOCDay

class Day06: AOCDay(23, "day06.txt") {
    override fun part1() {
        val timeAndDistance = mutableListOf<Pair<Int, Int>>();
        val time = mutableListOf<Int>();

        var timeString = "";
        for (char in getInput()[0]) {
            if (isInteger(char)) {
                timeString += char;
            } else {
                if (timeString != "") {
                    time.add(timeString.toInt());
                    timeString = "";
                }
            }
        }
        if (timeString != "") {
            time.add(timeString.toInt());
        }

        var distanceString = "";
        var current = 0;
        for (char in getInput()[1]) {
            if (isInteger(char)) {
                distanceString += char;
            } else {
                if (distanceString != "") {
                    timeAndDistance.add(Pair(time[current], distanceString.toInt()));
                    current++;
                    distanceString = "";
                }
            }
        }
        if (distanceString != "") {
            timeAndDistance.add(Pair(time[current], distanceString.toInt()));
        }


        var multiple = 1;
        for (race in timeAndDistance) {
            val time = race.first;
            val distance = race.second;

            var timesToWin = 0;
            for (i in 0..time) {
                val timeToMove = time-i;
                val move = i*timeToMove;

                if (move > distance)
                    timesToWin++;
            }
            if (timesToWin != 0)
                multiple *= timesToWin;
        }

        println("Multiple of all times to win is $multiple");
    }

    override fun part2() {
        var time: Long = 0;
        var distance: Long = 0;

        var timeString = "";
        for (char in getInput()[0]) {
            if (isInteger(char)) {
                timeString += char;
            }
        }
        time = timeString.toLong();

        var distanceString = "";
        for (char in getInput()[1]) {
            if (isInteger(char)) {
                distanceString += char;
            }
        }

        distance = distanceString.toLong();

        var timesToWin = 0;
        for (i in 0..time) {
            val timeToMove = time-i;
            val move = i*timeToMove;

            if (move > distance)
                timesToWin++;
        }

        println("Amount of times to win is $timesToWin");
    }

}