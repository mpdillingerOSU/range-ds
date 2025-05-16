package ranges;

/**
 * An instance of this class is used as the basis for classes that are capable
 * of being placed in a specific integer-based sequence.
 * @author Michael Dillinger
 * @since 0.1.0
 */
public interface Orderable<T> extends Comparable<T> {
    /**
     * Returns the integer value representing the instance.
     * @return the integer value representing the instance.
     */
    int intValue();

    /**
     * Returns the long value representing the instance.
     * @return the long value representing the instance.
     */
    long longValue();

    /**
     * Returns the value representing the provided long being added to the
     * instance.
     * @param val the integer value being added to the instance.
     * @return the value representing the provided integer being added to the
     * instance.
     */
    T add(long val);

    /**
     * Returns the value representing the provided long being subtracted
     * from the instance.
     * @param val the integer value being subtracted from the instance.
     * @return the value representing the provided integer being subtracted
     * from the instance.
     */
    T subtract(long val);
}
