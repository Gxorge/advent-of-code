package uk.hotten.adventofcode.aoc24

import uk.hotten.adventofcode.AOCDay

class Day02: AOCDay(24, "day02.txt") {

    override fun part1() {
        var totalSafe = 0;

        for (line in getInput()) {
            println("STARTING LINE $line")
            val split = line.split(" ");
            val data = mutableListOf<Int>();

            for (s in split) data.add(s.toInt());

            val isSafe = isReportSafe(data)
            if (isSafe.first) {
                totalSafe++;
                println("SAFE")
            }
        }

        println("Total safe reports is $totalSafe");
    }

    override fun part2() {
        var totalSafe = 0;

        for (line in getInput()) {
            println("STARTING LINE $line")

            val split = line.split(" ");
            val data = mutableListOf<Int>();

            for (s in split) data.add(s.toInt());

            var isSafe = isReportSafe(data)
            if (isSafe.first) {
                totalSafe++;
                println("SAFE");
                continue;
            }

            println("RE ATTEMPT")
            // remove fail loc

            val dataOGRemoved = mutableListOf<Int>();
            dataOGRemoved.addAll(data);
            dataOGRemoved.removeAt(isSafe.second);

            val dataNegRemoved = mutableListOf<Int>();
            dataNegRemoved.addAll(data);
            dataNegRemoved.removeAt(isSafe.second-1);

            isSafe = isReportSafe(dataOGRemoved);
            if (isSafe.first) {
                totalSafe++;
                println("SAFE ON REMOVAL");
            } else {
                println("FINAL RE ATTEMPT")
                isSafe = isReportSafe(dataNegRemoved);

                if (isSafe.first) {
                    totalSafe++;
                    println("SAFE ON REMOVAL RE ATTEMPT");
                }
            }
        }

        println("Total safe reports is $totalSafe");
    }

    fun isReportSafe(data: MutableList<Int>): Pair<Boolean, Int> {
        println(data.toString())
        val first  = data[0];
        val second = data[1];

        if (first == second) return Pair(false, 1);
        var increase = increaseOrNo(data);

        var fail = false;
        var failLoc = 0;
        for (d in data.indices) {
            if (d == data.size - 1) break;
            val diff = if (increase)
                data[d+1] - data[d];
            else
                data[d] - data[d+1];
            println("diff of $diff")
            if (diff > 3 || diff <= 0)  { return Pair(false, d+1) }
        }

        return Pair(!fail, failLoc);
    }

    fun increaseOrNo(data: MutableList<Int>): Boolean {
        var increases = 0;
        var decreases = 0;

        for (d in data.indices) {
            if (d == data.size - 1) break;

            val one = data[d];
            val two = data[d + 1];
            if (one < two) increases++;
            else if (one > two) decreases++;
        }

        return increases > decreases;
    }

}