package rospopa.pavlo.ex2;

import java.util.Random;

class RandomizedSet {
    private final int initialCapacity;
    private final double loadFactor;
    private final int resizeFactor;
    private final Random random;
    private Integer[] set;
    private int capacity;
    private int length;

    public RandomizedSet() {
        initialCapacity = 100;
        loadFactor = 0.5;
        resizeFactor = 2;
        capacity = initialCapacity;
        set = new Integer[capacity];
        random = new Random();
    }

    public boolean insert(int val) {
        var index = find(val);
        if (set[index] != null) {
            return false;
        }

        set[index] = val;
        length++;

        if (length > capacity * loadFactor) {
            resize(resizeFactor * capacity);
        }

        return true;
    }

    private void resize(int newCapacity) {
        var oldSet = set;
        capacity = newCapacity;
        set = new Integer[capacity];
        for (Integer val : oldSet) {
            if (val != null) {
                insert(val);
            }
        }
    }

    public boolean remove(int val) {
        var index = find(val);
        if (set[index] == null) {
            return false;
        }

        set[index] = null;
        length--;

        if (capacity > initialCapacity && length < capacity * loadFactor * loadFactor) {
            resize(capacity / resizeFactor);
        }

        return true;
    }

    public int getRandom() {
        var index = random.nextInt(capacity);
        while (set[index] == null) {
            index = (index + 1) % capacity;
        }
        return set[index];
    }

    private int hash(int val) {
        return val % capacity;
    }

    /**
     * @return internal index of val present in set or insertion index for the val.
     */
    private int find(int val) {
        var index = hash(val);
        while (set[index] != null) {
            if (set[index] == val) {
                return index;
            }
            index = (index + 1) % capacity;
        }
        return index;
    }
}
