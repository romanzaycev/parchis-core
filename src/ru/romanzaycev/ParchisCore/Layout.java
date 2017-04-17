package ru.romanzaycev.ParchisCore;

/**
 * Parchis game board layout interface.
 * <p>
 * Implementation of this interface represents game board layout:
 * positions of start cells, positions of home and safe cells.
 *
 * @author Roman Zaycev
 * @version 0.0.1
 * @see Rules
 */
public interface Layout {
    /**
     * Get player start cell position.
     * <p>
     * Returned value used by game board initialization.
     * Changing value in runtime will not result a new game
     * board initialization.
     * <p>
     * Changing value at runtime may result an error.
     *
     * @param playerIndex index of player
     *
     * @return sibling cell position
     */
    int getStartPosition(int playerIndex);

    /**
     * Get player home cell position.
     * <p>
     * Returned value used by game board initialization.
     * Changing value in runtime will not result a new game
     * board initialization.
     * <p>
     * Changing value at runtime may result an error.
     *
     * @param playerIndex index of player
     *
     * @return sibling position
     */
    int getHomePosition(int playerIndex);

    /**
     * Get safe cell position by index.
     * <p>
     * Returned value used by game board initialization.
     * Changing value in runtime will not result a new game
     * board initialization.
     * <p>
     * Changing value at runtime may result an error.
     *
     * @param cellIndex sibling cell position
     *
     * @return sibling cell position
     */
    int getSafeCellPosition(int cellIndex);
}
