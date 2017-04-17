package ru.romanzaycev.ParchisCore.Directions;

/**
 * Left to right game direction.
 *
 * @author Roman Zaycev
 * @version 0.0.1
 * @see <a href="Rules.html#getTurnDirection--">Rules.getTurnDirection</a>
 * @see <a href="Rules.html#getTurnDirection--">Rules.getMoveDirection</a>
 */
public class LtrDirection extends Direction {
    @Override
    public boolean isLtr() {
        return true;
    }
}
