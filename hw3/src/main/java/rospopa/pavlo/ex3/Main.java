package rospopa.pavlo.ex3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    /**
     * Solution for <a href="https://codeforces.com/problemset/problem/279/B?locale=en">B. Books problem</a>
     */
    public static void main(String[] args) throws IOException {
        var reader = new BufferedReader(new InputStreamReader(System.in));

        var input = reader.readLine().split(" ");
        var n = Integer.parseInt(input[0]);
        var t = Integer.parseInt(input[1]);

        input = reader.readLine().split(" ");
        var arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = Integer.parseInt(input[i]);
        }

        var maxBooksCount = 0;
        var booksCount = 0;
        var timeSum = 0;
        var l = 0;
        for (int k = 0; k < n; k++) {
            timeSum += arr[k];
            booksCount++;

            while (timeSum > t) {
                timeSum -= arr[l];
                booksCount--;
                l++;
            }

            if (booksCount > maxBooksCount) {
                maxBooksCount = booksCount;
            }
        }

        System.out.println(maxBooksCount);
    }
}
