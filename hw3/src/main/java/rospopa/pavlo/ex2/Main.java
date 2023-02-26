package rospopa.pavlo.ex2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) throws IOException {
        var reader = new BufferedReader(new InputStreamReader(System.in));

        var input = reader.readLine().split(" ");
        var n = Integer.parseInt(input[0]);
        var x = Integer.parseInt(input[1]);

        input = reader.readLine().split(" ");
        var arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = Integer.parseInt(input[i]);
        }

        var num = 0;
        var sum = 0;
        var l = 0;
        for (int k = 0; k < n; k++) {
            sum += arr[k];
            while (sum > x) {
                sum -= arr[l];
                l++;
            }
            if (sum == x) {
                num++;
            }
        }

        System.out.println(num);
    }
}
