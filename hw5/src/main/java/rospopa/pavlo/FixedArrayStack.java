package rospopa.pavlo;

public class FixedArrayStack<T> implements Stack<T> {
    private int length;
    private final int capacity;
    private final T[] arr;

    public FixedArrayStack(T[] arr) {
        this.arr = arr;
        capacity = arr.length;
    }

    @Override
    public void push(T e) {
        if (length == capacity) {
            throw new RuntimeException("exceeds stack capacity");
        }

        arr[length++] = e;
    }

    @Override
    public T pop() {
        if (isEmpty()) {
            throw new RuntimeException("empty stack");
        }
        return arr[--length];
    }

    @Override
    public boolean isEmpty() {
        return length == 0;
    }

    @Override
    public int getLength() {
        return length;
    }

    @Override
    public T top() {
        if (isEmpty()) {
            return null;
        }
        return arr[length - 1];
    }
}
