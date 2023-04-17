package rospopa.pavlo.ex1;

class MyHashMap {
    private final double resizeFactor;
    private final double loadFactor;
    private final int a;
    private final int b;
    private Entry[] arr;
    private int length;
    private int capacity;

    public MyHashMap() {
        resizeFactor = 1.5;
        loadFactor = 0.75;
        capacity = 10;
        arr = new Entry[capacity];
        a = 1009;
        b = 9973;
    }

    public void put(int key, int value) {
        if (getLength() + 1.0 / capacity > loadFactor) {
            increaseCapacity();
        }

        var index = hash(key) % capacity;
        var head = arr[index];
        var entry = findEntry(key, head);
        if (entry != null) {
            entry.value = value;
        } else {
            arr[index] = new Entry(key, value, head);
            length++;
        }
    }

    public int get(int key) {
        var index = hash(key) % capacity;
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
        var index = hash(key) % capacity;
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
        length--;
    }

    int getLength() {
        return length;
    }

    private int hash(int key) {
        return a * key + b;
    }

    private void increaseCapacity() {
        capacity = (int) (capacity * resizeFactor);
        var newArr = new Entry[capacity];
        for (Entry head : arr) {
            if (head == null) {
                continue;
            }

            var index = hash(head.key) % capacity;
            newArr[index] = join(newArr[index], head);
        }
        arr = newArr;
    }

    private Entry join(Entry head, Entry tail) {
        if (head == null) {
            return tail;
        }

        var entry = head;
        while (entry.next != null) {
            entry = entry.next;
        }
        entry.next = tail;
        return head;
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
