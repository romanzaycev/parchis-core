package ru.romanzaycev.ParchisCore.Engine;

import ru.romanzaycev.ParchisCore.Area.*;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Game turn.
 *
 * @author Roman Zaycev
 * @version 0.0.1
 */
public class Turn {
    private Player player;
    private HashMap<Pawn, ArrayList<PossibleMove>> possibleMoves = new HashMap<>();
    private Game game;
    private State state;
    private TurnData data;

    /**
     * Turn constructor.
     *
     * @param player instance
     * @param game   instance
     * @param state  current game state
     */
    protected Turn(Player player, Game game, State state) {
        this.player = player;
        this.game = game;
        this.state = state;
        data = new TurnData(player, state.getDicesValues().clone());
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
     * Get turn possible moves.
     *
     * @return pawns hash of cells list
     */
    public HashMap<Pawn, ArrayList<PossibleMove>> getPossibleMoves() {
        return possibleMoves;
    }

    /**
     * Get current game state.
     *
     * @return game state
     */
    public State getState() {
        return state;
    }

    /**
     * Skip turn, if allowed.
     *
     * @return true if turn skipped.
     */
    public boolean skip() {
        if (isSkipAllowed()) {
            data.setSkipSuccess(true);
        }

        return data.isSkipped();
    }

    /**
     * Move pawn to specified cell.
     *
     * @param pawn instance
     * @param to   cell
     *
     * @return true if success
     */
    public synchronized boolean move(Pawn pawn, Square to) {
        if (!pawn.getOwner().equals(player)) {
            throw new RuntimeException("Pawn owner is not equals current turn player");
        }
        if (isMoveAllowed(pawn, to)) {
            if (!to.isEmpty() && !to.getPawn().getOwner().equals(player)) {
                // Eating
                Pawn enemy = to.getPawn();
                enemy.toBase();
                data.setEatingTurn(true);
                pawn.raiseScore();
                game.fireEatEvent(to, enemy, pawn);
            }

            if (!pawn.isBasePosition()) {
                if (game.getPawnCell(pawn) != null) {
                    data.addFrom(pawn, game.getPawnCell(pawn));
                }
            }

            if (to instanceof HomeCell) {
                data.setToHomeTurn(true);
            }

            data.addPawn(pawn);

            pawn.setCell(to);
            data.addTo(pawn, to);
            game.move(pawn, to);

            data.setMoveSuccess(true);
        }

        return data.isMoved();
    }

    /**
     * Check skip allows.
     *
     * @return true if turn skip allowed
     */
    public boolean isSkipAllowed() {
        return possibleMoves.size() == 0 || game.isSkipAllowed();
    }

    /**
     * Check allowed move.
     *
     * @param pawn instance
     * @param to   cell
     *
     * @return true move allowed
     */
    public boolean isMoveAllowed(Pawn pawn, Square to) {
        if (possibleMoves.containsKey(pawn)) {
            ArrayList<PossibleMove> moves = possibleMoves.get(pawn);
            for (PossibleMove move : moves) {
                if (move.getCell().equals(to)) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Get turn data.
     *
     * @return turn data instance
     */
    protected TurnData getData() {
        return data;
    }

    /**
     * Set possible pawns moves.
     *
     * @param possibleMoves pawns hash of cells list
     */
    protected void setPossibleMoves(HashMap<Pawn, ArrayList<PossibleMove>> possibleMoves) {
        this.possibleMoves = possibleMoves;
    }
}
