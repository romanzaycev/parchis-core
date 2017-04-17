package ru.romanzaycev.ParchisCore.Area;

/**
 * Safe (common) cell.
 *
 * @author Roman Zaycev
 * @version 0.0.1
 */
public class SafeCell extends SiblingCell {
    /**
     * SafeCell constructor.
     *
     * @param siblingCellPosition sibling cell position
     */
    protected SafeCell(int siblingCellPosition) {
        super(siblingCellPosition);
    }

    /**
     * Get blocked status.
     *
     * @return true if cell blocked
     */
    public boolean isBlocked() {
        return !getSiblingCell().isEmpty();
    }
}
