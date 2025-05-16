package ranges;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A class representing a range of values. It establishes minimum and maximum
 * end points for the instance - both inclusive. As such, the data structure
 * is capable of having O(1) access to any value within the range, without
 * having to actually store each value.
 * @author Michael Dillinger
 * @since 0.1.0
 */
public abstract class Range<E> implements Iterable<E> {
    protected final Orderator<E> ORDERATOR;

    /**
     * Instantiates an instance of Range using a package-private method, so
     * that the class may only be inherited from within the same package.
     * @param ORDERATOR the orderator used by the range.
     */
    Range(final Orderator<E> ORDERATOR){
        this.ORDERATOR = ORDERATOR;
    }

    /**
     * Returns the number of elements within the instance.
     * @return the number of elements within the instance, if that value is
     * less than {@code Integer.MAX_VALUE}. Else, {@code Integer.MAX_VALUE}.
     */
    public abstract int size();

    /**
     * Returns whether the instance contains any elements.
     * @return {@code true}, if this instance contains no elements. Else,
     * {@code false}.
     */
    public final boolean isEmpty(){
        return size() == 0;
    }

    /**
     * Returns {@code true} if this instance contains the specified element.
     * Note that the method uses {@code Objects.equals(val, get(i))} in order
     * to check for the value to be searched for, and not the address of the
     * objects themselves being compared.
     * @param val the element to be searched for.
     * @return {@code true}, if this instance contains the specified element.
     * Else, {@code false}.
     */
    public abstract boolean contains(Object val);

    /**
     * Returns {@code true} if the instance contains all specified elements.
     * Note that the method uses {@code contains(Object val)} in order to
     * check for the values to be searched for.
     * @param vals the elements to be searched for.
     * @return {@code true}, if this instance contains all specified elements.
     * Else, {@code false}.
     */
    public final boolean containsAll(Range<?> vals){
        for(Object val : vals){
            if(!contains(val)){
                return false;
            }
        }

        return true;
    }

    /**
     * Returns a randomly generated value within the instance.
     * @return a randomly generated value within the instance.
     */
    public final E random(){
        return getByLong(randomIndexByLong());
    }

    /**
     * Helper method for internal calculations only that returns a randomly
     * generated index from within the instance. This differs from {@code
     * randomIndex()}, which is capped off at {@code Integer.MAX_VALUE} in
     * order to align with the Java-standard data structures.
     * @return a randomly generated index from within the instance.
     */
    //package-private
    final long randomIndexByLong(){
        return new Random().nextLong(possibilities());
    }

    /**
     * Returns the initial element in the instance.
     * @return the initial element in the instance.
     */
    public final E initial(){
        return get(0);
    }

    /**
     * Returns the last element in the instance.
     * @return the last element in the instance.
     */
    public final E last(){
        return get(size() - 1);
    }

    /**
     * Returns the nth element within the range, with the initial index being
     * {@code 0}.
     * @param index the index of the element to be returned.
     * @return the nth element within the range.
     */
    public final E get(int index){
        return getByLong(index);
    }

    /**
     * Helper method for internal calculations only that returns the value
     * at the provided index within the range. This differs from {@code
     * get()}, which is capped off at {@code Integer.MAX_VALUE} in order to
     * align with the Java-standard data structures.
     * @return the true number of possibilities within the range.
     */
    //package-private
    abstract E getByLong(long index);

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
    public abstract int indexOf(Object val);

    /**
     * Returns the index of the last occurrence of the provided element within
     * the instance. Note that the method uses
     * {@code Objects.equals(val, get(i))} in order to check for the value to
     * be searched for, and not the address of the objects themselves being
     * compared.
     * @param val the element to be searched for.
     * @return the index of the last occurrence of the provided element within
     * the instance, if it exists. Else, {@code -1}.
     */
    public final int lastIndexOf(Object val){
        return indexOf(val);
    }

    /**
     * Returns a randomly generated index from within the instance.
     * @return a randomly generated index from within the instance.
     */
    public final int randomIndex(){
        return new Random().nextInt(size());
    }

    /**
     * Helper method that returns the comparison of the two provided values
     * using the {@code Comparator} of the instance.
     * @return the comparison of the two provided values.
     */
    protected final int compare(E a, E b){
        return this.ORDERATOR.compare(a, b);
    }

    /**
     * Returns the integer value representing the instance.
     * @return the integer value representing the instance.
     */
    protected final int intValueOf(E val){
        return this.ORDERATOR.intValueOf(val);
    }

    /**
     * Returns the long value representing the instance.
     * @return the long value representing the instance.
     */
    protected final long longValueOf(E val){
        return this.ORDERATOR.longValueOf(val);
    }

    /**
     * Returns the result representing the provided value having been
     * incremented by the provided number of minimum increments.
     * @param val the value to be shifted.
     * @param increments the number of minimum increments to increment the
     * provided value.
     * @return the result representing the provided value having been
     * incremented by the provided number of minimum increments.
     */
    protected final E addTo(E val, long increments){
        return this.ORDERATOR.addTo(val, increments);
    }

