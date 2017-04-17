package ru.romanzaycev.ParchisCore.Engine;

import ru.romanzaycev.ParchisCore.Area.Square;

/**
 * Possible move.
 *
 * @author Roman Zaycev
 * @version 0.0.1
 */
public class PossibleMove {
    private DiceSum diceValue;
    private Square cell;

    public PossibleMove(DiceSum diceValue, Square cell) {
        this.diceValue = diceValue;
        this.cell = cell;
    }

    /**
     * Get move dice value.
     *
     * @return dice value
     */
    public DiceSum getDiceValue() {
        return diceValue;
    }

    /**
     * Get move cell.
     *
     * @return cell instance
     */
    public Square getCell() {
        return cell;
    }
}
