package ru.romanzaycev.ParchisCore.Engine;

import ru.romanzaycev.ParchisCore.Area.*;
import ru.romanzaycev.ParchisCore.Directions.RtlDirection;
import ru.romanzaycev.ParchisCore.Rules;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Game logic.
 *
 * @author Roman Zaycev
 * @version 0.0.1
 */
public class Game {
    private boolean isGameStarted = false;
    private Rules rules;
    private State state = new State();
    private Board board;
    private ArrayList<Player> players = new ArrayList<>();
    private ArrayList<Dice> dices = new ArrayList<>();
    private int tick = 0;
    private int currentPlayerIndex = 0;
    private Turn currentTurn;

    // Events listeners
    private ArrayList<EatListener> eatListeners = new ArrayList<>();

    /**
     * Game constructor.
     *
     * @param rules Current game rules
     */
    public Game(Rules rules) {
        this.rules = rules;
        board = new Board(rules.getNumberOfCells());
    }

    /**
     * Add player to game.
     *
     * @param player Player instance
     */
    public void addPlayer(Player player) {
        if (players.size() < rules.getMaxPlayers()) {
            players.add(player);
            player.setIndex(players.size() - 1);

            // Home cells will be generated in Game.prepareBoard() method
            board.makeStartCells(player, rules.getLayout().getStartPosition(players.size() - 1));

            for (int i = 0; i < rules.getNumberOfPlayerPawns(); i++) {
                player.addPawn();
            }
        }

        state.setCurrentPlayer(players.get(currentPlayerIndex));
    }

    /**
     * Validate and start game.
     */
    public void startGame() throws RuntimeException {
        if (!isGameStarted) {
            // Simple checks
            if (players.size() < rules.getMinPlayers()) {
                throw new RuntimeException("Not enough players");
            }
            if (players.size() > rules.getMaxPlayers()) {
                throw new RuntimeException("Too many players");
            }

            // Make game objects
            makeDices();
            prepareBoard();

            // Update game state
            state.setBoard(board);

            isGameStarted = true;
        }
    }

    /**
     * Get game process status.
     *
     * @return false if game ended
     */
    public boolean isGameProceed() {
        isGameStarted = !rules.isGameFinished(state);
        return isGameStarted;
    }

    /**
     * Make next game tick.
     *
     * @return true if game proceed
     */
    public boolean nextTick() {
        if (isGameStarted) {
            tick++;

            // Update game state
            setStateOnBoardPawns();
            setStateOnSafePawns();
            setStateOnHomePawns();
            state.setTick(tick);

            // Get player by current game rules.
            if (!rules.isExtraTurn(state)) {
                // Switch player
                nextPlayer();
            }

            dices.forEach(Dice::generateValue);
            setStateThrowDicesValues();
            setStateDicesValues();

            // Prepare Turn object
            makeTurnInstance();
        }

        return isGameProceed();
    }

    /**
     * Get game state.
     *
     * @return game state
     */
    public State getState() {
        return state;
    }

    /**
     * Get current turn.
     *
     * @return current turn
     */
    public Turn getTurn() {
        return currentTurn;
    }

    /**
     * Get winner.
     *
     * @return player instance or null
     */
    public Player getWinner() {
        for (Player player : players) {
            if (rules.isPlayerWin(player, state)) {
                return player;
            }
        }

        return null;
    }

    /**
     * Subscribe for "eat" event.
     *
     * @param listener instance
     */
    public synchronized void addEatListener(EatListener listener) {
        eatListeners.add(listener);
    }

    /**
     * Unsubscribe from "eat" event.
     *
     * @param listener instance
     */
    public synchronized void removeEatListener(EatListener listener) {
        eatListeners.remove(listener);
    }

    /**
     * Move pawn.
     *
     * @param pawn instance
     * @param to   cell instance
     */
    protected void move(Pawn pawn, Square to) {
        ArrayList<Integer> values = new ArrayList<>();
        for (int v : state.getDicesValues()) {
            values.add(v);
        }
        for (PossibleMove move : currentTurn.getPossibleMoves().get(pawn)) {
            if (move.getCell().equals(to)) {
                for (int usedValue : move.getDiceValue().getSummands()) {
                    values.remove((Object) usedValue); // It's fine
                }
            }
        }

        isGameProceed();

        // Update state dices values
        state.setDicesValues(values.stream().mapToInt(i -> i).toArray());

        // Game board was updated, recalculate possible moves
        currentTurn.setPossibleMoves(getPossibleMoves());
    }

    /**
     * Get pawn current position (cell).
     *
     * @param pawn instance
     *
     * @return cell or null is pawn on base
     */
    protected Square getPawnCell(Pawn pawn) {
        if (!pawn.isBasePosition()) {
            if (pawn.isHomePosition()) {
                for (Square cell : board.getHomeCells(pawn.getOwner())) {
                    Pawn pawnOnCell = cell.getPawn();
                    if (pawnOnCell != null) {
                        if (pawnOnCell.equals(pawn)) {
                            return cell;
                        }
                    }
                }
            } else {
                for (Square cell : board.getSafeCells()) {
                    Pawn pawnOnCell = cell.getPawn();
                    if (pawnOnCell != null) {
                        if (pawnOnCell.equals(pawn)) {
                            return cell;
                        }
                    }
                }

                // Not found, full board scan
                for (Square cell : board.getCells()) {
                    Pawn pawnOnCell = cell.getPawn();
                    if (pawnOnCell != null) {
                        if (pawnOnCell.equals(pawn)) {
                            return cell;
                        }
                    }
                }
            }
        }

        return null;
    }