    /**
     * Returns the result representing the provided value having been
     * decremented by the provided number of minimum increments.
     * @param val the value to be shifted.
     * @param increments the number of minimum increments to decrement the
     * provided value.
     * @return the result representing the provided value having been
     * decremented by the provided number of minimum increments.
     */
    protected final E subtractFrom(E val, long increments){
        return this.ORDERATOR.subtractFrom(val, increments);
    }

    /**
     * Helper method for internal calculations only that returns the true
     * number of possibilities within the range. This differs from {@code
     * size()}, which is capped off at {@code Integer.MAX_VALUE} in order to
     * align with the Java-standard data structures.
     * @return the true number of possibilities within the range.
     */
    //package-private
    abstract long possibilities();

    /**
     * Returns the minimum value within the range.
     * @return the minimum value within the range.
     */
    public abstract E min();

    /**
     * Returns the maximum value within the range.
     * @return the maximum value within the range.
     */
    public abstract E max();

    /**
     * Returns a new range contracted around a randomly generated value within
     * the range.
     * @return a new range contracted around a randomly generated value within
     * the range.
     */
    public final SingleRange<E> contract(){
        E val = random();

        return new SingleRange<>(val, val, ORDERATOR);
    }

    /**
     * Returns the value having been bound to the range.
     * @param val the value to be bound.
     * @return the value having been bound to the range.
     */
    public abstract E bound(E val);

    /**
     * Returns whether the entirety of the provided range occurs within the
     * range of the instance.
     * @param range the range to be bounds checked.
     * @return {@code true} if the entirety of the provided range occurs
     * within the range of the instance.
     * Else, {@code false}.
     */
    public abstract boolean contains(Range<E> range);

    /**
     * Returns a range of all values within the instance that occur before the
     * provided value.
     * @param val the value to compare.
     * @return a range of all values within the instance that occur before the
     * provided value.
     */
    public abstract Range<E> before(E val);

    /**
     * Returns a range of all values within the instance that occur after the
     * provided value.
     * @param val the value to compare.
     * @return a range of all values within the instance that occur after the
     * provided value.
     */
    public abstract Range<E> after(E val);

    /**
     * Converts the instance into a multi-range.
     * @return a multi-range representing the instance.
     */
    protected abstract MultiRange<E> toMulti();

    /**
     * Returns whether there is a range of intersecting values between the
     * instance and the comparison.
     * @param comp comparison for which to check intersection.
     * @return {@code true} is the comparison is not {@code null}, and there
     * is a range of intersecting values. Else, {@code false}.
     */
    public final boolean hasIntersection(Range<E> comp){
        return hasIntersection(this.toMulti(), comp.toMulti());
    }

    /**
     * Returns whether there is a range of intersecting values between the
     * two single ranges.
     * @param a the first range for which to check intersection.
     * @param b the second range for which to check intersection.
     * @return {@code true} is the neither range is {@code null}, and there
     * is a range of intersecting values. Else, {@code false}.
     */
    private boolean hasIntersection(SingleRange<E> a, SingleRange<E> b){
        return a != null && b != null && compare(a.MIN, b.MAX) <= 0
                && compare(b.MIN, a.MAX) <= 0;
    }

