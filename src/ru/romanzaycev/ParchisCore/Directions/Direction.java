package ru.romanzaycev.ParchisCore.Directions;

/**
 * Game direction.
 *
 * @author Roman Zaycev
 * @version 0.0.1
 * @see <a href="../Rules.html#getTurnDirection--">Rules.getTurnDirection</a>
 * @see <a href="../Rules.html#getTurnDirection--">Rules.getMoveDirection</a>
 */
abstract public class Direction {
    /**
     * Check right to left direction.
     *
     * @return true if direction is right to left
     */
    public boolean isRtl() {
        return false;
    }

    /**
     * Check left to right direction.
     *
     * @return true if direction is left to right
     */
    public boolean isLtr() {
        return false;
    }
}
