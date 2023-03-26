package rospopa.pavlo.ex3;

public class MyCircularDeque {
    private final int k;
    private final int capacity;
    private final int[] arr;
    private int length;
    private int head;
    private int tail;

    public MyCircularDeque(int k) {
        this.k = k;
        // internal capacity
        capacity = k + 1;
        arr = new int[capacity];
        length = 0;
        // pointing to sentinel head
        head = 0;
        // pointing to
        tail = 1;
    }

    public boolean insertFront(int value) {
        if (isFull()) {
            return false;
        }

        head--;
        if (head < 0) {
            head = capacity - 1;
        }
        arr[head] = value;
        length++;
        return true;
    }

    public boolean insertLast(int value) {
        if (isFull()) {
            return false;
        }

        arr[tail] = value;
        length++;
        tail = (tail + 1) % capacity;
        return true;
    }

    public boolean deleteFront() {
        if (isEmpty()) {
            return false;
        }

        head = (head + 1) % capacity;
        length--;
        return true;
    }

    public boolean deleteLast() {
        if (isEmpty()) {
            return false;
        }
        tail--;
        if (tail < 0) {
            tail = capacity - 1;
        }
        length--;
        return true;
    }

    public int getFront() {
        if (isEmpty()) {
            return -1;
        }
        return arr[head];
    }

    public int getRear() {
        if (isEmpty()) {
            return -1;
        }
        return arr[tail - 1];
    }

    public boolean isEmpty() {
        return length == 0;
    }

    public boolean isFull() {
        return length == k;
    }
}
