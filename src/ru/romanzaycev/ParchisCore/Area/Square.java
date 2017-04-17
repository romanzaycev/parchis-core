package ru.romanzaycev.ParchisCore.Area;

import ru.romanzaycev.ParchisCore.Engine.Pawn;

/**
 * Parchis game board square (cell) interface.
 *
 * @author Roman Zaycev
 * @version 0.0.1
 */
public interface Square {
    /**
     * Get cell board position.
     *
     * @return board position
     */
    int getPosition();

    /**
     * Get state.
     *
     * @return true if pawned.
     */
    boolean isEmpty();

    /**
     * Get cell pawn.
     *
     * @return pawn
     */
    Pawn getPawn();
}
