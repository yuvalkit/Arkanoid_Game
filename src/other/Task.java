package other;

/**
 * This is the class for Task.
 * @param <T> a given parameter
 */
public interface Task<T> {
    /**
     * Run that task.
     * @return a returned value from the task
     */
    T run();
}