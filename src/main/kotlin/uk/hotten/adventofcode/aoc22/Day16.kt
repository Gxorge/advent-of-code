package uk.hotten.adventofcode.aoc22

import uk.hotten.adventofcode.AOCDay

class Day16: AOCDay(22, "day16.txt") {

    val valves = mutableMapOf<String, Int>()
    val tunnels = mutableMapOf<String, List<String>>()

    init {
        val tunnelRegex = "[A-Z]{2}".toRegex()
        for (line in getInput()) {
            val lineSplit = line.split("; ")
            val valveSplit = lineSplit[0]
            val tunnelSplit = lineSplit[1]

            val valve = line.split(" ")[1]

            // flow rate
            val flowRate = valveSplit.split("=")[1].toInt()

            // tunnel
            val matches = tunnelRegex.findAll(tunnelSplit)
            val connections = matches.map { it.value }

            valves[valve] = flowRate
            tunnels[valve] = connections.toList()
        }
    }

    override fun part1() {
        val dists = mutableMapOf<String, MutableMap<String, Int>>()
        val nonEmpty = mutableListOf<String>()

        for (valve in valves) {
            if (valve.key != "AA" && valves[valve.key] == 0)
                continue

            if (valve.key != "AA")
                nonEmpty.add(valve.key)

            dists[valve.key] = mutableMapOf(valve.key to 0, "AA" to 0)
            val visted = mutableSetOf(valve.key)

            val queue = mutableListOf(Pair(0, valve.key))
            while (!queue.isEmpty()) {
                val pair = queue[0]
                queue.removeAt(0)
                val distance = pair.first
                val position = pair.second

                for (neighbor in tunnels[position]!!) {
                    if (neighbor in visted) continue

                    visted.add(neighbor)
                    if (valves[neighbor] != 0)
                        dists[valve.key]?.set(neighbor, distance + 1)
                    queue.add(Pair(distance + 1, neighbor))
                }
            }

            dists[valve.key]?.remove(valve.key)
            if (valve.key != "AA")
                dists[valve.key]?.remove("AA")
        }

        val indicies = mutableMapOf<String, Int>()
        nonEmpty.forEachIndexed { index, element ->
            indicies[element] = index
        }

        val cache = mutableMapOf<Triple<Int, String, Int>, Int>()

        fun dfs(time: Int, valve: String, bitmask: Int): Int {
            if (Triple(time, valve, bitmask) in cache) {
                return cache[Triple(time, valve, bitmask)]!!
            }

            var maxVal = 0

            for (neighbor in dists[valve]!!) {
                val bit = 1 shl indicies[neighbor.key]!!
                if (bitmask and bit != 0)
                    continue
                var timeRemaining = time - dists[valve]!![neighbor.key]!! - 1
                if (timeRemaining <= 0)
                    continue

                maxVal = Math.max(maxVal, dfs(timeRemaining, neighbor.key, bitmask or bit) + valves[neighbor.key]!! * timeRemaining)
            }

            cache[Triple(time, valve, bitmask)] = maxVal
            return maxVal
        }

        println(dfs(30, "AA", 0))
    }

    override fun part2() {
        val dists = mutableMapOf<String, MutableMap<String, Int>>()
        val nonEmpty = mutableListOf<String>()

        for (valve in valves) {
            if (valve.key != "AA" && valves[valve.key] == 0)
                continue

            if (valve.key != "AA")
                nonEmpty.add(valve.key)

            dists[valve.key] = mutableMapOf(valve.key to 0, "AA" to 0)
            val visted = mutableSetOf(valve.key)

            val queue = mutableListOf(Pair(0, valve.key))
            while (!queue.isEmpty()) {
                val pair = queue[0]
                queue.removeAt(0)
                val distance = pair.first
                val position = pair.second

                for (neighbor in tunnels[position]!!) {
                    if (neighbor in visted) continue

                    visted.add(neighbor)
                    if (valves[neighbor] != 0)
                        dists[valve.key]?.set(neighbor, distance + 1)
                    queue.add(Pair(distance + 1, neighbor))
                }
            }

            dists[valve.key]?.remove(valve.key)
            if (valve.key != "AA")
                dists[valve.key]?.remove("AA")
        }

        val indicies = mutableMapOf<String, Int>()
        nonEmpty.forEachIndexed { index, element ->
            indicies[element] = index
        }

        val cache = mutableMapOf<Triple<Int, String, Int>, Int>()

        fun dfs(time: Int, valve: String, bitmask: Int): Int {
            if (Triple(time, valve, bitmask) in cache) {
                return cache[Triple(time, valve, bitmask)]!!
            }

            var maxVal = 0

            for (neighbor in dists[valve]!!) {
                val bit = 1 shl indicies[neighbor.key]!!
                if (bitmask and bit != 0)
                    continue
                var timeRemaining = time - dists[valve]!![neighbor.key]!! - 1
                if (timeRemaining <= 0)
                    continue

                maxVal = Math.max(maxVal, dfs(timeRemaining, neighbor.key, bitmask or bit) + valves[neighbor.key]!! * timeRemaining)
            }

            cache[Triple(time, valve, bitmask)] = maxVal
            return maxVal
        }

        val b = (1 shl nonEmpty.size) - 1
        var max = 0

        for (i in 0 .. (b.div(2)))
            max = Math.max(max, dfs(26, "AA", i) + dfs(26, "AA", b xor i))

        print(max)
    }

}