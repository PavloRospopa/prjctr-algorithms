package rospopa.pavlo.ex2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class QHeap1Solution {
    private static final int MAX_HEAP_CAPACITY = 100000;

    public static void main(String[] args) throws IOException {
        var reader = new BufferedReader(new InputStreamReader(System.in));
        var q = Integer.parseInt(reader.readLine());
        var heap = new MinIntHeap(MAX_HEAP_CAPACITY);
        for (int i = 0; i < q; i++) {
            var input = reader.readLine().split(" ");
            var operator = input[0];
            switch (operator) {
                case "1":
                    heap.insert(Integer.parseInt(input[1]));
                    break;
                case "2":
                    heap.delete(Integer.parseInt(input[1]));
                    break;
                case "3":
                    System.out.println(heap.peek());
                    break;
            }
        }
    }

    static class MinIntHeap {
        int[] arr;
        int size;

        MinIntHeap(int capacity) {
            arr = new int[capacity + 1];
        }

        void insert(int v) {
            arr[++size] = v;
            siftup(size);
        }

        void delete(int v) {
            del(find(1, v));
        }

        private int find(int i, int v) {
            if (arr[i] == v) {
                return i;
            }

            if (arr[2 * i] <= v) {
                find(2 * i, v);
            }
            if (arr[2 * i + 1] <= v) {
                find(2 * i + 1, v);
            }

            return -1;
        }

        private void del(int i) {
            arr[i] = arr[size--];
            siftdown(i);
        }

        int peek() {
            return arr[1];
        }

        int pop() {
            var min = peek();

            arr[1] = arr[size--];
            siftdown(1);
            return min;
        }

        private void siftdown(int i) {
            while (2 * i <= size) {
                var j = i;
                if (arr[2 * i] < arr[j]) {
                    j = 2 * i;
                } else if (2 * i + 1 <= size && arr[2 * i + 1] < arr[j]) {
                    j = 2 * i + 1;
                }
                if (j == i) {
                    break;
                }
                swap(i, j);
                i = j;
            }
        }

        private void siftup(int i) {
            var p = i / 2;
            while (arr[p] > arr[i]) {
                swap(i, p);
                i = p;
                p = i / 2;
            }
        }

        private void swap(int i, int j) {
            var temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }
    }
}