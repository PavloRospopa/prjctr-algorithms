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
    private Entry[] set;
    private int length;

    public RandomizedSet() {
        random = new Random();
        loadFactor = 0.5;
        resizeFactor = 2;
        loadFactorDividedByResizeFactor = loadFactor / resizeFactor;
        initialCapacity = 16;
        capacity = initialCapacity;
        precalculateCapacityRelatedFields();
        set = new Entry[capacity];
    }

    private void precalculateCapacityRelatedFields() {
        capacityMultipliedByLoadFactor = capacity * loadFactor;
        capacityDividedByResizeFactor = (double) capacity / resizeFactor;
    }

    public boolean insert(int val) {
        var index = findIndex(val);
        var entry = set[index];
        if (entry != null && !entry.removed) {
            return false;
        }

        set[index] = new Entry(val);
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
        set = new Entry[capacity];
        for (Entry entry : oldSet) {
            if (entry != null && !entry.removed) {
                insert(entry.val);
            }
        }
    }

    public boolean remove(int val) {
        var index = findIndex(val);
        var entry = set[index];
        if (entry == null || entry.removed) {
            return false;
        }

        entry.removed = true;
        length--;

        if (capacity > initialCapacity && length / capacityDividedByResizeFactor < loadFactorDividedByResizeFactor) {
            resize((int) capacityDividedByResizeFactor);
        }

        return true;
    }

    public int getRandom() {
        for (var i = random.nextInt(capacity); ; i = random.nextInt(capacity)) {
            var entry = set[i];
            if (entry != null && !entry.removed) {
                return entry.val;
            }
        }
    }

    private int hash(int val) {
        long unsigned = val & 0xffffffffL;
        return (int) (unsigned % capacity);
    }

    // returns insertion index for entry if it does not exist in set.
    private int findIndex(int val) {
        var index = hash(val);
        while (set[index] != null) {
            if (set[index].val == val) {
                return index;
            }
            index = (index + 1) % capacity;
        }
        return index;
    }

    private static class Entry {
        private final int val;
        private boolean removed;

        public Entry(int val) {
            this.val = val;
        }
    }
}
