package ranges;

import java.util.Iterator;
import java.util.List;

/**
 * A class representing a single range of values.
 * @author Michael Dillinger
 * @since 0.1.0
 */
public final class SingleRange<E> extends Range<E>{
    public final E MIN;
    public final E MAX;
    private final long POSSIBILITIES;

    /**
     * Instantiates an instance of {@code SingleRange}, with the provided
     * minimum and maximum bounds, both inclusive. However, note that if the
     * provided maximum were to be less than the provided minimum, then their
     * values are flipped.
     * @param MIN the minimum value of the range.
     * @param MAX the maximum value of the range.
     */
    public <T extends Orderable<E>> SingleRange(final T MIN, final T MAX){
        this(
                (E) MIN,
                (E) MAX,
                new Orderator<>() {
                    @Override
                    public int intValueOf(E val) {
                        return ((Orderable<E>) val).intValue();
                    }

                    @Override
                    public long longValueOf(E val) {
                        return ((Orderable<E>) val).longValue();
                    }

                    @Override
                    public E addTo(E val, long steps) {
                        return ((Orderable<E>) val).add(steps);
                    }

                    @Override
                    public E subtractFrom(E val, long steps) {
                        return ((Orderable<E>) val).subtract(steps);
                    }

                    @Override
                    public int compare(E o1, E o2) {
                        return ((Orderable<E>) o1).compareTo(o2);
                    }
                }
        );
    }

    /**
     * Instantiates an instance of {@code SingleRange}, with the provided
     * minimum and maximum bounds, both inclusive, along with the orderable
     * for their comparison of values. However, note that if the provided
     * maximum were to be less than the provided minimum, then their values
     * are flipped.
     * @param MIN the minimum value of the range.
     * @param MAX the maximum value of the range.
     * @param ORDERATOR the comparator of the range.
     */
    public SingleRange(final E MIN, final E MAX,
                       final Orderator<E> ORDERATOR){
        super(ORDERATOR);

        if(compare(MIN, MAX) <= 0){
            this.MIN = MIN;
            this.MAX = MAX;
        } else {
            this.MIN = MAX;
            this.MAX = MIN;
        }

        this.POSSIBILITIES = longValueOf(MAX) - longValueOf(MIN) + 1;
    }

    /**
     * Factory method that instantiates an instance of {@code SingleRange}
     * for generic type {@code Byte}, with the provided minimum and maximum
     * bounds, both inclusive. However, note that if the provided maximum were
     * to be less than the provided minimum, then their values are flipped.
     * @param min the minimum value of the range.
     * @param max the maximum value of the range.
     */
    public static SingleRange<Byte> ofByte(Byte min, Byte max){
        return new SingleRange<>(min, max, Orderators.BYTE);
    }

    /**
     * Factory method that instantiates an instance of {@code SingleRange}
     * for generic type {@code Short}, with the provided minimum and maximum
     * bounds, both inclusive. However, note that if the provided maximum were
     * to be less than the provided minimum, then their values are flipped.
     * @param min the minimum value of the range.
     * @param max the maximum value of the range.
     */
    public static SingleRange<Short> ofShort(Short min, Short max){
        return new SingleRange<>(min, max, Orderators.SHORT);
    }

    /**
     * Factory method that instantiates an instance of {@code SingleRange}
     * for generic type {@code Integer}, with the provided minimum and maximum
     * bounds, both inclusive. However, note that if the provided maximum were
     * to be less than the provided minimum, then their values are flipped.
     * @param min the minimum value of the range.
     * @param max the maximum value of the range.
     */
    public static SingleRange<Integer> ofInteger(Integer min, Integer max){
        return new SingleRange<>(min, max, Orderators.INTEGER);
    }

    /**
     * Factory method that instantiates an instance of {@code SingleRange}
     * for generic type {@code Long}, with the provided minimum and maximum
     * bounds, both inclusive. However, note that if the provided maximum were
     * to be less than the provided minimum, then their values are flipped.
     * @param min the minimum value of the range.
     * @param max the maximum value of the range.
     */
    public static SingleRange<Long> ofLong(Long min, Long max){
        return new SingleRange<>(min, max, Orderators.LONG);
    }

