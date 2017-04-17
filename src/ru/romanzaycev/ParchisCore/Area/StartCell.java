package ru.romanzaycev.ParchisCore.Area;

import ru.romanzaycev.ParchisCore.Engine.Player;

/**
 * Player start cell.
 *
 * @author Roman Zaycev
 * @version 0.0.1
 */
public class StartCell extends SiblingCell {
    private Player owner;

    /**
     * StartCell constructor.
     *
     * @param owner               {Player} instance
     * @param siblingCellPosition cell position
     */
    protected StartCell(Player owner, int siblingCellPosition) {
        super(siblingCellPosition);
        this.owner = owner;
    }

    /**
     * Get start cell owner.
     *
     * @return player instance
     */
    public Player getOwner() {
        return owner;
    }
}
