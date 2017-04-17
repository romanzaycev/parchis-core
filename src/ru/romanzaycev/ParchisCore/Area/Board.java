package ru.romanzaycev.ParchisCore.Area;

import ru.romanzaycev.ParchisCore.Engine.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Parchis game board.
 * <p>
 * Represents board common cells, safe cells, players start and home cells.
 *
 * @author Roman Zaycev
 * @version 0.0.1
 * @see Cell
 * @see StartCell
 * @see HomeCell
 * @see SafeCell
 */
public class Board {
    private ArrayList<Player> players = new ArrayList<>();
    private ArrayList<Square> cells = new ArrayList<>();
    private HashMap<Player, ArrayList<Square>> homeCells = new HashMap<>();
    private HashMap<Player, Square> startCells = new HashMap<>();
    private ArrayList<Square> safeCells = new ArrayList<>();
    private HashMap<String, Square> cache = new HashMap<>();
    private boolean isIndexed = false;

    /**
     * Board constructor.
     *
     * @param numberOfCells total number of board cells
     *
     * @see ru.romanzaycev.ParchisCore.Rules
     */
    public Board(int numberOfCells) {
        for (int i = 1; i <= numberOfCells; i++) {
            Cell cell = new Cell(i);
            cell.setBoard(this);
            cells.add(cell);
        }
    }

    /**
     * Generate player home cells.
     *
     * @param player        instance
     * @param count         of home cells
     * @param startPosition of first home cell
     */
    public void makeHomeCells(Player player, int count, int startPosition) {
        ArrayList<Square> cells = new ArrayList<>();
        homeCells.put(player, cells);
        for (int i = 0; i < count; i++) {
            HomeCell cell = new HomeCell(player, i, startPosition);
            cell.setBoard(this);
            cells.add(cell);
        }
        if (!players.contains(player)) {
            players.add(player);
        }
    }

    /**
     * Generate player start cell
     *
     * @param player              instance
     * @param siblingCellPosition sibling cell position
     */
    public void makeStartCells(Player player, int siblingCellPosition) {
        StartCell cell = new StartCell(player, siblingCellPosition);
        cell.setBoard(this);
        startCells.put(player, cell);
    }

    /**
     * Add safe cell to board.
     *
     * @param siblingCellPosition sibling cell position
     */
    public void addSafeCell(int siblingCellPosition) {
        SafeCell cell = new SafeCell(siblingCellPosition);
        cell.setBoard(this);
        safeCells.add(cell);
    }

    /**
     * Get board playable cells.
     *
     * @return board cells list
     */
    public ArrayList<Square> getCells() {
        return cells;
    }

    /**
     * Get player home cells.
     *
     * @param player instance
     *
     * @return player home cells list
     */
    public ArrayList<Square> getHomeCells(Player player) {
        return homeCells.get(player);
    }

    /**
     * Get player start cell.
     *
     * @param player instance
     *
     * @return player start cell
     */
    public Square getStartCell(Player player) {
        return startCells.get(player);
    }

    /**
     * Get safe cells.
     *
     * @return safe cells list
     */
    public ArrayList<Square> getSafeCells() {
        return safeCells;
    }

    /**
     * Get board cell by cell unique index.
     *
     * @param position unique cell id
     *
     * @return cell instance or null
     */
    public Square getCellById(int position) {
        String index = String.valueOf(position);

        if (cache.containsKey(index)) {
            return cache.get(index);
        }

        boolean isFound = false;

        for (Square cell : safeCells) {
            if (cell.getPosition() == position) {
                cache.put(index, cell);
                isFound = true;
                break;
            }
        }

        if (!isFound) {
            for (Map.Entry<Player, Square> entry : startCells.entrySet()) {
                Square cell = entry.getValue();
                if (cell.getPosition() == position) {
                    cache.put(index, cell);
                    isFound = true;
                    break;
                }
            }
        }

        if (!isFound) {
            for (Map.Entry<Player, ArrayList<Square>> entry : homeCells.entrySet()) {
                for (Square cell : entry.getValue()) {
                    if (cell.getPosition() == position) {
                        cache.put(index, cell);
                        isFound = true;
                        break;
                    }
                }
            }
        }

        if (!isFound) {
            for (Square cell : cells) {
                if (cell.getPosition() == position) {
                    cache.put(index, cell);
                    break;
                }
            }
        }

        return cache.get(index);
    }

    /**
     * Make cell indexes.
     */
    public void makeIndexes() {
        if (!isIndexed) {
            int i = -(cells.size() + 1);
            for (Map.Entry<Player, ArrayList<Square>> entry : homeCells.entrySet()) {
                for (Square cell : entry.getValue()) {
                    Cell cellInstance = (Cell) cell;
                    cellInstance.setPosition(i);
                    i--;
                }
            }
            isIndexed = true;
        }
    }

    /**
     * Get list of players.
     *
     * @return list of players
     */
    protected ArrayList<Player> getPlayers() {
        return players;
    }
}
