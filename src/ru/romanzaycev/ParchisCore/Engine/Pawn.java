package ru.romanzaycev.ParchisCore.Engine;

import ru.romanzaycev.ParchisCore.Area.*;

/**
 * Parchis player pawn.
 *
 * @author Roman Zaycev
 * @version 0.0.1
 */
public class Pawn {
    private Player owner;
    private Square square;
    private int id = 0;
    private int score = 0;

    /**
     * Pawn constructor.
     *
     * @param owner {Player} instance
     * @param id    pawn id (for player)
     */
    protected Pawn(Player owner, int id) {
        this.owner = owner;
        this.id = id;
    }

    /**
     * Get pawn owner.
     *
     * @return player instance
     */
    public Player getOwner() {
        return owner;
    }

    /**
     * Get current pawn position (square).
     *
     * @return square instance or null if pawn on base
     */
    public Square getCell() {
        return square;
    }

    /**
     * Get pawn id.
     *
     * @return pawn id
     */
    public int getId() {
        return id;
    }

    /**
     * Get pawn score
     *
     * @return pawn score
     */
    public int getScore() {
        return score;
    }

    /**
     * Check is pawn in base position.
     *
     * @return true if pawn at base
     */
    public boolean isBasePosition() {
        return (square == null);
    }

    /**
     * Check is pawn on start position.
     *
     * @return true if pawn on start
     */
    public boolean isStartPosition() {
        return (square != null && square instanceof StartCell);
    }

    /**
     * Check is pawn in home position.
     *
     * @return true if pawn in home
     */
    public boolean isHomePosition() {
        return (square != null && square instanceof HomeCell);
    }

    /**
     * Set pawn square.
     *
     * @param square instance
     */
    protected void setCell(Square square) {
        this.square = square;
    }

    /**
     * Raise pawn score.
     */
    protected void raiseScore() {
        score++;
    }

    /**
     * Move pawn to base.
     */
    protected void toBase() {
        square = null;
    }
}
