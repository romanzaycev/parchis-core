package ru.romanzaycev.ParchisCore.Area;

import ru.romanzaycev.ParchisCore.Engine.Player;

/**
 * Player home (end) cell.
 *
 * @author Roman Zaycev
 * @version 0.0.1
 */
public class HomeCell extends Cell {
    private Player owner;
    private int inPosition;

    /**
     * HomeCell constructor.
     *
     * @param owner      {Player} instance.
     * @param position   cell position
     * @param inPosition home sector start position
     */
    protected HomeCell(Player owner, int position, int inPosition) {
        super(position);
        this.inPosition = inPosition;
        this.owner = owner;
    }

    /**
     * Get home sector start position (first home cell sibling).
     *
     * @return home sector start position
     */
    public int getInPosition() {
        return inPosition;
    }

    /**
     * Get home cell owner.
     *
     * @return player instance.
     */
    public Player getOwner() {
        return owner;
    }
}
