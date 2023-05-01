package rospopa.pavlo.ex5_3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Solution {
    final int n;
    final int hasZeros;
    final int hasOnes;
    final BinaryString[] strings;

    public Solution(int n, int hasZeros, int hasOnes, BinaryString[] strings) {
        this.n = n;
        this.hasZeros = hasZeros;
        this.hasOnes = hasOnes;
        this.strings = strings;
    }

    public static void main(String[] args) throws IOException {
        var reader = new BufferedReader(new InputStreamReader(System.in));
        var input = reader.readLine().split(" ");
        var n = Integer.parseInt(input[0]);
        var hasZeroes = Integer.parseInt(input[1]);
        var hasOnes = Integer.parseInt(input[2]);

        var strings = new BinaryString[n];
        for (var i = 0; i < n; i++) {
            strings[i] = new BinaryString(reader.readLine());
        }

        var solution = new Solution(n, hasZeroes, hasOnes, strings);
        System.out.println(solution.solve());
    }

    private int solve() {
        var result = 0;
        for (var i = 0; i < 1 << n; i++) {
            var subset = Integer.toBinaryString(i);
            var cost = calculateCostOfPicking(subset);
            if (cost.zeros <= hasZeros && cost.ones <= hasOnes && cost.numberOfStrings > result) {
                result = cost.numberOfStrings;
            }
        }

        return result;
    }

    private Cost calculateCostOfPicking(String subset) {
        var cost = new Cost();
        var stringsToPick = subset.toCharArray();
        for (int i = stringsToPick.length - 1, j = strings.length - 1; i >= 0; i--, j--) {
            if (stringsToPick[i] == '1') {
                var binaryString = strings[j];
                cost.addOnes(binaryString.ones);
                cost.addZeros(binaryString.zeros);
                cost.incNumberOfStrings();
            }
        }

        return cost;
    }

    static class Cost {
        int numberOfStrings;
        int zeros;
        int ones;

        void incNumberOfStrings() {
            numberOfStrings++;
        }

        void addZeros(int z) {
            zeros += z;
        }

        void addOnes(int o) {
            ones += o;
        }
    }

    static class BinaryString {
        final String str;
        final int zeros;
        final int ones;

        BinaryString(String str) {
            var zeros = 0;
            var ones = 0;
            var chars = str.toCharArray();
            for (var c : chars) {
                switch (c) {
                    case '0':
                        zeros++;
                        break;
                    case '1':
                        ones++;
                        break;
                }
            }
            this.zeros = zeros;
            this.ones = ones;
            this.str = str;
        }
    }
}
