package ru.romanzaycev.ParchisCore.Area;

import ru.romanzaycev.ParchisCore.Engine.Pawn;
import ru.romanzaycev.ParchisCore.Engine.Player;

/**
 * Parchis common game cell.
 *
 * @author Roman Zaycev
 * @version 0.0.1
 */
public class Cell implements Square {
    protected Board board;
    private int position;

    /**
     * Cell constructor.
     *
     * @param position on board
     */
    protected Cell(int position) {
        this.position = position;
    }

    /**
     * Make link to board.
     *
     * @param board instance
     */
    void setBoard(Board board) {
        this.board = board;
    }

    /**
     * Get cell board position.
     *
     * @return board position
     */
    @Override
    public int getPosition() {
        return position;
    }

    /**
     * Get state.
     *
     * @return true if cell empty
     */
    @Override
    public boolean isEmpty() {
        for (Player player : board.getPlayers()) {
            for (Pawn pawn : player.getPawns()) {
                if (!pawn.isBasePosition()) {
                    if (pawn.getCell().equals(this)) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    /**
     * Get cell pawn.
     *
     * @return pawn or null
     */
    @Override
    public Pawn getPawn() {
        for (Player player : board.getPlayers()) {
            for (Pawn pawn : player.getPawns()) {
                if (!pawn.isBasePosition()) {
                    if (pawn.getCell().equals(this)) {
                        return pawn;
                    }
                }
            }
        }

        return null;
    }

    /**
     * Set cell position.
     *
     * @param position (unique index)
     */
    protected void setPosition(int position) {
        this.position = position;
    }
}
