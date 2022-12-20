package uk.hotten.adventofcode.aoc22

import uk.hotten.adventofcode.AOCDay

class Day20: AOCDay(22, "day20.txt") {

    val initialList = mutableListOf<Node>()

    data class Node(var n: Long, var left: Node?, var right: Node?)

    override fun part1() {
        getInput().forEach { initialList.add(Node(it.toLong(), null, null)) }

        for (i in 0 until initialList.size) {
            initialList[i].right = initialList[(i + 1).mod(initialList.size)]
            initialList[i].left = initialList[(i - 1).mod(initialList.size)]
        }

        var zeroNode: Node = Node(-1, null, null)
        val mod = initialList.size - 1

        for (current in initialList) {
            if (current.n == "0".toLong()) {
                zeroNode = current
            }

            var pc = current
            if (current.n > 0) {
                for (i in 0 until current.n % mod) {
                    pc = pc.right!!
                }

                if (current == pc)
                    continue

                current.right!!.left = current.left
                current.left!!.right = current.right
                pc.right!!.left = current
                current.right = pc.right
                pc.right = current
                current.left = pc
            } else {
                for (i in 0 until -current.n % mod) {
                    pc = pc.left!!
                }

                if (current == pc)
                    continue

                current.left!!.right = current.right
                current.right!!.left = current.left
                pc.left!!.right = current
                current.left = pc.left
                pc.left = current
                current.right = pc
            }
        }

        var t: Long = 0
        for (i in 0 until 3) {
            for (j in 0 until 1000) {
                zeroNode = zeroNode.right!!
            }
            println(zeroNode.n)
            t += zeroNode.n
        }

        println(t)
    }

    override fun part2() {
        getInput().forEach { initialList.add(Node(it.toLong() * 811589153, null, null)) }

        for (i in 0 until initialList.size) {
            initialList[i].right = initialList[(i + 1).mod(initialList.size)]
            initialList[i].left = initialList[(i - 1).mod(initialList.size)]
        }

        var zeroNode: Node = Node(-1, null, null)
        val mod = initialList.size - 1

        for (mrjames in 0 until 10) {
            for (current in initialList) {
                if (current.n == "0".toLong()) {
                    zeroNode = current
                }

                var pc = current
                if (current.n > 0) {
                    for (i in 0 until current.n % mod) {
                        pc = pc.right!!
                    }

                    if (current == pc)
                        continue

                    current.right!!.left = current.left
                    current.left!!.right = current.right
                    pc.right!!.left = current
                    current.right = pc.right
                    pc.right = current
                    current.left = pc
                } else {
                    for (i in 0 until -current.n % mod) {
                        pc = pc.left!!
                    }

                    if (current == pc)
                        continue

                    current.left!!.right = current.right
                    current.right!!.left = current.left
                    pc.left!!.right = current
                    current.left = pc.left
                    pc.left = current
                    current.right = pc
                }
            }
        }

        var t: Long = 0
        for (i in 0 until 3) {
            for (j in 0 until 1000) {
                zeroNode = zeroNode.right!!
            }
            println(zeroNode.n)
            t += zeroNode.n
        }

        println(t)
    }


}