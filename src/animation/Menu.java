package animation;

/**
 * This is the interface for a Menu that extends the Animation interface.
 * @param <T> a given parameter
 */
public interface Menu<T> extends Animation {
    /**
     * Adds a new selection to the menu.
     * @param key the key to press for the selection
     * @param message the title of the selection
     * @param returnVal the returned value of the selection
     */
    void addSelection(String key, String message, T returnVal);
    /**
     * Returns the status of the menu.
     * @return the status of the menu.
     */
    T getStatus();
    /**
     * Adds a new sub menu to the menu.
     * @param key the key to press for the sub menu
     * @param message the title of the sub menu
     * @param subMenu the sub menu
     */
    void addSubMenu(String key, String message, Menu<T> subMenu);
    /**
     * Clears the current selection of the menu.
     */
    void clearSelection();
}