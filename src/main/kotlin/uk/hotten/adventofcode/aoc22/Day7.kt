package uk.hotten.adventofcode.aoc22

import uk.hotten.adventofcode.AOCDay

// dont read this code just dont do it i dont even know what the fuck this does
class Day7: AOCDay(22, "day7.txt") {

    override fun part1() {
        val dirs = mutableMapOf<String, Int>()

        var isInSub = false;
        var currentDir = "/"
        var recentCD = "/"
        var currentSize = 0;
        for (lineNo in 1 until getInput().size) {
            val line = getInput()[lineNo]
            println(line)
            if (line.startsWith('$')) {
                if (line.startsWith("$ cd ..")) { // Up dir
                    val split = currentDir.split('/')
                    currentDir = "/"
                    for (item in 1 until split.size-2)
                        currentDir += "${split[item]}/"

                    if (!dirs.containsKey(recentCD)) {
                        dirs[recentCD] = currentSize
                        println("commited dir size $dirs")
                    }
                    currentSize = 0

                    recentCD = currentDir
                    println("cd uped to $currentDir")
                } else if (!line.startsWith("$ ls")) { // should be into a dir
                    currentDir += "${line.split(' ')[2]}/"
                    if (!dirs.containsKey(recentCD)) {
                        dirs[recentCD] = currentSize
                        println("commited dir size $dirs")
                    }
                    currentSize = 0
                    recentCD = currentDir
                    println("now in $currentDir")
                }

                continue
            }

            val lineSplit = line.split(' ')
            if (!isInteger(lineSplit[0]))
                continue

            currentSize += lineSplit[0].toInt()
            println("current size is now $currentSize")

        }

        if (currentSize != 0) {
            if (!dirs.containsKey(recentCD)) {
                dirs[recentCD] = currentSize
                println("!commited dir size $dirs")
            }
            currentSize = 0
        }

        println("procesing")
        // post processor the upper dirs
        val sortedDirs = mutableMapOf<String, Int>()
        for (entry in dirs) {
            val key = entry.key
            var totalVal = entry.value;
            for (entryCheck in dirs) {
                val checkingKey = entryCheck.key
                if (key == checkingKey)
                    continue
                if (checkingKey.startsWith(key))
                    totalVal += entryCheck.value
            }

            sortedDirs[key] = totalVal
        }


        println(sortedDirs)

        // remove all over 100,000
        var sizesToWorkWith = mutableListOf<Int>()
        var num = 0
        for (entry in sortedDirs) {
            val size = entry.value
            if (size >= 100000)
                continue

            sizesToWorkWith.add(size)
            num += size
        }
        println("Answer is $num")



        sizesToWorkWith = sizesToWorkWith.sortedDescending() as MutableList<Int>
        println("sizes to work with: $sizesToWorkWith")

    }

    override fun part2() {
        val dirs = mutableMapOf<String, Int>()

        var isInSub = false;
        var currentDir = "/"
        var recentCD = "/"
        var currentSize = 0;
        for (lineNo in 1 until getInput().size) {
            val line = getInput()[lineNo]
            println(line)
            if (line.startsWith('$')) {
                if (line.startsWith("$ cd ..")) { // Up dir
                    val split = currentDir.split('/')
                    currentDir = "/"
                    for (item in 1 until split.size-2)
                        currentDir += "${split[item]}/"

                    if (!dirs.containsKey(recentCD)) {
                        dirs[recentCD] = currentSize
                        println("commited dir size $dirs")
                    }
                    currentSize = 0

                    recentCD = currentDir
                    println("cd uped to $currentDir")
                } else if (!line.startsWith("$ ls")) { // should be into a dir
                    currentDir += "${line.split(' ')[2]}/"
                    if (!dirs.containsKey(recentCD)) {
                        dirs[recentCD] = currentSize
                        println("commited dir size $dirs")
                    }
                    currentSize = 0
                    recentCD = currentDir
                    println("now in $currentDir")
                }

                continue
            }

            val lineSplit = line.split(' ')
            if (!isInteger(lineSplit[0]))
                continue

            currentSize += lineSplit[0].toInt()
            println("current size is now $currentSize")

        }

        if (currentSize != 0) {
            if (!dirs.containsKey(recentCD)) {
                dirs[recentCD] = currentSize
                println("!commited dir size $dirs")
            }
            currentSize = 0
        }

        println("procesing")
        // post processor the upper dirs
        val sortedDirs = mutableMapOf<String, Int>()
        for (entry in dirs) {
            val key = entry.key
            var totalVal = entry.value;
            for (entryCheck in dirs) {
                val checkingKey = entryCheck.key
                if (key == checkingKey)
                    continue
                if (checkingKey.startsWith(key))
                    totalVal += entryCheck.value
            }

            sortedDirs[key] = totalVal
        }


        println(sortedDirs)

        val totalSpace = 70000000
        val updateSpace = 30000000
        val totalUsed = sortedDirs["/"]!!
        val spaceFree = totalSpace - totalUsed
        val amountNeeded = updateSpace - spaceFree
        println("amount to free $amountNeeded")

        val smallestToDelete = mutableListOf<Int>()
        var processedToUse = mutableListOf<Int>()
        for (entry in sortedDirs) {
            if (entry.value >= amountNeeded)
                processedToUse.add(entry.value)
        }
        processedToUse = processedToUse.sortedDescending() as MutableList<Int>

        println(processedToUse)


    }

    fun isInteger(str: String?) = str?.toIntOrNull()?.let { true } ?: false

    fun countOccurrences(s: String, ch: Char): Int {
        return s.filter { it == ch }.count()
    }
}