    /**
     * Check turn skip allows.
     *
     * @return true if turn skip allowed
     */
    protected boolean isSkipAllowed() {
        return rules.isTurnSkipAllowed();
    }

    /**
     * Emit "eat" event.
     *
     * @param cell  instance
     * @param pawn  eaten pawn instance
     * @param enemy enemy pawn instance
     */
    protected synchronized void fireEatEvent(Square cell, Pawn pawn, Pawn enemy) {
        EatEvent event = new EatEvent(this, currentTurn.getData(), cell, pawn, enemy);

        for (EatListener listener : eatListeners) {
            listener.eatReceived(event);
        }
    }

    /**
     * Generate turn instance for user code.
     */
    private void makeTurnInstance() {
        if (currentTurn != null) {
            state.addTurnToHistory(currentTurn);
        }
        Player player = players.get(currentPlayerIndex);
        currentTurn = new Turn(player, this, state.copy());
        currentTurn.setPossibleMoves(getPossibleMoves());
    }

    /**
     * Get possible moves, generated by current game rules.
     *
     * @return list of cells
     */
    private HashMap<Pawn, ArrayList<PossibleMove>> getPossibleMoves() {
        HashMap<Pawn, ArrayList<PossibleMove>> possibleMoves = new HashMap<>();
        for (Pawn pawn : players.get(currentPlayerIndex).getPawns()) {
            ArrayList<PossibleMove> pawnPossibleMoves = rules.getPossibleMoves(pawn, state);
            if (pawnPossibleMoves != null && pawnPossibleMoves.size() > 0) {
                possibleMoves.put(pawn, pawnPossibleMoves);
            }
        }

        return possibleMoves;
    }

    /**
     * Make dice(s) instance(s).
     */
    private void makeDices() { // @TODO: Add setDice method
        int dices = rules.getNumberOfDices();
        if (dices == 0) {
            dices = 1;
        }
        for (int i = 0; i < dices; i++) {
            this.dices.add(new Dice(rules.getDiceMin(), rules.getDiceMax()));
        }
    }

    /**
     * Prepare game board.
     * <p>
     * Make safe cells;<br>
     * make home cells;<br>
     * make cells unique indexes.
     */
    private void prepareBoard() {
        // Add safe cells
        for (int i = 0; i < rules.getNumberOfSafeCells(); i++) {
            board.addSafeCell(rules.getLayout().getSafeCellPosition(i));
        }

        // Add home cells
        for (int i = 0; i < players.size(); i++) {
            board.makeHomeCells(players.get(i), rules.getNumberOfHomeCells(), rules.getLayout().getHomePosition(i));
        }

        // Calculate home cells indexes
        board.makeIndexes();
    }

    /**
     * Next player.
     */
    private void nextPlayer() {
        if (rules.getTurnDirection() instanceof RtlDirection) {
            currentPlayerIndex++;
            if (currentPlayerIndex == players.size()) {
                currentPlayerIndex = 0;
            }
        } else {
            currentPlayerIndex--;
            if (currentPlayerIndex < 0) {
                currentPlayerIndex = 0;
            }
        }
        state.setCurrentPlayer(players.get(currentPlayerIndex));
    }

    /**
     * Set state throw (immutable) dices values.
     */
    private void setStateThrowDicesValues() {
        int[] values = new int[dices.size()];
        int i = 0;
        for (Dice dice : dices) {
            values[i] = dice.getValue();
            i++;
        }
        state.setThrowDicesValues(values);
    }

    /**
     * Update state current dices values.
     */
    private void setStateDicesValues() {
        int[] values = new int[dices.size()];
        int i = 0;
        for (Dice dice : dices) {
            values[i] = dice.getValue();
            i++;
        }
        state.setDicesValues(values);
    }

    /**
     * Update state: current pawns.
     */
    private void setStateOnBoardPawns() {
        state.setOnBoardPawns(getPawnsList(board.getCells()));
    }

    /**
     * Update state: current pawns in safe cells.
     */
    private void setStateOnSafePawns() {
        state.setOnSafePawns(getPawnsList(board.getSafeCells()));
    }

    /**
     * Update state: current pawns in home cells.
     */
    private void setStateOnHomePawns() {
        HashMap<Player, Pawn[]> homePawns = new HashMap<>();
        for (Player player : players) {
            homePawns.put(player, getPawnsList(board.getHomeCells(player)));
        }
        state.setOnHomePawns(homePawns);
    }

    /**
     * Get list of pawn from list of cells.
     *
     * @param source cells list
     *
     * @return pawns list
     */
    private Pawn[] getPawnsList(ArrayList<Square> source) {
        int length = 0;
        ArrayList<Pawn> pawnsList = new ArrayList<>();
        for (Square cell : source) {
            if (!cell.isEmpty()) {
                pawnsList.add(cell.getPawn());
                length++;
            }
        }
        Pawn[] pawns = new Pawn[length];
        for (int i = 0; i < pawnsList.size(); i++) {
            pawns[i] = pawnsList.get(i);
        }

        return pawns;
    }
}
