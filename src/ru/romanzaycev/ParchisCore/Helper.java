package ru.romanzaycev.ParchisCore;

import ru.romanzaycev.ParchisCore.Area.*;
import ru.romanzaycev.ParchisCore.Engine.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.IntStream;

/**
 * Library helper class.
 * <p>
 * Use this class for Rules implementation.
 *
 * @author Roman Zaycev
 * @version 0.0.1
 * @see Rules
 */
public class Helper {
    private static HashMap<Player, int[]> lines = new HashMap<>();

    /**
     * Get dice sums.
     * <p>
     * Dice sums calculates for correct turn work.
     *
     * @param values dices values
     *
     * @return dices sums
     */
    public static ArrayList<DiceSum> getDiceSums(int[] values) {
        ArrayList<DiceSum> diceSums = new ArrayList<>();
        ArrayList<Integer> added = new ArrayList<>();

        for (int i = 0; i < values.length; i++) {
            int v = values[i];
            diceSums.add(new DiceSum(v));
            added.add(v);

            for (int j = 0; j < values.length; j++) {
                if (j == i)
                    continue;

                int vv = v + values[j];
                if (!added.contains(vv)) {
                    diceSums.add(new DiceSum(v, values[j]));
                    added.add(vv);
                }
            }
        }

        return diceSums;
    }

    /**
     * Get game line for player.
     * <p>
     * Line calculates for player, cell position and count of cells.
     * This line may be used for calculate pawn possible moves.
     *
     * @param totalLine    full player game line
     * @param board        game board instance
     * @param rules        game rules instance
     * @param player       instance
     * @param fromPosition cell unique position
     * @param count        move count
     *
     * @return list of cells
     */
    public static ArrayList<Square> getLine(int[] totalLine, Board board, Rules rules, Player player, int fromPosition, int count) {
        ArrayList<Square> line = new ArrayList<>();

        Square cell = board.getCellById(fromPosition);

        boolean isBlocked = true;
        if (cell instanceof SafeCell) {
            SafeCell sCell = (SafeCell) cell;
            if (!sCell.isBlocked()) {
                isBlocked = false;
            }
        } else {
            isBlocked = false;
        }

        if (!isBlocked) {
            int index = indexOf(totalLine, fromPosition);
            if (index > -1) {
                index++;
                if (index + 1 <= totalLine.length) {
                    int toIndex = index + count;
                    if (toIndex <= totalLine.length) {
                        int[] chunk = Arrays.copyOfRange(totalLine, index, toIndex);
                        for (int pos : chunk) {
                            line.add(board.getCellById(pos));
                        }
                    } else {
                        System.out.println(toIndex);
                    }
                }
            }
        }

        return line;
    }

    /**
     * Get full player game line.
     * <p>
     * This line may be used for calculate player possible moves.
     *
     * @param board  game board instance
     * @param rules  game rules instance
     * @param player instance
     *
     * @return array of cells positions
     */
    public static int[] getPlayerLine(Board board, Rules rules, Player player) {
        if (lines.containsKey(player)) {
            return lines.get(player);
        }

        int cells = board.getCells().size();
        int homeCells = board.getHomeCells(player).size();

        int[] squares = new int[cells + homeCells + 1];

        HomeCell firstHomeCell = (HomeCell) board.getHomeCells(player).get(0);
        int startPosition = rules.getLayout().getStartPosition(player.getIndex());

        squares[0] = -startPosition;
        int n = startPosition;
        int j = 1;
        while (j < cells + 1) {
            squares[j] = n;
            n--;
            if (n == 0) {
                n = cells;
            }
            j++;
        }

        n = firstHomeCell.getPosition();
        for (int i = 0; i < homeCells; i++) {
            squares[j] = n;
            n--;
            j++;
        }

        lines.put(player, squares);

        return squares;
    }

    /**
     * Return true, if throwed double dices.
     *
     * @param state game state instance
     *
     * @return true if double
     */
    public static boolean isExtraTurnByDouble(State state) {
        if (state != null) {
            if (state.getThrowDicesValues().length > 1) {
                int[] uniqueValues = IntStream.of(state.getThrowDicesValues()).distinct().toArray();

                if (uniqueValues.length == 1 && uniqueValues[0] != 0) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Get index of array element.
     *
     * @param arr array
     * @param val element
     *
     * @return element index
     */
    public static int indexOf(int[] arr, int val) {
        return IntStream.range(0, arr.length).filter(i -> arr[i] == val).findFirst().orElse(-1);
    }
}
