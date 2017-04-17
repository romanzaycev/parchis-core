package ru.romanzaycev.ParchisCore.Engine;

import java.util.Arrays;

/**
 * All values of dice.
 *
 * @author Roman Zaycev
 * @version 0.0.1
 */
public class DiceSum {
    private int[] summands;

    /**
     * DiceSum constructor.
     *
     * @param summands array of values
     */
    public DiceSum(int... summands) {
        this.summands = summands;
    }

    /**
     * Get all values.
     *
     * @return array of summands values
     */
    public int getValue() {
        return Arrays.stream(summands).sum();
    }

    /**
     * Get summands.
     *
     * @return array of dice values
     */
    public int[] getSummands() {
        return summands;
    }

    @Override
    public String toString() {
        return String.format("%d:%s", getValue(), Arrays.toString(summands));
    }
}
