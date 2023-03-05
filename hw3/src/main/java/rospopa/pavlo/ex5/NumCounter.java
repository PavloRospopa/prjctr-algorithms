package rospopa.pavlo.ex5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class NumCounter {
    final Map<Integer, Integer> numCounters;

    NumCounter() {
        this.numCounters = new HashMap<>();
    }

    int get(int num) {
        return numCounters.get(num);
    }

    int increment(int num) {
        var counter = numCounters.get(num);
        if (counter == null) {
            numCounters.put(num, 1);
            return 1;
        } else {
            counter = counter + 1;
            numCounters.put(num, counter);
            return counter;
        }
    }

    int decrement(int num) {
        var counter = numCounters.get(num);
        counter = counter - 1;
        numCounters.put(num, counter);
        return counter;
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
        var subarrayCounter = 0L;
        var counter = new NumCounter();
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
