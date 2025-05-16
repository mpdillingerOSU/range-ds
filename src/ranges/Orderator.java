package ranges;

import java.util.Comparator;

/**
 * An instance of this class is used as the basis for classes that are capable
 * of being placed in a specific integer-based sequence.
 * @author Michael Dillinger
 * @since 0.1.0
 */
public interface Orderator<T> extends Comparator<T> {
    /**
     * Returns the integer value representing the instance.
     * @return the integer value representing the instance.
     */
    int intValueOf(T val);

    /**
     * Returns the long value representing the instance.
     * @return the long value representing the instance.
     */
    long longValueOf(T val);

    /**
     * Returns the result representing the provided value having been
     * incremented by the provided number of steps.
     * @param val the value to be shifted.
     * @param steps the number of steps to increment the provided value by.
     * @return the result representing the provided value having been
     * incremented by the provided number of steps.
     */
    T addTo(T val, long steps);

    /**
     * Returns the result representing the provided value having been
     * decremented by the provided number of steps.
     * @param val the value to be shifted.
     * @param steps the number of steps to decrement the provided value.
     * @return the result representing the provided value having been
     * decremented by the provided number of steps.
     */
    T subtractFrom(T val, long steps);
}
