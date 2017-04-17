package ru.romanzaycev.ParchisCore.Area;

/**
 * Branch cell.
 *
 * @author Roman Zaycev
 * @version 0.0.1
 * @see SafeCell
 * @see StartCell
 */
abstract public class SiblingCell extends Cell {
    private int siblingCellPosition;

    /**
     * SiblingCell constructor.
     *
     * @param siblingCellPosition sibling cell position
     */
    protected SiblingCell(int siblingCellPosition) {
        super(
                (siblingCellPosition < 0)
                        ? siblingCellPosition - 1
                        : -siblingCellPosition
        );
        this.siblingCellPosition = siblingCellPosition;
    }

    /**
     * Get sibling cell position.
     *
     * @return cell position
     */
    public int getSiblingCellPosition() {
        return siblingCellPosition;
    }

    /**
     * Get sibling cell.
     *
     * @return sibling cell instance
     */
    public Square getSiblingCell() {
        return this.board.getCellById(siblingCellPosition);
    }
}
