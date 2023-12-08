package uk.hotten.adventofcode.aoc23

import uk.hotten.adventofcode.AOCDay

class Day08: AOCDay(23, "day08.txt") {
    override fun part1() {
        val instructions = mutableListOf<Char>()
        val nodes = mutableMapOf<String, Pair<String,String>>();

        getInput()[0].forEach { c -> instructions.add(c); }

        for (i in 2 until getInput().size) {
            val split = getInput()[i].split(" = ");
            val name = split[0];
            var left = "";
            var right = "";
            for (n in 1..3)
                left += split[1][n];
            for (n in 6..8)
                right += split[1][n];

            nodes[name] = Pair(left, right);
        }

        var current = "AAA";
        var steps = 0;
        while (current != "ZZZ") {
            for (ins in instructions) {
                val node = nodes[current]!!;
                current = if (ins == 'L') node.first else node.second;
                steps++;

                if (current == "ZZZ")
                    break;
            }
        }

        println("Found ZZZ in $steps steps")
    }

    override fun part2() {
        val instructions = mutableListOf<Char>()
        val nodes = mutableMapOf<String, Pair<String,String>>();
        val nodesEndingWithA = mutableListOf<String>();

        getInput()[0].forEach { c -> instructions.add(c); }

        for (i in 2 until getInput().size) {
            val split = getInput()[i].split(" = ");
            val name = split[0];

            if (name[2] == 'A') {
                nodesEndingWithA.add(name);
            }

            var left = "";
            var right = "";
            for (n in 1..3)
                left += split[1][n];
            for (n in 6..8)
                right += split[1][n];

            nodes[name] = Pair(left, right);
        }

        val firstZStep = mutableMapOf<String, Int>();
        for (aNode in nodesEndingWithA) {
            var current = aNode;
            var steps = 0;
            while (current[2] != 'Z') {
                for (ins in instructions) {
                    val node = nodes[current]!!;
                    current = if (ins == 'L') node.first else node.second;
                    steps++;

                    if (current[2] == 'Z')
                        break;
                }
            }

            firstZStep[aNode] = steps;
        }

        // LCM....

        println(firstZStep)
        var largest: Long = 0;
        var maxLCM: Long = 0;
        for (kv in firstZStep) {
            if (kv.value > largest)
                largest = kv.value.toLong();
            if (maxLCM == "0".toLong())
                maxLCM = kv.value.toLong();
            else
                maxLCM *= kv.value;
        }

        println(largest);
        println(maxLCM);

        var done = false;
        var lcm: Long = largest;
        while (!done) {
            var found = true;

            if (lcm >= maxLCM) {
                println("Reached max lcm of $maxLCM");
                return;
            }

            for (kv in firstZStep) {
                if (lcm % kv.value != "0".toLong()) {
                    found = false;
                    break;
                }
            }

            if (found)
                done = true;
            else
                lcm += largest;
        }

        println("All finish on a Z end at $lcm steps");
    }

}