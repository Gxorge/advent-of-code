package uk.hotten.adventofcode.aoc22

import uk.hotten.adventofcode.AOCDay
import java.lang.Math.floor
import java.math.BigInteger

class Day25: AOCDay(22, "day25.txt") {

    override fun part1() {
        var total: BigInteger = "0".toBigInteger()
        for (line in getInput()) {
            var currentPower: BigInteger = "1".toBigInteger()
            for (i in line.length-1 downTo 0) {
                total += ("=-012".indexOf(line[i]) - 2).toBigInteger() * currentPower
                println(("=-012".indexOf(line[i]) - 2).toBigInteger() * currentPower)
                currentPower *= "5".toBigInteger()
            }

        }

        println("Decimal total is $total")

        var snafu = ""
        var newTot = total.toBigDecimal()
        while (total > "0".toBigInteger()) {
            val rem = total.mod("5".toBigInteger())
            total /= "5".toBigInteger()
            println("$rem $total")

            if (rem <= "2".toBigInteger()) {
                snafu = rem.toString() + snafu
            } else {
                snafu = "   =-"[rem.toInt()] + snafu
                total += "1".toBigInteger()
            }
        }

        println("SNAFU is $snafu")
    }

    override fun part2() {
        println("Start the blender!")
    }

}