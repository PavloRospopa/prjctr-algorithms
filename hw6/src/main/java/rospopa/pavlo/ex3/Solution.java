package rospopa.pavlo.ex3;

import java.util.ArrayList;
import java.util.List;

class Solution {
    public List<String> generateParenthesis(int n) {
        List<String> result = new ArrayList<>();
        gen(n, n, "", result);
        return result;
    }

    private void gen(int o, int c, String prefix, List<String> output) {
        if (o == 0 && c == 0) {
            output.add(prefix);
            return;
        }

        if (o > 0) {
            gen(o - 1, c, prefix + '(', output);
        }
        if (c > o) {
            gen(o, c - 1, prefix + ')', output);
        }
    }
}
