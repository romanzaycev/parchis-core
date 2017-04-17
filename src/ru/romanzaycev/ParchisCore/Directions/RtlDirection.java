package ru.romanzaycev.ParchisCore.Directions;

/**
 * Right to left game direction.
 *
 * @author Roman Zaycev
 * @version 0.0.1
 * @see <a href="Rules.html#getTurnDirection--">Rules.getTurnDirection</a>
 * @see <a href="Rules.html#getTurnDirection--">Rules.getMoveDirection</a>
 */
public class RtlDirection extends Direction {
    @Override
    public boolean isRtl() {
        return true;
    }
}
