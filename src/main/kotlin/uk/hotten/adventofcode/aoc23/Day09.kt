package uk.hotten.adventofcode.aoc23

import uk.hotten.adventofcode.AOCDay

class Day09: AOCDay(23, "day09.txt") {

    override fun part1() {
        var extrapolatedSum = 0;
        for (line in getInput()) {
            val nums = mutableListOf<Int>();
            line.split(" ").forEach { n -> nums.add(n.toInt()) }

            extrapolatedSum += sequence(mutableListOf(nums));
        }

        println("Sum of all extrapolated values is $extrapolatedSum");
    }

    private fun sequence(seqs: MutableList<MutableList<Int>>): Int {
        val seq = mutableListOf<Int>();
        val nums = seqs[seqs.size-1];
        for (i in 0 until nums.size-1) {
            val curr = nums[i];
            val next = nums[i+1];

            seq.add(next - curr);
        }
        seqs.add(seq);
        //println(seq);

        var allZero = true;
        for (s in seq)
            if (s != 0)
                allZero = false;

        if (allZero) {
            //println("---")
            for (i in seqs.size-1 downTo 1) {
                val curr = seqs[i][seqs[i].size-1];
                val last = seqs[i-1][seqs[i-1].size-1];

                seqs[i-1].add(curr + last);
                //println(seqs[i-1]);
            }

            return seqs[0][seqs[0].size-1];
        } else {
            return sequence(seqs);
        }
    }

    override fun part2() {
        var extrapolatedSum = 0;
        for (line in getInput()) {
            val nums = mutableListOf<Int>();
            line.split(" ").forEach { n -> nums.add(n.toInt()) }

            extrapolatedSum += sequenceP2(mutableListOf(nums));
        }

        println("Sum of all extrapolated values is $extrapolatedSum");
    }

    private fun sequenceP2(seqs: MutableList<MutableList<Int>>): Int {
        val seq = mutableListOf<Int>();
        val nums = seqs[seqs.size-1];
        for (i in 0 until nums.size-1) {
            val curr = nums[i];
            val next = nums[i+1];

            seq.add(next - curr);
        }
        seqs.add(seq);
        //println(seq);

        var allZero = true;
        for (s in seq)
            if (s != 0)
                allZero = false;

        if (allZero) {
            //println("---")
            for (i in seqs.size-1 downTo 1) {
                val curr = seqs[i][0];
                val last = seqs[i-1][0];

                seqs[i-1].add(0, last - curr);
                //println(seqs[i-1]);
            }

            return seqs[0][0];
        } else {
            return sequenceP2(seqs);
        }
    }

}