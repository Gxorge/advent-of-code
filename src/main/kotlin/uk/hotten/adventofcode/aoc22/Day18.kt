package uk.hotten.adventofcode.aoc22

import uk.hotten.adventofcode.AOCDay

class Day18: AOCDay(22, "day18.txt") {

    data class Cube(val x: Int, val y: Int, val z: Int) {

        fun getNeighbours(): List<Cube> {
            return listOf(
                Cube(x+1, y, z), Cube(x-1, y, z),
                Cube(x, y+1, z), Cube(x, y-1, z),
                Cube(x, y, z+1), Cube(x, y, z-1)
            )
        }
    }

    val cubes = mutableListOf<Cube>()

    init {
        for (line in getInput()) {
            val lineSplit = line.split(",")
            val x = lineSplit[0].toInt()
            val y = lineSplit[1].toInt()
            val z = lineSplit[2].toInt()
            cubes.add(Cube(x,y,z))
        }
    }

    override fun part1() {
        var surfaceArea: Long = 0
        for (cubeNum in 0 until cubes.size) {
            var facesOpen = 6
            val cube = cubes[cubeNum]
            println("==== $cube")
            for (compareNum in 0 until cubes.size) {
                val compareCube = cubes[compareNum]
                if (cubeNum == compareNum)
                    continue

                if ((compareCube.x + 1 == cube.x || compareCube.x - 1 == cube.x) && compareCube.y == cube.y && compareCube.z == cube.z) {
                    facesOpen--
                }

                if ((compareCube.y + 1 == cube.y || compareCube.y - 1 == cube.y) && compareCube.x == cube.x && compareCube.z == cube.z) {
                    facesOpen--
                }

                if ((compareCube.z + 1 == cube.z || compareCube.z - 1 == cube.z) && compareCube.x == cube.x && compareCube.y == cube.y) {
                    facesOpen--
                }

            }
            if (facesOpen < 1)
                continue
            surfaceArea += facesOpen
        }

        println("The total surface area is $surfaceArea")
    }

    override fun part2() {
        // create a slightly larger grid
        val minX = cubes.minOf {it.x} - 1
        val minY = cubes.minOf {it.y} - 1
        val minZ = cubes.minOf {it.z} - 1

        val maxX = cubes.maxOf { it.x } + 1
        val maxY = cubes.maxOf { it.y } + 1
        val maxZ = cubes.maxOf { it.z } + 1

        val grid = mutableSetOf<Cube>()
        val queue = mutableListOf(Cube(minX, minY, minZ))
        while (queue.size > 0) {
            val cube = queue.removeLast()
            if (cube in cubes) // not a way we can go!
                continue

            if (cube.x !in minX..maxX ||
                    cube.y !in minY .. maxY ||
                    cube.z !in minZ .. maxZ) continue

            // It is not already in the set
            if (grid.add(cube)) queue.addAll(cube.getNeighbours())
        }
        
        println(cubes.sumOf { cube -> cube.getNeighbours().filter { it in grid }.size })
    }

}