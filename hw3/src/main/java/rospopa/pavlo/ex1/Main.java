package rospopa.pavlo.ex1;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        var scanner = new Scanner(System.in);
        var n = scanner.nextInt();

        var height = new int[n];

        for (int i = 0; i < n; i++) {
            height[i] = scanner.nextInt();
        }

        var maxArea = maxArea(height);

        System.out.println("result " + maxArea);
    }

    private static int maxArea(int[] height) {
        var maxArea = 0;
        for (int i = 0; i < height.length; i++) {
            for (int j = i + 1; j < height.length; j++) {
                var area = (j - i) * Math.min(height[i], height[j]);
                if (area > maxArea) {
                    maxArea = area;
                }
            }
        }

        return maxArea;
    }
}