    /**
     * Factory method that instantiates an instance of {@code SingleRange}
     * for generic type {@code Character}, with the provided minimum and
     * maximum bounds, both inclusive. However, note that if the provided
     * maximum were to be less than the provided minimum, then their values
     * are flipped.
     * @param min the minimum value of the range.
     * @param max the maximum value of the range.
     */
    public static SingleRange<Character> ofCharacter(Character min, Character max){
        return new SingleRange<>(min, max, Orderators.CHARACTER);
    }

    /**
     * Factory method that instantiates an instance of {@code SingleRange}
     * for generic type {@code Float}, with the provided minimum and
     * maximum bounds, both inclusive, along with the minimum increment. This
     * minimum increment is necessary in order to have segmentation, due to
     * the theoretically infinite number of values between any given two
     * integers. However, note that if the provided maximum were to be less
     * than the provided minimum, then their values are flipped.
     * @param min the minimum value of the range.
     * @param max the maximum value of the range.
     * @param minIncrement the minimum increment of the range.
     */
    public static SingleRange<Float> ofFloat(Float min, Float max,
                                             float minIncrement){
        return new SingleRange<>(min, max, Orderators.ofFloat(minIncrement));
    }

    /**
     * Factory method that instantiates an instance of {@code SingleRange}
     * for generic type {@code Double}, with the provided minimum and
     * maximum bounds, both inclusive. However, note that if the provided
     * maximum were to be less than the provided minimum, then their values
     * are flipped.
     * @param min the minimum value of the range.
     * @param max the maximum value of the range.
     * @param minIncrement the minimum increment of the range.
     */
    public static SingleRange<Double> ofDouble(Double min, Double max,
                                               double minIncrement){
        return new SingleRange<>(min, max, Orderators.ofDouble(minIncrement));
    }

    /**
     * Factory method that instantiates an instance of {@code SingleRange}
     * for generic type {@code Character}, with the provided minimum and
     * maximum bounds, both inclusive. However, note that if the provided
     * maximum were to be less than the provided minimum, then their values
     * are flipped.
     * @param min the minimum value of the range.
     * @param max the maximum value of the range.
     */
    public static <E extends Enum<E>> SingleRange<E> ofEnum(E min, E max){
        return new SingleRange<>(
                min,
                max,
                Orderators.ofEnum(min.getDeclaringClass())
        );
    }

    /**
     * Returns the minimum value within the range.
     * @return the minimum value within the range.
     */
    public E min(){
        return MIN;
    }

    /**
     * Returns the maximum value within the range.
     * @return the maximum value within the range.
     */
    public E max(){
        return MAX;
    }

    /**
     * Returns the number of possibilities within the range.
     * @return the number of possibilities within the range, if that value is
     * less than {@code Integer.MAX_VALUE}. Else, {@code Integer.MAX_VALUE}.
     */
    @Override
    public int size(){
        return POSSIBILITIES > Integer.MAX_VALUE ? Integer.MAX_VALUE : (int) POSSIBILITIES;
    }

    /**
     * Helper method for internal calculations only that returns the true
     * number of possibilities within the range. This differs from {@code
     * size()}, which is capped off at {@code Integer.MAX_VALUE} in order to
     * align with the Java-standard data structures.
     * @return the true number of possibilities within the range.
     */
    @Override
    //package-private
    long possibilities(){
        return POSSIBILITIES;
    }

    /**
     * Helper method for internal calculations only that returns the value
     * at the provided index within the range. This differs from {@code
     * get()}, which is capped off at {@code Integer.MAX_VALUE} in order to
     * align with the Java-standard data structures.
     * @return the true number of possibilities within the range.
     */
    //package-private
    E getByLong(long index){
        if(index < 0 || index >= POSSIBILITIES){
            throw new IndexOutOfBoundsException(index + " is out of bounds for size " + size());
        }

        return addTo(MIN, index);
    }

    /**
     * Returns the index of the first occurrence of the provided element
     * within the instance. Note that the method uses
     * {@code Objects.equals(val, get(i))} in order to check for the value to
     * be searched for, and not the address of the objects themselves being
     * compared.
     * @param val the element to be searched for.
     * @return the index of the first occurrence of the provided element
     * within the instance, if it exists. Else, {@code -1}.
     */
    @Override
    public int indexOf(Object val){
        try {
            return contains(val) ? (int) (ORDERATOR.longValueOf((E) val) - ORDERATOR.longValueOf(MIN)) : -1;
        } catch (ClassCastException e){
            return -1;
        }
    }

