package uk.hotten.adventofcode.aoc23

import uk.hotten.adventofcode.AOCDay
import java.lang.Exception

class Day05: AOCDay(23, "day05.txt") {

    val seeds = mutableListOf<Long>();
    val seedsP2 = mutableListOf<Pair<Long, Long>>();
    val seedToSoil = mutableMapOf<Pair<Long, Long>, Pair<Long, Long>>();
    val soilToFertilizer = mutableMapOf<Pair<Long, Long>, Pair<Long, Long>>();
    val fertilizerToWater = mutableMapOf<Pair<Long, Long>, Pair<Long, Long>>();
    val waterToLight = mutableMapOf<Pair<Long, Long>, Pair<Long, Long>>();
    val lightToTemperature = mutableMapOf<Pair<Long, Long>, Pair<Long, Long>>();
    val temperatureToHumidity = mutableMapOf<Pair<Long, Long>, Pair<Long, Long>>();
    val humidityToLocation = mutableMapOf<Pair<Long, Long>, Pair<Long, Long>>();

    override fun part1() {
        var map = "";

        for (line in getInput()) {
            if (line.startsWith("seeds:") && seeds.isEmpty()) {
                for (num in line.split(": ")[1].split(" ")) {
                    seeds.add(num.toLong());
                }
                continue;
            }

            if (line == "")
                continue;

            if (line.contains("map")) {
                map = line;
                continue;
            }

            val splits = line.split(" ");
            val destinationStart = splits[0].toLong();
            val sourceStart = splits[1].toLong();
            val range = splits[2].toLong();

            val currentMap = getMap(map);
            var lastSource: Long = sourceStart + range-1;
            var lastDestination: Long = destinationStart + range-1;

            currentMap[Pair(sourceStart, destinationStart)] = (Pair(lastSource, lastDestination));
        }

        var lowestSeed: Long = 0;
        var lowestLoc: Long = Long.MAX_VALUE;
        for (seed in seeds) {
            val loc = getFromMap(humidityToLocation, getFromMap(temperatureToHumidity, getFromMap(lightToTemperature, getFromMap(waterToLight, getFromMap(fertilizerToWater, getFromMap(soilToFertilizer, getFromMap(seedToSoil, seed)))))));
            if (loc < lowestLoc) {
                lowestLoc = loc;
                lowestSeed = seed;
            }
        }

        println("Lowest location is $lowestLoc on seed $lowestSeed");
    }

    override fun part2() {
        var map = "";

        for (line in getInput()) {
            var first: Long = 0;
            if (line.startsWith("seeds:") && seedsP2.isEmpty()) {
                for (num in line.split(": ")[1].split(" ")) {
                    if (first == "0".toLong())
                        first = num.toLong();
                    else {
                        seedsP2.add(Pair(first, num.toLong()));
                        first = 0;
                    }
                }
                continue;
            }

            if (line == "")
                continue;

            if (line.contains("map")) {
                map = line;
                continue;
            }

            val splits = line.split(" ");
            val destinationStart = splits[0].toLong();
            val sourceStart = splits[1].toLong();
            val range = splits[2].toLong();

            val currentMap = getMap(map);
            var lastSource: Long = sourceStart + range-1;
            var lastDestination: Long = destinationStart + range-1;

            currentMap[Pair(sourceStart, destinationStart)] = (Pair(lastSource, lastDestination));
        }

        for (i in 0 .. Long.MAX_VALUE) {
            val seed = getFromMapReversed(seedToSoil, getFromMapReversed(soilToFertilizer, getFromMapReversed(fertilizerToWater, getFromMapReversed(waterToLight, getFromMapReversed(lightToTemperature, getFromMapReversed(temperatureToHumidity, getFromMapReversed(humidityToLocation, i)))))));
            if (isSeed(seed)) {
                println("Lowest location is $i on seed $seed");
                return;
            }
        }

        println("Not found.");
    }

    private fun getMap(map: String): MutableMap<Pair<Long, Long>, Pair<Long, Long>> {
        if (map.startsWith("seed-to-soil"))
            return seedToSoil;
        else if (map.startsWith("soil-to-fertilizer"))
            return soilToFertilizer;
        else if (map.startsWith("fertilizer-to-water"))
            return fertilizerToWater;
        else if (map.startsWith("water-to-light"))
            return waterToLight;
        else if (map.startsWith("light-to-temperature"))
            return lightToTemperature;
        else if (map.startsWith("temperature-to-humidity"))
            return temperatureToHumidity;
        else if (map.startsWith("humidity-to-location"))
            return humidityToLocation;
        else
            throw Exception("Map not found: $map");
    }

    private fun getFromMap(map: MutableMap<Pair<Long, Long>, Pair<Long, Long>>, num: Long): Long {
        for (key in map.keys) {
            val last = map[key]!!;
            val range = key.first .. last.first;
            if (range.contains(num)) {
                return key.second + (num - key.first);
            }
        }

        return num;
    }

    private fun getFromMapReversed(map: MutableMap<Pair<Long, Long>, Pair<Long, Long>>, num: Long): Long {
        for (key in map.keys) {
            val last = map[key]!!;
            val range = key.second .. last.second;
            if (range.contains(num)) {
                return key.first + (num - key.second);
            }
        }

        return num;
    }

    private fun isSeed(num: Long): Boolean {
        for (seed in seedsP2) {
            val range = seed.first .. (seed.first + seed.second);
            if (range.contains(num)) {
                return true;
            }
        }

        return false;
    }

}