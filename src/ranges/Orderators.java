package ranges;

/**
 * A utility class intended to be store the standard {@code Orderator}
 * instance for built-in Java classes.
 * @author Michael Dillinger
 * @since 0.1.0
 */
public class Orderators {
    public static final Orderator<Byte> BYTE = new Orderator<>() {
        @Override
        public int intValueOf(Byte val) {
            return val;
        }

        @Override
        public long longValueOf(Byte val) {
            return val;
        }

        @Override
        public Byte addTo(Byte val, long steps) {
            return (byte) (val + steps);
        }

        @Override
        public Byte subtractFrom(Byte val, long steps) {
            return (byte) (val - steps);
        }

        @Override
        public int compare(Byte o1, Byte o2) {
            return Byte.compare(o1, o2);
        }
    };

    public static final Orderator<Short> SHORT = new Orderator<>() {
        @Override
        public int intValueOf(Short val) {
            return val;
        }

        @Override
        public long longValueOf(Short val) {
            return val;
        }

        @Override
        public Short addTo(Short val, long steps) {
            return (short) (val + steps);
        }

        @Override
        public Short subtractFrom(Short val, long steps) {
            return (short) (val - steps);
        }

        @Override
        public int compare(Short o1, Short o2) {
            return Short.compare(o1, o2);
        }
    };

    public static final Orderator<Integer> INTEGER = new Orderator<>() {
        @Override
        public int intValueOf(Integer val) {
            return val;
        }

        @Override
        public long longValueOf(Integer val) {
            return val;
        }

        @Override
        public Integer addTo(Integer val, long steps) {
            return (int) (val + steps);
        }

        @Override
        public Integer subtractFrom(Integer val, long steps) {
            return (int) (val - steps);
        }

        @Override
        public int compare(Integer o1, Integer o2) {
            return Integer.compare(o1, o2);
        }
    };

    public static final Orderator<Long> LONG = new Orderator<>() {
        @Override
        public int intValueOf(Long val) {
            return val.intValue();
        }

        @Override
        public long longValueOf(Long val) {
            return val;
        }

        @Override
        public Long addTo(Long val, long steps) {
            return val + steps;
        }

        @Override
        public Long subtractFrom(Long val, long steps) {
            return val - steps;
        }

        @Override
        public int compare(Long o1, Long o2) {
            return Long.compare(o1, o2);
        }
    };

    public static final Orderator<Character> CHARACTER = new Orderator<>() {
        @Override
        public int intValueOf(Character val) {
            return val;
        }

        @Override
        public long longValueOf(Character val) {
            return val;
        }

        @Override
        public Character addTo(Character val, long steps) {
            return (char) (val + steps);
        }

        @Override
        public Character subtractFrom(Character val, long steps) {
            return (char) (val - steps);
        }

        @Override
        public int compare(Character o1, Character o2) {
            return Character.compare(o1, o2);
        }
    };

    /**
     * Returns an {@code Orderator} for the {@code Float} data type with the
     * provided step value.
     * @param stepValue the step value for the {@code Orderator}.
     * @return an {@code Orderator} for the {@code Float} data type with the
     * provided step value.
     */
    public static Orderator<Float> ofFloat(float stepValue){
        return new Orderator<>() {
            private final float STEP_VALUE = stepValue;

            @Override
            public int intValueOf(Float val) {
                return val.intValue();
            }

            @Override
            public long longValueOf(Float val) {
                return val.longValue();
            }

            @Override
            public Float addTo(Float val, long steps) {
                return val + (steps * STEP_VALUE);
            }

            @Override
            public Float subtractFrom(Float val, long steps) {
                return val - (steps * STEP_VALUE);
            }

            @Override
            public int compare(Float o1, Float o2) {
                return Float.compare(o1, o2);
            }
        };
    }

    /**
     * Returns an {@code Orderator} for the {@code Double} data type with the
     * provided step value.
     * @param stepValue the step value for the {@code Orderator}.
     * @return an {@code Orderator} for the {@code Double} data type with the
     * provided step value.
     */
    public static Orderator<Double> ofDouble(double stepValue){
        return new Orderator<>() {
            private final double STEP_VALUE = stepValue;

            @Override
            public int intValueOf(Double val) {
                return val.intValue();
            }

            @Override
            public long longValueOf(Double val) {
                return val.longValue();
            }

            @Override
            public Double addTo(Double val, long steps) {
                return val + (steps * STEP_VALUE);
            }

            @Override
            public Double subtractFrom(Double val, long steps) {
                return val - (steps * STEP_VALUE);
            }

            @Override
            public int compare(Double o1, Double o2) {
                return Double.compare(o1, o2);
            }
        };
    }

    /**
     * Returns an {@code Orderator} for the provided enum class.
     * @param cls the enum class for which to create the {@code Orderator}
     * for.
     * @return an {@code Orderator} for the provided enum class.
     * @param <E> the enum class for which to create the {@code Orderator}
     * for.
     */
    public static <E extends Enum<E>> Orderator<E> ofEnum(Class<E> cls){
        return new Orderator<>() {
            private final E[] VALUES = cls.getEnumConstants();

            @Override
            public int intValueOf(E val) {
                return val.ordinal();
            }

            @Override
            public long longValueOf(E val) {
                return val.ordinal();
            }

            @Override
            public E addTo(E val, long steps) {
                return VALUES[(int) (val.ordinal() + steps)];
            }

            @Override
            public E subtractFrom(E val, long steps) {
                return VALUES[(int) (val.ordinal() - steps)];
            }

            @Override
            public int compare(E o1, E o2) {
                return o1.compareTo(o2);
            }
        };
    }
}
