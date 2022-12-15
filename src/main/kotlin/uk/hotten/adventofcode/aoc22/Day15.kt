package uk.hotten.adventofcode.aoc22

import uk.hotten.adventofcode.AOCDay
import kotlin.math.abs

class Day15: AOCDay(22, "day15.txt") {

    val sensorLocations = mutableListOf<Pair<Int, Int>>()
    val beaconLocations = mutableListOf<Pair<Int, Int>>()
    var pairLocations = mutableMapOf<Pair<Int, Int>, Pair<Int, Int>>()
    val grid = mutableMapOf<Pair<Int, Int>, Byte>()

    init {
        val coordRegex = Regex("x=(-?\\d+), y=(-?\\d+)")

        for (line in getInput()) {
            val lineSplit = line.split(": ")
            val sensor = lineSplit[0]
            val beacon = lineSplit[1]

            // Sensor
            val sensorMatch = coordRegex.find(sensor)!!.groupValues
            val sensorLoc = Pair(sensorMatch[1].toInt(), sensorMatch[2].toInt())
            sensorLocations.add(sensorLoc)

            // Beacon
            val beaconMatch = coordRegex.find(beacon)?.groupValues!!
            val beaconLoc = Pair(beaconMatch[1].toInt(), beaconMatch[2].toInt())
            beaconLocations.add(beaconLoc)


            pairLocations[sensorLoc] = beaconLoc
        }
    }

    override fun part1() {
        val findOnY = 2000000

        for (entry in pairLocations) {
            println("Checking $entry")
            val sensor = entry.key
            val beacon = entry.value

            val radius = manhattan(sensor, beacon)

            val topX = sensor.first - radius
            val bottomX = sensor.first + radius

            for (x in topX .. bottomX) {
                val distance = manhattan(sensor, Pair(x, findOnY))

                if (distance <= radius) {
                    grid[Pair(x,findOnY)] = 1
                }
            }
        }

        var xLocs = mutableListOf<Int>()
        for (entry in grid) {
            xLocs.add(entry.key.first)
        }

        xLocs = xLocs.sortedDescending() as MutableList<Int>

        var cannotBeBeacon = 0
        for (x in xLocs[xLocs.size-1] .. xLocs[0]) {
            val loc = Pair(x,findOnY)
            if (beaconLocations.contains(loc) || sensorLocations.contains(loc))
                continue

            if (grid.contains(loc) && grid[loc] == "1".toByte())
                cannotBeBeacon++
        }

        println("Amount that cannot be beacon on Y=${findOnY} is $cannotBeBeacon")
    }

    override fun part2() {
        var found = false

        for (entry in pairLocations) {

            val x = entry.key.first
            val y = entry.key.second
            val distance = manhattan(entry.key, entry.value) + 1

            for (i in 0 until distance) {
                found = p2Check(Pair(x+i, y-distance+i)) || p2Check(Pair(x+distance-i, y+i)) || p2Check(Pair(x-i, y+distance-i)) || p2Check(Pair(x-distance+i, y-i))
                if (found)
                    break
            }

            if (found)
                break
        }

    }

    val max = 4000000
    private fun p2Check(pos: Pair<Int, Int>): Boolean {
        val x = pos.first
        val y = pos.second

        if (x < 0 || y < 0) return false
        if (x > max || y > max) return false
        for (item in pairLocations) {
            val sensor = item.key
            val beacon = item.value
            val sensorBeaconDistance = manhattan(sensor, beacon)
            val distance = manhattan(sensor, pos)

            if (distance <= sensorBeaconDistance || manhattan(pos, beacon) == 0) return false
        }

        println("The tuning frequency is please do the math in python or something: ($x * 4000000) + $y")
        return true
    }

    private fun manhattan(pos1: Pair<Int, Int>, pos2: Pair<Int, Int>): Int {
        return (abs(pos1.first - pos2.first) + abs(pos1.second - pos2.second))
    }

    private fun visualise() {
        var xLocs = mutableListOf<Int>()
        var yLocs = mutableListOf<Int>()
        for (entry in grid) {
            xLocs.add(entry.key.first)
            yLocs.add(entry.key.second)
        }

        xLocs = xLocs.sortedDescending() as MutableList<Int>
        yLocs = yLocs.sortedDescending() as MutableList<Int>

        for (y in yLocs[yLocs.size-1] .. yLocs[0]) {
            for (x in xLocs[xLocs.size-1] .. xLocs[0]) {
                if (sensorLocations.contains(Pair(x,y)))
                    print("S")
                else if (beaconLocations.contains(Pair(x,y)))
                    print("B")
                else if (!grid.containsKey(Pair(x,y)))
                    print(".")
                else
                    print("#")
            }
            print("\n")
        }
    }

}