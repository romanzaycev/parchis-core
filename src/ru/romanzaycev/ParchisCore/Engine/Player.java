package ru.romanzaycev.ParchisCore.Engine;

import java.util.ArrayList;

/**
 * Parchis player.
 *
 * @author Roman Zaycev
 * @version 0.0.1
 */
public class Player {
    private ArrayList<Pawn> pawns = new ArrayList<>();
    private String name;
    private int index = 0;

    /**
     * Player constructor.
     *
     * @param name player name
     */
    public Player(String name) {
        this.name = name;
    }

    /**
     * Get player name.
     *
     * @return player name
     */
    public String getName() {
        return name;
    }

    /**
     * Set player index
     *
     * @param index player index
     */
    protected void setIndex(int index) {
        this.index = index;
    }

    /**
     * Get player index.
     *
     * @return player index
     */
    public int getIndex() {
        return index;
    }

    /**
     * Add pawn.
     */
    public void addPawn() {
        pawns.add(new Pawn(this, pawns.size()));
    }

    /**
     * Get player pawns list.
     *
     * @return pawns list
     */
    public ArrayList<Pawn> getPawns() {
        return pawns;
    }

    /**
     * Get player score.
     *
     * @return player score
     */
    public int getScore() {
        int score = 0;

        for (Pawn pawn : pawns) {
            score += pawn.getScore();
        }

        return score;
    }

    /**
     * Get player name.
     *
     * @return player name
     */
    @Override
    public String toString() {
        return name;
    }
}
