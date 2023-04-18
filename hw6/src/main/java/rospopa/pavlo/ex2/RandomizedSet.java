package rospopa.pavlo.ex2;

import java.util.Random;

class RandomizedSet {
    private final Random random;
    private final double loadFactor;
    private final int resizeFactor;
    private final double loadFactorDividedByResizeFactor;
    private final int initialCapacity;
    private int capacity;
    private double capacityMultipliedByLoadFactor;
    private double capacityDividedByResizeFactor;
    private Integer[] set;
    private int length;

    public RandomizedSet() {
        random = new Random();
        loadFactor = 0.5;
        resizeFactor = 2;
        loadFactorDividedByResizeFactor = loadFactor / resizeFactor;
        initialCapacity = 16;
        capacity = initialCapacity;
        precalculateCapacityRelatedFields();
        set = new Integer[capacity];
    }

    private void precalculateCapacityRelatedFields() {
        capacityMultipliedByLoadFactor = capacity * loadFactor;
        capacityDividedByResizeFactor = (double) capacity / resizeFactor;
    }

    public boolean insert(int val) {
        var index = findIndex(val);
        if (set[index] != null) {
            return false;
        }

        set[index] = val;
        length++;

        if (length > capacityMultipliedByLoadFactor) {
            resize(resizeFactor * capacity);
        }

        return true;
    }

    private void resize(int newCapacity) {
        var oldSet = set;
        capacity = newCapacity;
        precalculateCapacityRelatedFields();
        set = new Integer[capacity];
        for (Integer val : oldSet) {
            if (val != null) {
                insert(val);
            }
        }
    }

    public boolean remove(int val) {
        var index = findIndex(val);
        if (set[index] == null) {
            return false;
        }

        set[index] = null;
        length--;

        if (capacity > initialCapacity && length / capacityDividedByResizeFactor < loadFactorDividedByResizeFactor) {
            resize((int) capacityDividedByResizeFactor);
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
        long unsigned = val & 0xffffffffL;
        return (int) (unsigned % capacity);
    }

    /**
     * @return index of val present in set or insertion index for the val.
     */
    private int findIndex(int val) {
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
