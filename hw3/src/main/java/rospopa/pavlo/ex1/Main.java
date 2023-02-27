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

        var maxArea = maxAreaFast(height);

        System.out.println("result " + maxArea);
    }

    private static int maxAreaSlow(int[] height) {
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

    private static int maxAreaFast(int[] height) {
        var l = 0;
        var r = height.length - 1;

        var maxArea = 0;
        while (l != r) {
            var hl = height[l];
            var hr = height[r];
            var area = (r - l) * Math.min(hl, hr);
            if (area > maxArea) {
                maxArea = area;
            }

            if (hl > hr) {
                r--;
            } else {
                l++;
            }
        }

        return maxArea;
    }
}
