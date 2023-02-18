package rospopa.pavlo.ex2;

import java.util.Scanner;

public class Main {

    /**
     * The program:
     * 1. Reads input args x y z w. (1 <= x, y, z <= 100, 1 <= w <= 1000)
     * 2. Iterates over all probable parameters of equation ix + jy + kz = w and finds correct ones.
     * 3. The result of possible correct combinations is printed to stdout.
     * Algorithm analysis: program has time complexity O(n^3);
     * in the worst case scenario (x = y = z = 1, w = 1000) program executes 10^9 operations which satisfies problem requirements.
     */
    public static void main(String[] args) {
        var in = new Scanner(System.in);

        var x = in.nextInt();
        var y = in.nextInt();
        var z = in.nextInt();
        var w = in.nextInt();

        var count = 0;
        for (int i = 0; i <= w / x; i++) {
            for (int j = 0; j <= w / y; j++) {
                for (int k = 0; k <= w / z; k++) {
                    if (i * x + j * y + k * z == w) {
                        count++;
                    }
                }
            }
        }

        System.out.println(count);
    }
}
