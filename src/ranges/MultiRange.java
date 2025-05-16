package ranges;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A class representing multiple ranges of values.
 * @author Michael Dillinger
 * @since 0.1.0
 */
public final class MultiRange<E> extends Range<E>{
    //package-private
    final ArrayList<SingleRange<E>> SINGLE_RANGES;
    private final long POSSIBILITIES;

    /**
     * Instantiates an instance of {@code MultiRange}, with the provided
     * individual ranges. However, note that if there is any intersect within
     * the provided ranges, that those intersecting ranges will be merged
     * together.
     * @param SINGLE_RANGES the single ranges composing the multi-range.
     */
    public MultiRange(final List<SingleRange<E>> SINGLE_RANGES){
        this(
                SINGLE_RANGES,
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
     * Instantiates an instance of {@code MultiRange}, with the provided
     * individual ranges, the orderator for their comparison of values.
     * However, note that if there is any intersect within the provided
     * ranges, that those intersecting ranges will be merged together.
     * @param SINGLE_RANGES the single ranges composing the multi-range.
     * @param ORDERATOR the orderator of the range.
     */
    public MultiRange(final List<SingleRange<E>> SINGLE_RANGES,
                      final Orderator<E> ORDERATOR){
        super(ORDERATOR);

        this.SINGLE_RANGES = SINGLE_RANGES != null
                ? union(SINGLE_RANGES)
                : new ArrayList<>();

        long possibilities = 0;
        for(SingleRange<E> range : this.SINGLE_RANGES){
            possibilities += range.possibilities();
        }
        this.POSSIBILITIES = possibilities;
    }

    /**
     * Returns the minimum value within the range.
     * @return the minimum value within the range.
     */
    public E min(){
        return SINGLE_RANGES.get(0).MIN;
    }

    /**
     * Returns the maximum value within the range.
     * @return the maximum value within the range.
     */
    public E max(){
        return SINGLE_RANGES.get(SINGLE_RANGES.size() - 1).MAX;
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

        for(SingleRange<E> range : SINGLE_RANGES){
            if(index < range.possibilities()){
                return addTo(range.MIN, index);
            }

            index -= range.possibilities();
        }

        throw new IndexOutOfBoundsException(index + " is out of bounds for size " + size());
    }

    /**
     * Returns the value having been bound to the range.
     * @param val the value to be bound.
     * @return the value having been bound to the range.
     */
    public E bound(E val){
        E boundVal = null;
        Long dist = null;
        for(SingleRange<E> range : SINGLE_RANGES){
            if(range.contains(val)){
                return val;
            }

            long minDist = Math.abs(longValueOf(subtractFrom(val, longValueOf(range.MIN))));
            long maxDist = Math.abs(longValueOf(subtractFrom(val, longValueOf(range.MAX))));

            E comp;
            long compDist;
            if(minDist <= maxDist){
                comp = range.MIN;
                compDist = minDist;
            } else {
                comp = range.MAX;
                compDist = maxDist;
            }

            if(dist == null || compDist < dist){
                boundVal = comp;
                dist = compDist;
            }
        }

        return boundVal;
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
        int index;
        long offset = 0;
        for(SingleRange<E> range : SINGLE_RANGES){
            index = range.indexOf(val);

            if(index != -1){
                offset += index;
                return offset > Integer.MAX_VALUE ? Integer.MAX_VALUE : (int) offset;
            }

            offset += range.possibilities();
        }

        return -1;
    }

    /**
     * Returns whether the value is within the range of the instance.
     * @param val the value to be bounds checked.
     * @return {@code true} if the value is within the range of the instance.
     * Else, {@code false}.
     */
    public boolean contains(Object val){
        for(SingleRange<E> range : SINGLE_RANGES){
            if(range.contains(val)){
                return true;
            }
        }

        return false;
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
        if(range instanceof SingleRange<E> singleRange){
            return containsAt(singleRange, 0) != -1;
        } else if(range instanceof MultiRange<E> multiRange){
            int index = 0;
            int foundIndex;
            for(SingleRange<E> singleRange : multiRange.SINGLE_RANGES){
                foundIndex = containsAt(singleRange, index);

                if(foundIndex == -1){
                    return false;
                }

                index = foundIndex + 1;
            }

            return true;
        }

        return false;
    }

    /**
     * Helper method that returns the index of the single range within the
     * instance for which the provided single range is fully contained.
     * @param range the range for which to find the index of.
     * @param start the index at which to start the progressive linear search.
     * @return the index of the single range within the instance for which the
     * provided single range is fully contained.
     */
    private int containsAt(SingleRange<E> range, int start){
        for(int i = start; i < SINGLE_RANGES.size(); i++){
            if(SINGLE_RANGES.get(i).contains(range)){
                return i;
            }
        }

        return -1;
    }

    /**
     * Returns a range of all values within the instance that occur before the
     * provided value.
     * @param val the value to compare.
     * @return a range of all values within the instance that occur before the
     * provided value.
     */
    public MultiRange<E> before(E val){
        if(val == null){
            throw new NullPointerException("argument val is null");
        }

        if(compare(val, max()) > 0){
            return this;
        } else if(compare(val, min()) <= 0){
            return null;
        }

        final ArrayList<SingleRange<E>> RESULT = new ArrayList<>();
        for(SingleRange<E> singleRange : SINGLE_RANGES){
            SingleRange<E> newRange = singleRange.before(val);

            //Once we hit a null, then we know that nothing after this range
            // will be valid.
            if(newRange == null){
                break;
            }

            //Add the new range to our result list.
            RESULT.add(newRange);

            //If the range was modified, then we know that nothing after this
            // range will be valid.
            if(!newRange.equals(singleRange)){
                break;
            }
        }

        return RESULT.isEmpty() ? null : new MultiRange<>(RESULT);
    }

    /**
     * Returns a range of all values within the instance that occur after the
     * provided value.
     * @param val the value to compare.
     * @return a range of all values within the instance that occur after the
     * provided value.
     */
    public MultiRange<E> after(E val){
        if(val == null){
            throw new NullPointerException("argument val is null");
        }

        if(compare(val, min()) < 0){
            return this;
        } else if(compare(val, max()) >= 0){
            return null;
        }

        final ArrayList<SingleRange<E>> RESULT = new ArrayList<>();
        for(int i = SINGLE_RANGES.size() - 1; i > -1; i--){
            SingleRange<E> newRange = SINGLE_RANGES.get(i).after(val);

            //Once we hit a null, then we know that nothing before this range
            // will be valid.
            if(newRange == null){
                break;
            }

            //Add the new range to our result list.
            RESULT.add(newRange);

            //If the range was modified, then we know that nothing before this
            // range will be valid.
            if(!newRange.equals(SINGLE_RANGES.get(i))){
                break;
            }
        }

        return RESULT.isEmpty() ? null : new MultiRange<>(RESULT);
    }

    /**
     * Converts the instance into a multi-range.
     * @return a multi-range representing the instance.
     */
    protected MultiRange<E> toMulti(){
        return this;
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
        return obj == this || (obj instanceof MultiRange<?> range
                && range.SINGLE_RANGES.equals(SINGLE_RANGES));
    }

    /**
     * Returns a string of the values representing the range of the instance.
     * @return a string of the values representing the range of the instance.
     */
    @Override
    public String toString(){
        final StringBuilder BUILDER = new StringBuilder("[");

        if(SINGLE_RANGES.size() > 0){
            BUILDER.append(SINGLE_RANGES.get(0));
            for(int i = 1; i < SINGLE_RANGES.size(); i++){
                BUILDER.append(", ").append(SINGLE_RANGES.get(i));
            }
        }

        BUILDER.append("]");

        return BUILDER.toString();
    }

    /**
     * Returns an {@code Iterator} over the possibilities of the range.
     * @return an {@code Iterator} over the possibilities of the range.
     */
    @Override
    public Iterator<E> iterator() {
        return new Iterator<>() {
            private final Iterator<SingleRange<E>> OUTER_ITERATOR = SINGLE_RANGES.iterator();
            private Iterator<E> innerIterator = null;

            /**
             * Returns {@code true} if the iteration has more elements. (In other words, returns
             * {@code true} if next would return an element rather than throwing an exception.)
             * @return {@code true}, if the iteration has more elements.
             */
            @Override
            public boolean hasNext() {
                return innerIterator == null || innerIterator.hasNext() || OUTER_ITERATOR.hasNext();
            }

            /**
             * Returns the next element in the iteration.
             * @return the next element in the iteration.
             */
            @Override
            public E next() {
                if(innerIterator == null || !innerIterator.hasNext()){
                    innerIterator = OUTER_ITERATOR.next().iterator();
                }

                return innerIterator.next();
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