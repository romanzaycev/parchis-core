package ru.romanzaycev.ParchisCore.Engine;

import ru.romanzaycev.ParchisCore.Area.Square;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Game turn data.
 *
 * @author Roman Zaycev
 * @version 0.0.1
 */
public class TurnData {
    private Player player;
    private int[] dicesValues;
    private HashMap<Pawn, Square> from = new HashMap<>();
    private HashMap<Pawn, Square> to = new HashMap<>();
    private ArrayList<Pawn> pawns = new ArrayList<>();
    private boolean isEatingTurn = false;
    private boolean isToHomeTurn = false;
    private boolean isSkipSuccess = false;
    private boolean isMoveSuccess = false;

    /**
     * TurnData constructor.
     *
     * @param player instance
     */
    protected TurnData(Player player, int[] dicesValues) {
        this.player = player;
        this.dicesValues = dicesValues;
    }

    /**
     * Get turn player.
     *
     * @return player instance
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Get turn dices values.
     * <p>
     * NOTE For history only.
     *
     * @return array of dices values
     */
    public int[] getDicesValues() {
        return dicesValues;
    }

    /**
     * Check eating turn.
     *
     * @return true if pawn eat other pawn by this turn
     */
    public boolean isEatingTurn() {
        return isEatingTurn;
    }

    /**
     * Check home move.
     *
     * @return true if pawn successful moved to home
     */
    public boolean isToHomeTurn() {
        return isToHomeTurn;
    }

    /**
     * Check skipped move.
     *
     * @return true if turn skipped
     */
    public boolean isSkipped() {
        return isSkipSuccess;
    }

    /**
     * Check success move.
     *
     * @return true if turn successful moved
     */
    public boolean isMoved() {
        return isMoveSuccess;
    }

    /**
     * Get starting move positions.
     *
     * @return cells map
     */
    public HashMap<Pawn, Square> getFromCells() {
        return from;
    }

    /**
     * Get finished move positions.
     *
     * @return cells map
     */
    public HashMap<Pawn, Square> getToCells() {
        return to;
    }

    /**
     * Get turn moved pawns.
     *
     * @return pawns list
     */
    public ArrayList<Pawn> getPawns() {
        return pawns;
    }

    /**
     * Add moved pawn.
     *
     * @param pawn instance
     */
    protected void addPawn(Pawn pawn) {
        if (!pawns.contains(pawn)) {
            pawns.add(pawn);
        }
    }

    /**
     * Add starting move position.
     *
     * @param pawn instance
     * @param cell innstance
     */
    protected void addFrom(Pawn pawn, Square cell) {
        from.put(pawn, cell);
    }

    /**
     * Add finish move position.
     *
     * @param pawn instance
     * @param to   instance
     */
    protected void addTo(Pawn pawn, Square to) {
        this.to.put(pawn, to);
    }

    /**
     * Set "Eating" flag.
     *
     * @param eatingTurn flag
     */
    protected void setEatingTurn(boolean eatingTurn) {
        isEatingTurn = eatingTurn;
    }

    /**
     * Set "toHome" flag.
     *
     * @param toHomeTurn flag
     */
    protected void setToHomeTurn(boolean toHomeTurn) {
        isToHomeTurn = toHomeTurn;
    }

    /**
     * Set "isSkipped" flag.
     *
     * @param skipSuccess flag
     */
    protected void setSkipSuccess(boolean skipSuccess) {
        isSkipSuccess = skipSuccess;
    }

    /**
     * Set successful moved flag.
     *
     * @param moveSuccess flag
     */
    protected void setMoveSuccess(boolean moveSuccess) {
        isMoveSuccess = moveSuccess;
    }

    /**
     * Copy (clone) instance.
     *
     * @return cloned instance
     */
    protected TurnData copy() {
        TurnData clone = new TurnData(player, dicesValues.clone());

        clone.player = player;
        clone.from = from;
        clone.to = to;
        clone.pawns = pawns;
        clone.isEatingTurn = isEatingTurn;
        clone.isToHomeTurn = isToHomeTurn;
        clone.isSkipSuccess = isSkipSuccess;
        clone.isMoveSuccess = isMoveSuccess;

        return clone;
    }
}
