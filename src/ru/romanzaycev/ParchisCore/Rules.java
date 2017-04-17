package ru.romanzaycev.ParchisCore;

import ru.romanzaycev.ParchisCore.Directions.Direction;
import ru.romanzaycev.ParchisCore.Engine.*;

import java.util.ArrayList;

/**
 * Parchis game rules interface.
 * <p>
 * Implementation of this interface controls the game process:
 * game board structure (with <a href="Layout.html">Layout</a>), possible moves,
 * turns, players switch and etc.
 *
 * @author Roman Zaycev
 * @version 0.0.1
 */
public interface Rules {
    /**
     * Get minimum number of players for this game rules.
     * <p>
     * The return value MUST BE NOT changed in runtime.
     *
     * @return number of players
     *
     * @see Layout
     */
    int getMinPlayers();

    /**
     * Get maximum number of players for this game rules.
     * <p>
     * The return value MUST BE NOT changed in runtime.
     *
     * @return number of players
     *
     * @see Layout
     */
    int getMaxPlayers();

    /**
     * Get number of player pawns.
     * <p>
     * The return value MUST BE NOT changed in runtime.
     *
     * @return number of pawns
     *
     * @see <a href="#getNumberOfHomeCells--">getNumberOfHomeCells</a>
     */
    int getNumberOfPlayerPawns();

    /**
     * Get number of dices for this game rules.
     * <p>
     * The return value MUST BE NOT changed in runtime.
     *
     * @return number of dices
     */
    int getNumberOfDices();

    /**
     * Get number of playable cells.
     * <p>
     * The return value MUST BE NOT changed in runtime.
     *
     * @return number of cells
     */
    int getNumberOfCells();

    /**
     * Get number of home cells.
     * <p>
     * The return value MUST BE NOT changed in runtime.
     *
     * @return number of cells
     */
    int getNumberOfHomeCells();

    /**
     * Get number of Safe cells.
     * <p>
     * The return value MUST BE NOT changed in runtime.
     *
     * @return number of cells
     */
    int getNumberOfSafeCells();

    /**
     * Check pawns eating mode.
     *
     * @return true if eating required
     */
    boolean isEatRequired();

    /**
     * Check turn skip mode.
     *
     * @return true if turn skip allowed
     */
    boolean isTurnSkipAllowed();

    /**
     * Get players turn direction (left to right or right to left).
     *
     * @return players turn direction
     *
     * @see ru.romanzaycev.ParchisCore.Directions.LtrDirection
     * @see ru.romanzaycev.ParchisCore.Directions.RtlDirection
     */
    Direction getTurnDirection();

    /**
     * Get pawns move direction (left to right or right to left).
     *
     * @return pawns move deirection
     *
     * @see ru.romanzaycev.ParchisCore.Directions.LtrDirection
     * @see ru.romanzaycev.ParchisCore.Directions.RtlDirection
     */
    Direction getMoveDirection();

    /**
     * Get game board layout.
     * <p>
     * The return value MUST BE NOT changed in runtime.
     *
     * @return board layout
     */
    Layout getLayout();

    /**
     * Check winner player.
     *
     * @param player instance
     * @param state  instance
     *
     * @return true if player wins
     */
    boolean isPlayerWin(Player player, State state);

    /**
     * Check game finish.
     *
     * @param state instance
     *
     * @return true if game ended
     */
    boolean isGameFinished(State state);

    /**
     * Get pawn possible moves.
     *
     * @param pawn  instance
     * @param state instance
     *
     * @return list of possible moves
     */
    ArrayList<PossibleMove> getPossibleMoves(Pawn pawn, State state);

    /**
     * Check player extra turn.
     *
     * @param state instance
     *
     * @return true if player can make another move
     */
    boolean isExtraTurn(State state);

    /**
     * Get dice minimal possible random value.
     *
     * @return minimal value
     */
    int getDiceMin();

    /**
     * Get dice maximum possible random value.
     *
     * @return maximum value
     */
    int getDiceMax();

    /**
     * Get value of dice for pawn start.
     * <p>
     * NOTE: Probably "6"
     *
     * @return number
     */
    int getStartDiceValue();
}
