package rospopa.pavlo.ex5_1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Solution {
    private static final char[] COLORS = "rgb".toCharArray();

    public static void main(String[] args) throws IOException {
        var reader = new BufferedReader(new InputStreamReader(System.in));
        var n = Integer.parseInt(reader.readLine());
        var rCost = new int[n];
        var gCost = new int[n];
        var bCost = new int[n];
        for (var i = 0; i < n; i++) {
            var costs = reader.readLine().split(" ");
            rCost[i] = Integer.parseInt(costs[0]);
            gCost[i] = Integer.parseInt(costs[1]);
            bCost[i] = Integer.parseInt(costs[2]);
        }

        var minCost = new Cost(20000);
        gen(n, "", rCost, gCost, bCost, minCost);

        System.out.println(minCost.val);
    }

    private static void gen(int n, String prefix, int[] rCost, int[] gCost, int[] bCost, Cost minCost) {
        if (n == 0) {
            var cost = calculateCost(prefix, rCost, gCost, bCost);
            if (cost < minCost.val) {
                minCost.val = cost;
            }
            return;
        }

        for (var c : COLORS) {
            if (prefix.isEmpty() || prefix.charAt(prefix.length() - 1) != c) {
                gen(n - 1, prefix + c, rCost, gCost, bCost, minCost);
            }
        }
    }

    static int calculateCost(String prefix, int[] rCost, int[] gCost, int[] bCost) {
        var cost = 0;
        var housesColors = prefix.toCharArray();
        for (var i = 0; i < housesColors.length; i++) {
            var c = housesColors[i];
            switch (c) {
                case 'r':
                    cost += rCost[i];
                    break;
                case 'g':
                    cost += gCost[i];
                    break;
                case 'b':
                    cost += bCost[i];
                    break;
            }
        }
        return cost;
    }

    static class Cost {
        int val;

        Cost(int val) {
            this.val = val;
        }
    }
}
