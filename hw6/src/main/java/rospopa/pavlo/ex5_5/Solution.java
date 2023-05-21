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
        var wins = 0;
        for (var k = m; k <= n; k++) {
            var winner = playGame(Player.B, k);
            if (winner == Player.B) {
                wins++;
            }
        }

        return wins;
    }

    private Player playGame(Player currentPlayer, int k) {
        for (int ti : t) {
            if (k - ti == 0) {
                return currentPlayer;
            }
        }

        for (int ti : t) {
            if (k - ti > 0 && playGame(Player.opponentOf(currentPlayer), k - ti) == currentPlayer) {
                return currentPlayer;
            }
        }

        return Player.opponentOf(currentPlayer);
    }

    enum Player {
        B, S;

        public static Player opponentOf(Player p) {
            return p == B ? S : B;
        }
    }
}
