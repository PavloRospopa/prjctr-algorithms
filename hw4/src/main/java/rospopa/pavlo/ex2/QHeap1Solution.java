package rospopa.pavlo.ex2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public class QHeap1Solution {
    private static final int MAX_NUMBER_OF_OPERATIONS = 100000;

    public static void main(String[] args) throws IOException {
        var reader = new BufferedReader(new InputStreamReader(System.in));
        var q = Integer.parseInt(reader.readLine());
        var heap = new QHeap(MAX_NUMBER_OF_OPERATIONS);
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

    static class QHeap {
        MinIntHeap heap;
        Set<Integer> deleted;

        QHeap(int capacity) {
            heap = new MinIntHeap(capacity);
            deleted = new HashSet<>();
        }

        void insert(int v) {
            if (deleted.contains(v)) {
                deleted.remove(v);
            } else {
                heap.insert(v);
            }
        }

        void delete(int v) {
            deleted.add(v);
        }

        int peek() {
            while (deleted.contains(heap.peek())) {
                deleted.remove(heap.min());
            }

            return heap.peek();
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

        int peek() {
            return arr[1];
        }

        int min() {
            var min = arr[1];
            swap(1, size--);
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
            while (i > 1 && arr[i / 2] > arr[i]) {
                swap(i, i / 2);
                i = i / 2;
            }
        }

        private void swap(int i, int j) {
            var temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }
    }
}
