package ru.romanzaycev.ParchisCore.Engine;

import ru.romanzaycev.ParchisCore.Area.Board;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Current game state.
 *
 * @author Roman Zaycev
 * @version 0.0.1
 */
public class State {
    private int tick = 0;
    private Player currentPlayer;
    private int[] throwDicesValues = new int[0];
    private int[] dicesValues = new int[0];
    private Board board;
    private Pawn[] onBoardPawns = new Pawn[0];
    private Pawn[] onSafePawns = new Pawn[0];
    private HashMap<Player, Pawn[]> onHomePawns = new HashMap<>();
    private State previousState;
    private ArrayList<TurnData> turns = new ArrayList<>();

    /**
     * State constructor.
     */
    protected State() {

    }

    /**
     * Set current game tick.
     *
     * @param tick number
     */
    protected void setTick(int tick) {
        previousState = this.copy();
        this.tick = tick;
    }

    /**
     * Get current game tick.
     *
     * @return int
     */
    public int getTick() {
        return tick;
    }

    /**
     * Set throw dices values.
     *
     * @param values array of values
     */
    protected void setThrowDicesValues(int[] values) {
        throwDicesValues = values;
    }

    /**
     * Set current dices values.
     *
     * @param values array of values
     */
    protected void setDicesValues(int[] values) {
        dicesValues = values;
    }

    /**
     * Get throw dices values.
     *
     * @return int[]
     */
    public int[] getThrowDicesValues() {
        return throwDicesValues;
    }

    /**
     * Get current dices values.
     *
     * @return int[]
     */
    public int[] getDicesValues() {
        return dicesValues;
    }

    /**
     * Set current player.
     *
     * @param player instance
     */
    protected void setCurrentPlayer(Player player) {
        this.currentPlayer = player;
    }

    /**
     * Get current player.
     *
     * @return player instance
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Set game board.
     *
     * @param board instance
     */
    protected void setBoard(Board board) {
        this.board = board;
    }

    /**
     * Get game board.
     *
     * @return game board
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Get previous game state.
     *
     * @return previous game state or null
     */
    public State getPreviousState() {
        return previousState;
    }

    /**
     * Set on board pawns.
     *
     * @param pawns array of pawns
     */
    protected void setOnBoardPawns(Pawn[] pawns) {
        onBoardPawns = pawns;
    }

    /**
     * Get on board placed pawns.
     *
     * @return array of pawns
     */
    public Pawn[] getOnBoardPawns() {
        return onBoardPawns;
    }

    /**
     * Set pawns in safe cells.
     *
     * @param pawns array of pawns
     */
    protected void setOnSafePawns(Pawn[] pawns) {
        onSafePawns = pawns;
    }

    /**
     * Get on safe cells pawns.
     *
     * @return array of pawns
     */
    public Pawn[] getOnSafePawns() {
        return onSafePawns;
    }

    /**
     * Set home pawns.
     *
     * @param pawns array of pawns in hash
     */
    protected void setOnHomePawns(HashMap<Player, Pawn[]> pawns) {
        onHomePawns = pawns;
    }

    /**
     * Get on home cells pawn.
     *
     * @return hash of pawn array
     */
    public HashMap<Player, Pawn[]> getOnHomePawns() {
        return onHomePawns;
    }

    /**
     * Add turn copy to turns history.
     *
     * @param turn instance
     */
    protected void addTurnToHistory(Turn turn) {
        turns.add(turn.getData().copy());
    }

    /**
     * Get turns history.
     *
     * @return list of turns
     */
    public ArrayList<TurnData> getTurnsHistory() {
        return turns;
    }

    protected State copy() {
        State clone = new State();

        clone.tick = tick;
        clone.currentPlayer = currentPlayer;
        clone.throwDicesValues = throwDicesValues;
        clone.dicesValues = dicesValues;
        clone.board = board;
        clone.onBoardPawns = onBoardPawns;
        clone.onSafePawns = onSafePawns;
        clone.onHomePawns = onHomePawns;
        clone.previousState = null;

        return clone;
    }
}
