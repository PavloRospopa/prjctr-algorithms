package rospopa.pavlo.ex5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class NumCounter {
    final int[] numCounters;

    NumCounter(int n) {
        this.numCounters = new int[n];
    }

    int get(int num) {
        return numCounters[num];
    }

    int increment(int num) {
        return ++numCounters[num];
    }

    int decrement(int num) {
        return --numCounters[num];
    }

    public static void main(String[] args) throws IOException {
        var reader = new BufferedReader(new InputStreamReader(System.in));

        var input = reader.readLine().split(" ");
        var n = Integer.parseInt(input[0]);
        var k = Integer.parseInt(input[1]);

        input = reader.readLine().split(" ");
        var arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = Integer.parseInt(input[i]);
        }

        var l = 0;
        var subarrayCounter = 0;
        var counter = new NumCounter(n);
        for (int r = 0; r < n; r++) {
            var rNum = arr[r];
            if (counter.increment(rNum) >= k) {
                subarrayCounter += n - r;

                while (counter.decrement(arr[l++]) >= k || counter.get(rNum) >= k) {
                    subarrayCounter += n - r;
                }
            }
        }
        System.out.println(subarrayCounter);
    }
}
