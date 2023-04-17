package rospopa.pavlo.ex1;

class MyHashMap {
    private final int resizeFactor;
    private final double loadFactor;
    private final int a;
    private final int b;
    private Entry[] arr;
    private int length;

    public MyHashMap() {
        resizeFactor = 2;
        loadFactor = 0.75;
        arr = new Entry[10];
        a = (int) (Math.random() * 100);
        b = (int) (Math.random() * 100);
    }

    public void put(int key, int value) {
        if ((double) getLength() / arr.length > loadFactor) {
            increaseCapacity();
        }

        var index = hash(key) % arr.length;
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
        var index = hash(key) % arr.length;
        var head = arr[index];
        var entry = findEntry(key, head);
        return entry != null ? entry.value : -1;
    }

    private Entry findEntry(int key, Entry head) {
        Entry result = null;
        var entry = new Entry(-1, -1, head);
        while (entry.next != null) {
            entry = entry.next;
            if (entry.key == key) {
                result = entry;
                break;
            }
        }

        return result;
    }

    public void remove(int key) {
        var index = hash(key) % arr.length;
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
        var newArr = new Entry[arr.length * resizeFactor];
        System.arraycopy(arr, 0, newArr, 0, arr.length);
        arr = newArr;
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
