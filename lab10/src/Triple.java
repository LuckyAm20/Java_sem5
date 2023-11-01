public class Triple<T, U, V> {
    private final T first;
    private final U second;
    private final V third;

    public Triple(T first, U second, V third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    public T getFirst() {
        return first;
    }

    public U getSecond() {
        return second;
    }

    public V getThird() {
        return third;
    }

    public T min() {
        if (first.getClass().equals(second.getClass()) && first.getClass().equals(third.getClass())) {
            if (first instanceof Comparable) {
                T min = first;
                if (((Comparable<T>) second).compareTo(min) < 0) {
                    min = (T) second;
                }
                if (((Comparable) third).compareTo(min) < 0) {
                    min = (T) third;
                }
                return min;
            } else {
                throw new RuntimeException("Impossible to perform min");
            }
        } else {
            throw new RuntimeException("Different types");
        }
    }

    public T max() {
        if (first.getClass().equals(second.getClass()) && first.getClass().equals(third.getClass())) {
            if (first instanceof Comparable) {
                T max = first;
                if (((Comparable) second).compareTo(max) > 0) {
                    max = (T) second;
                }
                if (((Comparable) third).compareTo(max) > 0) {
                    max = (T) third;
                }
                return max;
            } else {
                throw new RuntimeException("Impossible to perform max");
            }
        } else {
            throw new RuntimeException("Different types");
        }
    }

    public double mean() {
        if (first instanceof Number && second instanceof Number && third instanceof Number) {
            return (((Number) first).doubleValue() + ((Number) second).doubleValue() + ((Number) third).doubleValue()) / 3;
        } else {
            throw new RuntimeException("Impossible to perform mean");
        }
    }
}