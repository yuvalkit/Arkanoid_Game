package listeners;

/**
 * This is the interface for HitNotifier.
 */
public interface HitNotifier {
    /**
     * Add hl as a listener to hit events.
     * @param hl a hit listener
     */
    void addHitListener(HitListener hl);
    /**
     * Remove hl from the list of listeners to hit events.
     * @param hl a hit listener
     */
    void removeHitListener(HitListener hl);
}