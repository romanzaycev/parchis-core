package ru.romanzaycev.ParchisCore.Engine;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Game dice.
 *
 * @author Roman Zaycev
 * @version 0.0.1
 */
public class Dice {
    private int min = 1;
    private int max = 6;
    private int value = 0;

    /**
     * Dice constructor.
     *
     * @param min Dice minimum value
     * @param max Dice maximum value
     */
    protected Dice(int min, int max) {
        this.min = min;
        this.max = max;
    }

    /**
     * Get dice value.
     *
     * @return int dice value
     */
    public int getValue() {
        return value;
    }

    /**
     * Generate new dice random values.
     */
    protected void generateValue() {
        value = ThreadLocalRandom.current().nextInt(min, max + 1);
    }
}
