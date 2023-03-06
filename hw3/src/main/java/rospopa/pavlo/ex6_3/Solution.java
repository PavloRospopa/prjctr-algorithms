package rospopa.pavlo.ex6_3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Solution {

    /**
     * The problem guarantees that 1 <= Ki <= 2^31 - 1.
     * We can think about the upper bound of K as the number of elements in sequence that can be calculated as
     * sum of arithmetic progression an, where a1 = 1 (digits of 10^0), a2 = 2 (digits of 10^1), a3 = 3 (digits of 10^2)...an = n (digits of 10^(n-1)).
     * Sn = n(a1 + an)/2;
     * n(1 + n)/2 = 2^31 - 1;
     * n is approximately equal 65536.
     * Therefore, we can safely assume that any value of K will be within a sequence of digits ending with a number that is 10 power 70000.
     */
    static final int MAX_POWER_OF_TEN_IN_SEQ = 70000;

    public static void main(String[] args) throws IOException {
        var reader = new BufferedReader(new InputStreamReader(System.in));

        var n = Integer.parseInt(reader.readLine());
        var kArr = new int[n];
        for (int i = 0; i < n; i++) {
            kArr[i] = Integer.parseInt(reader.readLine());
        }

        for (int i = 0; i < n - 1; i++) {
            System.out.print(findDigit(kArr[i]));
            System.out.print(" ");
        }
        System.out.print(findDigit(kArr[n - 1]));
    }

    static char findDigit(int k) {
        // no K will point a digit in sequence ending with number that is 10 power -1.
        var bad = -1;
        // any K will point a digit in sequence ending with number that is 10 power 70000.
        var good = MAX_POWER_OF_TEN_IN_SEQ;
        while (good - bad > 1) {
            var m = (good + bad) / 2;
            if (calculateNumOfDigitsForSeq(m) >= k) {
                good = m;
            } else {
                bad = m;
            }
        }

        //here we have found bad exponent of 10 that is followed by good exponent of 10.
        //pointer K points to some digit in the subsequence of digits of 10 to the good power.
        //the good subsequence of digits has only one '1' digit which is the first one in the subsequence.
        //by deducting num of digits of sequence ending with 10 to the bad power from K we can find the digit pointed by K.
        return k - calculateNumOfDigitsForSeq(bad) > 1 ? '0' : '1';
    }

    private static int calculateNumOfDigitsForSeq(int highestPowerOfTen) {
        var numOfOnes = 1 + highestPowerOfTen;
        var numOfZeros = numOfOnes * highestPowerOfTen / 2;
        return numOfOnes + numOfZeros;
    }
}
