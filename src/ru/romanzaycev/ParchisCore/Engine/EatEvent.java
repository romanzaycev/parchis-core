package ru.romanzaycev.ParchisCore.Engine;

import ru.romanzaycev.ParchisCore.Area.Square;

import java.util.EventObject;

/**
 * Eating event.
 *
 * @author Roman Zaycev
 * @version 0.0.1
 */
public class EatEvent extends EventObject {
    private TurnData data;
    private Square cell;
    private Pawn pawn;
    private Pawn enemy;

    /**
     * EatEvent constructor.
     *
     * @param source event emitter instance
     * @param data   turn data instance
     * @param cell   instance
     * @param pawn   that was eaten
     * @param enemy  opponent's pawn
     */
    public EatEvent(Object source, TurnData data, Square cell, Pawn pawn, Pawn enemy) {
        super(source);
        this.data = data;
        this.cell = cell;
        this.pawn = pawn;
        this.enemy = enemy;
    }

    /**
     * Get turn data.
     *
     * @return turn instance
     */
    public TurnData getTurnData() {
        return data;
    }

    /**
     * Get cell.
     *
     * @return cell instance
     */
    public Square getCell() {
        return cell;
    }

    /**
     * Get eaten pawn.
     *
     * @return pawn instance
     */
    public Pawn getPawn() {
        return pawn;
    }

    /**
     * Get enemy pawn.
     *
     * @return pawn instance
     */
    public Pawn getEnemy() {
        return enemy;
    }
}
