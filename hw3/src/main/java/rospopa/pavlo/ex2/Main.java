package rospopa.pavlo.ex2;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        var scanner = new Scanner(System.in);
        var n = scanner.nextInt();
        var x = scanner.nextInt();
        var arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = scanner.nextInt();
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