    /**
     * Returns whether there is a range of intersecting values between the
     * two multi-ranges.
     * @param a the first range for which to check intersection.
     * @param b the second range for which to check intersection.
     * @return {@code true} is the neither range is {@code null}, and there
     * is a range of intersecting values. Else, {@code false}.
     */
    private boolean hasIntersection(MultiRange<E> a, MultiRange<E> b){
        if(a == null || b == null){
            return false;
        }

        for(SingleRange<E> x : a.SINGLE_RANGES){
            for(SingleRange<E> y : b.SINGLE_RANGES){
                if(hasIntersection(x, y)){
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Returns a range of intersecting values between the instance and the
     * comparison. If there are no intersecting values - including if the
     * comparison is null - then {@code null} is returned.
     * @param range the range with which to intersect.
     * @return range of all intersecting values.
     */
    public final Range<E> intersect(Range<E> range){
        return intersect(this.toMulti(), range.toMulti());
    }

    /**
     * Returns a range of intersecting values between the two single ranges.
     * If there are no intersecting values - including if either range is null
     * - then {@code null} is returned.
     * @param a the first range with which to intersect.
     * @param b the second range with which to intersect.
     * @return range of all intersecting values.
     */
    private SingleRange<E> intersect(SingleRange<E> a, SingleRange<E> b){
        if(!hasIntersection(a, b)){
            return null;
        }

        //Get the larger minimum value
        E min = compare(a.MIN, b.MIN) > 0 ? a.MIN : b.MIN;

        //Get the smaller maximum value
        E max = compare(a.MAX, b.MAX) < 0 ? a.MAX : b.MAX;

        return new SingleRange<>(min, max, ORDERATOR);
    }

    /**
     * Returns a range of intersecting values between the two multi-ranges.
     * If there are no intersecting values - including if either range is null
     * - then {@code null} is returned.
     * @param a the first range with which to intersect.
     * @param b the second range with which to intersect.
     * @return range of all intersecting values.
     */
    private MultiRange<E> intersect(MultiRange<E> a, MultiRange<E> b){
        final ArrayList<SingleRange<E>> RESULT = new ArrayList<>();

        for(SingleRange<E> x : a.SINGLE_RANGES){
            for(SingleRange<E> y : b.SINGLE_RANGES){
                SingleRange<E> z = intersect(x, y);
                if(z != null){
                    RESULT.add(z);
                }
            }
        }

        return RESULT.isEmpty() ? null : new MultiRange<>(RESULT);
    }

    /**
     * Returns a range representing the union of the instance and the provided
     * range after having been merged together.
     * @param range the range with which to merge.
     * @return union of all merged values.
     */
    public final Range<E> union(Range<E> range){
        return union(this.toMulti(), range.toMulti());
    }

    /**
     * Returns a range representing the union of the two single ranges after
     * having been merged together. If there are no intersecting values -
     * including if the comparison is null - then {@code null} is returned.
     * @param a the first range with which to merge.
     * @param b the second range with which to merge.
     * @return union of all merged values.
     */
    private SingleRange<E> union(SingleRange<E> a, SingleRange<E> b){
        if(!hasIntersection(a, b)){
            return null;
        }

        //Get the larger minimum value
        E min = compare(a.MIN, b.MIN) < 0 ? a.MIN : b.MIN;

        //Get the smaller maximum value
        E max = compare(a.MAX, b.MAX) > 0 ? a.MAX : b.MAX;

        return new SingleRange<>(min, max, ORDERATOR);
    }

    /**
     * Returns a range representing the union of the two multi-ranges after
     * having been merged together. If there are no intersecting values -
     * including if the comparison is null - then {@code null} is returned.
     * @param a the first range with which to merge.
     * @param b the second range with which to merge.
     * @return union of all merged values.
     */
    private MultiRange<E> union(MultiRange<E> a, MultiRange<E> b){
        ArrayList<SingleRange<E>> join = new ArrayList<>();
        join.addAll(a.SINGLE_RANGES);
        join.addAll(b.SINGLE_RANGES);

        //Note that due to merging in the constructor, that we can just pass
        // the combined list into it, and the merging will occur there.
        return new MultiRange<>(join);
    }

    /**
     * Returns an arraylist of ranges representing the merging of all of the
     * provided ranges.
     * @param ranges the ranges to merge.
     * @return ranges of all merged values.
     */
    protected final ArrayList<SingleRange<E>> union(List<SingleRange<E>> ranges){
        if(ranges == null){
            return null;
        }

        final ArrayList<SingleRange<E>> RESULT = new ArrayList<>();

        final ArrayList<SingleRange<E>> COPY = new ArrayList<>();
        for(SingleRange<E> range : ranges){
            if(range != null){
                COPY.add(range);
            }
        }

        //We need to order them here to ensure that we always catch the next
        // possible intersection. Otherwise, we can have intersections that
        // are not caught (i.e., single ranges in the order 3 - 12, 15 - 18,
        // 10 - 16, and 4 - 12 would result in two single ranges of 3 - 12 and
        // 10 - 18, rather than a one single range of 3 - 18).
        COPY.sort((a, b) -> compare(a.MIN, b.MIN));

        while(!COPY.isEmpty()){
            SingleRange<E> val = COPY.remove(0);

            //Due to the list being in sorted order, we only need to merge
            // values until there is no intersection. Once no intersection
            // is possible with the next element, or the list is empty, then
            // we can end this inner loop.
            while(!COPY.isEmpty() && val.hasIntersection(COPY.get(0))){
                val = union(val, COPY.remove(0));
            }

            RESULT.add(val);
        }

        //Ensures that if two ranges do not have any additional possible
        // values between them, that they then merge, as there are no values
        // between the max of the lower range and the min of the higher
        // range - and are thus essentially a single range (i.e., two ranges
        // of [0, 10] and [11, 20] have no further possible values between
        // them, and will thus merge to become a single range of [0, 20]).
        for(int i = 0; i < RESULT.size() - 1;){
            if(compare(addTo(RESULT.get(i).MAX, 1), RESULT.get(i + 1).MIN) == 0){
                RESULT.set(i, new SingleRange<>(
                        RESULT.get(i).MIN,
                        RESULT.get(i + 1).MAX,
                        ORDERATOR
                ));
                RESULT.remove(i + 1);
            } else {
                i++;
            }
        }

        return RESULT;
    }
}
