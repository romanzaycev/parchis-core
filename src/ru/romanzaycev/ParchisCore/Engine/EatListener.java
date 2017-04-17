package ru.romanzaycev.ParchisCore.Engine;

/**
 * Eating event listener.
 *
 * @author Roman Zaycev
 * @version 0.0.1
 * @see EatEvent
 */
public interface EatListener {
    public void eatReceived(EatEvent event);
}
