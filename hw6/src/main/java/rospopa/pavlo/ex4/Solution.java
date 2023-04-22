package rospopa.pavlo.ex4;

import java.util.ArrayList;
import java.util.List;

class Solution {

    public List<List<Integer>> combine(int n, int k) {
        int[] alphabet = new int[n];
        for (int i = 0; i < n; i++) {
            alphabet[i] = i + 1;
        }

        List<String> binVectors = new ArrayList<>();
        gen(n, k, "", binVectors);

        List<List<Integer>> combinations = new ArrayList<>();
        for (String bv : binVectors) {
            List<Integer> c = new ArrayList<>();
            char[] v = bv.toCharArray();
            for (int i = 0; i < n; i++) {
                if (v[i] == '1') {
                    c.add(alphabet[i]);
                }
            }
            combinations.add(c);
        }
        return combinations;
    }

    private void gen(int n, int k, String prefix, List<String> output) {
        if (n == 0 && k == 0) {
            output.add(prefix);
            return;
        }

        n--;
        if (n >= k) {
            gen(n, k, prefix + '0', output);
        }
        if (k > 0) {
            gen(n, k - 1, prefix + '1', output);
        }
    }
}
