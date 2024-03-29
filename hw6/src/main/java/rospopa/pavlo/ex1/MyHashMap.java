package rospopa.pavlo.ex1;

class MyHashMap {
    private final Entry[] arr;
    private final int capacity;

    public MyHashMap() {
        // the problem sets a constraint that there will be at most 10^4 calls to the map.
        // this allows us to estimate required map capacity in the worst case (10^4 / load_factor (0.75) = 53334)
        capacity = 53334;
        arr = new Entry[capacity];
    }

    public void put(int key, int value) {
        var index = hash(key);
        var head = arr[index];
        var entry = findEntry(key, head);
        if (entry != null) {
            entry.value = value;
        } else {
            arr[index] = new Entry(key, value, head);
        }
    }

    public int get(int key) {
        var index = hash(key);
        var head = arr[index];
        var entry = findEntry(key, head);
        return entry != null ? entry.value : -1;
    }

    private Entry findEntry(int key, Entry head) {
        Entry result = null;
        for (var entry = head; entry != null; entry = entry.next) {
            if (entry.key == key) {
                result = entry;
                break;
            }
        }
        return result;
    }

    public void remove(int key) {
        var index = hash(key);
        var head = arr[index];
        var sentinel = new Entry(-1, -1, head);
        for (var entry = sentinel; entry != null; entry = entry.next) {
            var next = entry.next;
            if (next != null && next.key == key) {
                entry.next = next.next;
                break;
            }
        }

        arr[index] = sentinel.next;
    }

    private int hash(int key) {
        return key % capacity;
    }

    static class Entry {
        private final int key;
        private int value;
        private Entry next;

        public Entry(int key, int value, Entry next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }
}
