package rospopa.pavlo.ex5_5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Solution {
    final int s;
    final int m;
    final int n;
    final int[] t;

    Solution(int s, int m, int n, int[] t) {
        this.s = s;
        this.m = m;
        this.n = n;
        this.t = t;
    }

    public static void main(String[] args) throws IOException {
        var reader = new BufferedReader(new InputStreamReader(System.in));
        var input = reader.readLine().split(" ");
        var s = Integer.parseInt(input[0]);
        var m = Integer.parseInt(input[1]);
        var n = Integer.parseInt(input[2]);
        var t = toIntArr(reader.readLine().split(" "));

        var solution = new Solution(s, m, n, t);
        System.out.println(solution.solve());
    }

    static int[] toIntArr(String[] arr) {
        var intArr = new int[arr.length];
        for (var i = 0; i < arr.length; i++) {
            intArr[i] = Integer.parseInt(arr[i]);
        }
        return intArr;
    }

    private int solve() {
        var result = 0;
        for (var k = m; k <= n; k++) {
            var gameResult = playGame(1, true, k);
            if (gameResult.bobsTurn) {
                result++;
            }
        }

        return result;
    }

    private Turn playGame(int turn, boolean bobsTurn, int stones) {
        if (stones < 0) {
            return Turn.INVALID_TURN;
        }
        if (stones == 0) {
            return new Turn(turn, bobsTurn, stones);
        }

        var answer = new Turn(Integer.MAX_VALUE, bobsTurn, stones);
        for (var ti : t) {
            var result = playGame(turn + 1, !bobsTurn, stones - ti);
            if (result.remainingStones == 0 && result.turn < answer.turn) {
                answer = result;
            }
        }

        return answer;
    }

    static class Turn {
        static final Turn INVALID_TURN = new Turn(Integer.MAX_VALUE, false, 0);

        int turn;
        boolean bobsTurn;
        int remainingStones;

        Turn(int turn, boolean bobsTurn, int remainingStones) {
            this.turn = turn;
            this.bobsTurn = bobsTurn;
            this.remainingStones = remainingStones;
        }
    }
}