    /**
     * Returns the value having been bound to the range.
     * @param val the value to be bound.
     * @return the value having been bound to the range.
     */
    public E bound(E val){
        if(val == null){
            return null;
        }

        return compare(val, MIN) <= 0 ? MIN : compare(val, MAX) >= 0 ? MAX : val;
    }

    /**
     * Returns whether the value is within the range of the instance.
     * @param val the value to be bounds checked.
     * @return {@code true} if the value is within the range of the instance.
     * Else, {@code false}.
     */
    public boolean contains(Object val){
        try{
            return compare((E) val, MIN) >= 0 && compare((E) val, MAX) <= 0;
        } catch (ClassCastException e){
            return false;
        }
    }

    /**
     * Returns whether the entirety of the provided range occurs within the
     * range of the instance.
     * @param range the range to be bounds checked.
     * @return {@code true} if the entirety of the provided range occurs
     * within the range of the instance.
     * Else, {@code false}.
     */
    public boolean contains(Range<E> range){
        return contains(range.min()) && contains(range.max());
    }

    /**
     * Returns a range of all values within the instance that occur before the
     * provided value.
     * @param val the value to compare.
     * @return a range of all values within the instance that occur before the
     * provided value.
     */
    public SingleRange<E> before(E val){
        if(val == null){
            throw new NullPointerException("argument val is null");
        }

        if(compare(val, MAX) > 0){
            return this;
        } else if(compare(val, MIN) <= 0){
            return null;
        }

        return new SingleRange<>(MIN, subtractFrom(val, 1), ORDERATOR);
    }

    /**
     * Returns a range of all values within the instance that occur after the
     * provided value.
     * @param val the value to compare.
     * @return a range of all values within the instance that occur after the
     * provided value.
     */
    public SingleRange<E> after(E val){
        if(val == null){
            throw new NullPointerException("argument val is null");
        }

        if(compare(val, MIN) < 0){
            return this;
        } else if(compare(val, MAX) >= 0){
            return null;
        }

        return new SingleRange<>(addTo(val, 1), MAX, ORDERATOR);
    }

    /**
     * Converts the instance into a multi-range.
     * @return a multi-range representing the instance.
     */
    protected MultiRange<E> toMulti(){
        return new MultiRange<>(List.of(this));
    }

    /**
     * Returns whether or not the provided obj is equal to the instance. They
     * are equal if the obj is itself an instance of {@code SingleRange}, and
     * if their attributes are equal.
     * @param obj the obj to be compared to the instance.
     * @return {@code true}, if the obj is equal to the instance. Else, {@code
     * false}.
     */
    @Override
    public boolean equals(Object obj){
        return obj == this || (obj instanceof SingleRange<?> range
                && range.MIN.equals(MIN) && range.MAX.equals(MAX));
    }

    /**
     * Returns a string of the values representing the range of the instance.
     * @return a string of the values representing the range of the instance.
     */
    @Override
    public String toString(){
        return "[" + MIN + ", " + MAX + "]";
    }

    /**
     * Returns an {@code Iterator} over the possibilities of the range.
     * @return an {@code Iterator} over the possibilities of the range.
     */
    @Override
    public Iterator<E> iterator() {
        return new Iterator<>() {
            private int current = -1;

            /**
             * Returns {@code true} if the iteration has more elements. (In other words, returns
             * {@code true} if next would return an element rather than throwing an exception.)
             * @return {@code true}, if the iteration has more elements.
             */
            @Override
            public boolean hasNext() {
                return current < POSSIBILITIES - 1;
            }

            /**
             * Returns the next element in the iteration.
             * @return the next element in the iteration.
             */
            @Override
            public E next() {
                return getByLong(++current);
            }

            /**
             * Throws an exception, due to removal during iteration not being supported.
             */
            @Override
            public void remove() {
                throw new UnsupportedOperationException("Unsupported Operation");
            }
        };
    }
}