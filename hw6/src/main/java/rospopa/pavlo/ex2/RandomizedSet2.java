package rospopa.pavlo.ex2;

import java.util.ArrayList;
import java.util.Random;

class RandomizedSet2 {
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

    public RandomizedSet2() {
        random = new Random();
        loadFactor = 0.75;
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
        var index = hash(val);
        var head = set[index];
        var entry = findEntry(val, head);
        if (entry != null) {
            return false;
        }

        set[index] = new Entry(val, head);
        length++;

        if (length > capacityMultipliedByLoadFactor) {
            resize(resizeFactor * capacity);
        }

        return true;
    }

    private Entry findEntry(int val, Entry head) {
        Entry result = null;
        for (var entry = head; entry != null; entry = entry.next) {
            if (entry.val == val) {
                result = entry;
                break;
            }
        }
        return result;
    }

    private void resize(int newCapacity) {
        var oldSet = set;
        capacity = newCapacity;
        precalculateCapacityRelatedFields();
        set = new Entry[capacity];
        for (Entry entry : oldSet) {
            for (var e = entry; e != null; e = e.next) {
                insert(e.val);
            }
        }
    }

    public boolean remove(int val) {
        var result = false;
        var index = hash(val);
        var head = set[index];
        var sentinel = new Entry(-1, head);
        for (var entry = sentinel; entry != null; entry = entry.next) {
            var next = entry.next;
            if (next != null && next.val == val) {
                entry.next = next.next;
                result = true;
                break;
            }
        }

        set[index] = sentinel.next;
        length--;

        if (capacity > initialCapacity && length / capacityDividedByResizeFactor < loadFactorDividedByResizeFactor) {
            resize((int) capacityDividedByResizeFactor);
        }

        return result;
    }

    public int getRandom() {
        var index = random.nextInt(capacity);
        while (set[index] == null) {
            index = (index + 1) % capacity;
        }

        var entries = new ArrayList<Entry>();
        var entry = set[index];
        entries.add(entry);
        while (entry.next != null) {
            entries.add(entry.next);
            entry = entry.next;
        }

        var i = random.nextInt(entries.size());
        return entries.get(i).val;
    }

    private int hash(int val) {
        long unsigned = val & 0xffffffffL;
        return (int) (unsigned % capacity);
    }


    private static class Entry {
        private final int val;
        private Entry next;

        public Entry(int val, Entry next) {
            this.val = val;
            this.next = next;
        }
    }
}
