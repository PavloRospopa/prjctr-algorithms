package rospopa.pavlo.ex5_2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Collections.min;

public class Solution {
    public static void main(String[] args) throws IOException {
        var reader = new BufferedReader(new InputStreamReader(System.in));
        var n = Integer.parseInt(reader.readLine());
        var myUnits = toIntArr(reader.readLine().split(" "));
        var opponentsUnits = toIntArr(reader.readLine().split(" "));

        var maxStrength = new Strength();
        var myUnitsUsed = new boolean[n];
        var opponentsUnitsUsed = new boolean[n];
        gen(n, emptyList(), myUnits, opponentsUnits, myUnitsUsed, opponentsUnitsUsed, maxStrength);

        System.out.println(maxStrength.val);
    }

    static void gen(int n, List<UnitPair> prefix, int[] myUnits, int[] opponentsUnits, boolean[] myUnitsUsed,
                    boolean[] opponentsUnitsUsed, Strength maxStrength) {
        if (n == 0) {
            System.out.println(prefix.toString());
            var remainingStrength = calculateStrengthAfterBattle(prefix, myUnits, opponentsUnits);
            if (remainingStrength > maxStrength.val) {
                maxStrength.val = remainingStrength;
            }
            return;
        }

        for (int i = 0; i < myUnits.length; i++) {
            if (!myUnitsUsed[i]) {
                myUnitsUsed[i] = true;
                for (int j = 0; j < opponentsUnits.length; j++) {
                    if (!opponentsUnitsUsed[j]) {
                        opponentsUnitsUsed[j] = true;
                        var newPrefix = new ArrayList<>(prefix);
                        newPrefix.add(new UnitPair(i, j));
                        gen(n - 1, newPrefix, myUnits, opponentsUnits, myUnitsUsed, opponentsUnitsUsed, maxStrength);
                        opponentsUnitsUsed[j] = false;
                    }
                }
                myUnitsUsed[i] = false;
            }
        }
    }

    static int calculateStrengthAfterBattle(List<UnitPair> prefix, int[] myUnits, int[] opponentsUnits) {
        var strength = 0;
        for (UnitPair pair : prefix) {
            var myUnitStrength = myUnits[pair.mine];
            if (myUnitStrength > opponentsUnits[pair.opponents]) {
                strength += myUnitStrength;
            }
        }
        return strength;
    }

    static int[] toIntArr(String[] arr) {
        var intArr = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            intArr[i] = Integer.parseInt(arr[i]);
        }
        return intArr;
    }

    static class Strength {
        int val;
    }

    static class UnitPair {
        final int mine;
        final int opponents;

        UnitPair(int mine, int opponents) {
            this.mine = mine;
            this.opponents = opponents;
        }

        @Override
        public String toString() {
            return String.format("[mine=%1$d:opponents=%2$d]", mine, opponents);
        }
    }
}
