package other;

/**
 * This is the class for Counter.
 */
public class Counter {
    //a int value
    private int value;
    /**
     * The constructor for a new Counter.
     * @param value a value
     */
    public Counter(int value) {
        this.value = value;
    }
    /**
     * Add number to current count.
     * @param number a number
     */
    public void increase(int number) {
        this.value = value + number;
    }
    /**
     * Subtract number from current count.
     * @param number a number
     */
    public void decrease(int number) {
        this.value = value - number;
    }
    /**
     * Get current count.
     * @return this value
     */
    public int getValue() {
        return this.value;
    }
    /**
     * Return whether or not this counter value is 0.
     * @return true if value is 0, false otherwise
     */
    public boolean isZero() {
        return (this.value == 0);
    }
}