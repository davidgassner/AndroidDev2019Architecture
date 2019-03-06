package com.example.android.architecture.util

import android.content.Context
import com.example.android.architecture.R
import kotlin.random.Random

class DiceHelper {

    companion object {

        // Get a random number between 1 and 6
        private fun getDie(): Int {
            return Random.nextInt(1, 7)
        }

        // Roll the dice, return 5 random integers in an array
        fun rollDice(): IntArray {
            return intArrayOf(
                getDie(),
                getDie(),
                getDie(),
                getDie(),
                getDie()
            )
        }

        // Evaluate the results of a dice roll
        fun evaluateDice(context: Context, dice: IntArray?): String {

            // Initialize a map of die counts
            val result = mutableMapOf(
                Pair(1, 0),
                Pair(2, 0),
                Pair(3, 0),
                Pair(4, 0),
                Pair(5, 0),
                Pair(6, 0)
            )

            // Update the die counts for each of 5 dice
            for (i in 0 until dice!!.size) {
                val currentCount = result.getOrElse(dice[i]) { 0 }
                result[dice[i]] = currentCount + 1
            }

            // Check possible outcomes in order of best roll
            return when {
                result.containsValue(5) ->
                    context.getString(R.string.five_of_a_kind)
                result.containsValue(4) ->
                    context.getString(R.string.four_of_a_kind)
                isFullHouse(result) ->
                    context.getString(R.string.full_house)
                isStraight(dice) ->
                    context.getString(R.string.straight)
                result.containsValue(3) ->
                    context.getString(R.string.three_of_a_kind)
                is2Pairs(result.values) ->
                    context.getString(R.string.two_pairs)
                result.containsValue(2) ->
                    context.getString(R.string.pair)
                else ->
                    context.getString(R.string.nothing_special)
            }

        }

        // Check for a full house
        private fun isFullHouse(result: MutableMap<Int, Int>): Boolean {
            return result.containsValue(3) && result.containsValue(2)
        }

        // Check for 2 pairs
        private fun is2Pairs(values: MutableCollection<Int>): Boolean {
            var foundPair = false
            for (value in values) {
                if (value == 2) {
                    if (foundPair) return true else foundPair = true
                }
            }
            return false
        }

        // Check for straight
        private fun isStraight(dice: IntArray): Boolean {
            return (dice.contains(1) || dice.contains(6)) &&
                    dice.contains(2) &&
                    dice.contains(3) &&
                    dice.contains(4) &&
                    dice.contains(5)
        }

    }

}