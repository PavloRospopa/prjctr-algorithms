package rospopa.pavlo;

public interface Stack<T> {
    void push(T e);

    T pop();

    boolean isEmpty();

    int getLength();

    T top();
}
