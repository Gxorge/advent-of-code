package uk.hotten.adventofcode.aoc23

import uk.hotten.adventofcode.AOCDay

class Day07: AOCDay(23, "day07.txt") {
    override fun part1() {
        val rankings = mutableListOf<Pair<String, Int>>();
        for (line in getInput()) {
            val card = line.split(" ")[0];
            val bid = line.split(" ")[1].toInt();
            if (rankings.isEmpty()) {
                rankings.add(Pair(card, bid));
                continue;
            }

            var cardInserted = false;
            for (i in rankings.indices) {
                val cardWins = fight(card, rankings[i].first);
                if (!cardWins) {
                    rankings.add(i, Pair(card, bid))
                    cardInserted = true;
                    break;
                }
            }

            if (!cardInserted)
                rankings.add(Pair(card, bid));
        }

        var totalWinnings = 0;
        for (i in rankings.indices) {
            val bid = rankings[i].second;
            totalWinnings += bid * (i+1);
        }

        println("The total winnings are $totalWinnings");
    }

    private fun fight(main: String, opponent: String): Boolean {

        val mainHand = calculateHandType(main);
        val opponentHand = calculateHandType(opponent);

        // Win by hand type only?
        if (mainHand > opponentHand)
            return true;
        else if (mainHand < opponentHand)
            return false;


        for (i in main.indices) {
            val mainCard = calculateCardStrength(main[i]);
            val opponentCard = calculateCardStrength(opponent[i]);
            if (mainCard > opponentCard)
                return true;
            else if (mainCard < opponentCard)
                return false;
        }

        return false;
    }

    private fun calculateHandType(hand: String): Int {
        val amounts = mutableMapOf<Char, Int>()
        for (card in hand) {
            if (!amounts.containsKey(card))
                amounts[card] = 1;
            else
                amounts[card] = amounts[card]!!+1;
        }

        // Check for five of a kind
        for (a in amounts) {
            if (a.value == 5)
                return 7;
        }

        // Check for four of a kind
        for (a in amounts) {
            if (a.value == 4)
                return 6;
        }

        // Check for full house
        var twoFound = false;
        var threeFound = false;
        for (a in amounts) {
            if (a.value == 2) {
                if (twoFound)
                    continue;
                if (threeFound)
                    return 5;
                twoFound = true;
            } else if (a.value == 3) {
                if (threeFound)
                    continue;
                if (twoFound)
                    return 5;
                threeFound = true;
            }
        }

        // Check for three of a kind
        for (a in amounts) {
            if (a.value == 3)
                return 4;
        }

        // Check for two pair
        var onePair = false;
        for (a in amounts) {
            if (a.value == 2)
                if (!onePair)
                    onePair = true;
                else
                    return 3;
        }

        // Check for one pair
        for (a in amounts) {
            if (a.value == 2)
                return 2;
        }

        // Has to be a high card
        return 1;
    }

    private fun calculateCardStrength(card: Char): Int {
        when (card) {
            'A' -> return 13;
            'K' -> return 12;
            'Q' -> return 11;
            'J' -> return 10;
            'T' -> return 9;
            '9' -> return 8;
            '8' -> return 7;
            '7' -> return 6;
            '6' -> return 5;
            '5' -> return 4;
            '4' -> return 3;
            '3' -> return 2;
            '2' -> return 1;
            else -> return 0;
        }
    }


    override fun part2() {
        val rankings = mutableListOf<Pair<String, Int>>();
        for (line in getInput()) {
            val card = line.split(" ")[0];
            val bid = line.split(" ")[1].toInt();
            if (rankings.isEmpty()) {
                rankings.add(Pair(card, bid));
                continue;
            }

            var cardInserted = false;
            for (i in rankings.indices) {
                val cardWins = fightP2(card, rankings[i].first);
                if (!cardWins) {
                    rankings.add(i, Pair(card, bid))
                    cardInserted = true;
                    break;
                }
            }

            if (!cardInserted)
                rankings.add(Pair(card, bid));
        }

        var totalWinnings = 0;
        for (i in rankings.indices) {
            val bid = rankings[i].second;
            totalWinnings += bid * (i+1);
        }

        println("The total winnings are $totalWinnings");
    }

    private fun fightP2(main: String, opponent: String): Boolean {

        val mainHand = calculateHandTypeP2(main);
        val opponentHand = calculateHandTypeP2(opponent);

        // Win by hand type only?
        if (mainHand > opponentHand)
            return true;
        else if (mainHand < opponentHand)
            return false;


        for (i in main.indices) {
            val mainCard = calculateCardStrengthP2(main[i]);
            val opponentCard = calculateCardStrengthP2(opponent[i]);
            if (mainCard > opponentCard)
                return true;
            else if (mainCard < opponentCard)
                return false;
        }

        return false;
    }

    private fun calculateHandTypeP2(hand: String): Int {
        val amounts = mutableMapOf<Char, Int>()
        for (card in hand) {
            if (!amounts.containsKey(card))
                amounts[card] = 1;
            else
                amounts[card] = amounts[card]!!+1;
        }

        if (!hand.contains('J')) return calculateHandType(hand);

        // Check for five of a kind
        if (amounts.size == 1)
            return 7;

        for (a in amounts) {
            for (i in 0..5) {
                if (a.value == 5-i && amounts['J'] == i)
                    return 7;
            }
        }

        // Check for four of a kind
        for (a in amounts) {
            if (a.key == 'J')
                continue;
            for (i in 0..4) {
                if (a.value == 4-i && amounts['J'] == i)
                    return 6;
            }
        }

        // Check for full house
        var twoFound: Char? = null;
        var threeFound: Char? = null;
        for (a in amounts) {
            if (a.key == 'J')
                continue;
            if (a.value == 2) {
                if (twoFound != null)
                    continue;
                twoFound = a.key;
            } else if (a.value == 3) {
                if (threeFound != null)
                    continue;
                threeFound = a.key;
            }
        }

        if (twoFound != null || threeFound != null) {
            var byAmount = if (twoFound != null) 3 else 2;
            for (a in amounts) {
                if (a.key == twoFound)
                    continue;

                for (i in 0..byAmount) {
                    if (a.value == byAmount-i && amounts['J'] == i)
                        return 5;
                }
            }
        }

        // Check for three of a kind
        for (a in amounts) {
            for (i in 0..3) {
                if (a.value == 3-i && amounts['J'] == i)
                    return 4;
            }
        }

        // Check for two pair
        var onePair = false;
        for (a in amounts) {
            if (a.value == 2)
                if (!onePair)
                    onePair = true;
        }
        if (onePair) {
            for (a in amounts) {
                for (i in 0..2) {
                    if (a.value == 2-i && amounts['J'] == i)
                        return 3;
                }
            }
        }


        // Check for one pair
        for (a in amounts) {
            for (i in 0..2) {
                if (a.value == 2-i && amounts['J'] == i)
                    return 2;
            }
        }

        // Has to be a high card
        return 1;
    }

    private fun calculateCardStrengthP2(card: Char): Int {
        when (card) {
            'A' -> return 13;
            'K' -> return 12;
            'Q' -> return 11;
            'T' -> return 10;
            '9' -> return 9;
            '8' -> return 8;
            '7' -> return 7;
            '6' -> return 6;
            '5' -> return 5;
            '4' -> return 4;
            '3' -> return 3;
            '2' -> return 2;
            'J' -> return 1;
            else -> return 0;
        }
    }

}