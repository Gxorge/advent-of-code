package uk.hotten.adventofcode.aoc21

import uk.hotten.adventofcode.AOCDay

class Day3: AOCDay(21, "day03.txt") {

    val lines = mutableListOf<String>()

    init {
        for (line in getInput()) {
            lines.add(line)
        }
    }

    override fun part1() {
        var gammaBinary = ""
        var epsilonBinary = ""
        val times = lines[0].length

        for (i in 0 until times) {
            var zeros = 0
            var ones = 0

            for (line in lines) {
                if (line[i] == '0') zeros++
                else if (line[i] == '1') ones++
                else println("err")
            }

            if (zeros > ones)  {
                gammaBinary += "0"
                epsilonBinary += "1"
            } else if (zeros < ones) {
                gammaBinary += "1"
                epsilonBinary += "0"
            } else {
                println("HALP")
            }

            println("Nums were $zeros zeros and $ones ones. New gamma binary is $gammaBinary, epsilon binary is $epsilonBinary (${i+1}/$times)")
        }

        val gamma = Integer.parseInt(gammaBinary, 2)
        val epsilon = Integer.parseInt(epsilonBinary, 2)

        println("Gamma rate is $gamma and $epsilon. Multiplied is ${gamma * epsilon}")
    }

    override fun part2() {
        var oxygen = lines
        var co2 = lines
        val times = lines[0].length

        var oxNumber = -1
        var coNumber = -1

        // Oxygen
        for (i in 0 until times) {
            var zeros = 0
            var ones = 0

            for (line in oxygen) {
                if (line[i] == '0') zeros++
                else if (line[i] == '1') ones++
                else println("err")
            }

            var newOx = mutableListOf<String>()
            if (zeros > ones)  {
                for (line in oxygen)
                    if (line[i] == '0') newOx.add(line)
            } else if (zeros < ones) {
                for (line in oxygen)
                    if (line[i] == '1') newOx.add(line)
            } else {
                println("HALP")
                for (line in oxygen)
                    if (line[i] == '1') newOx.add(line)
            }
            oxygen = newOx;

            println("oxygen size is now ${oxygen.size} from $zeros zeros and $ones ones")
            for (line in oxygen)
                println(line)

            if (oxygen.size == 1)
                break;
        }

        oxNumber = Integer.parseInt(oxygen[0], 2)
        println("Oxygen is $oxNumber")

        // CO2
        for (i in 0 until times) {
            var zeros = 0
            var ones = 0

            for (line in co2) {
                if (line[i] == '0') zeros++
                else if (line[i] == '1') ones++
                else println("err")
            }

            var newCo = mutableListOf<String>()
            if (zeros > ones)  {
                for (line in co2)
                    if (line[i] == '1') newCo.add(line)
            } else if (zeros < ones) {
                for (line in co2)
                    if (line[i] == '0') newCo.add(line)
            } else {
                println("HALP")
                for (line in co2)
                    if (line[i] == '0') newCo.add(line)
            }
            co2 = newCo;

            if (co2.size == 1)
                break;
        }

        coNumber = Integer.parseInt(co2[0], 2)
        println("CO2 is $coNumber")

        println("Multiplied is ${oxNumber * coNumber}")
    }

}