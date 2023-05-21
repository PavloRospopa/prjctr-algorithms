package rospopa.pavlo.ex5_5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class IterativeSolution {
    final int s;
    final int m;
    final int n;
    final int[] t;
    final Player[] bCache;
    final Player[] sCache;

    IterativeSolution(int s, int m, int n, int[] t) {
        this.s = s;
        this.m = m;
        this.n = n;
        this.t = t;
        this.bCache = new Player[n + 1];
        this.sCache = new Player[n + 1];
        this.initCaches();
    }

    public static void main(String[] args) throws IOException {
        var reader = new BufferedReader(new InputStreamReader(System.in));
        var input = reader.readLine().split(" ");
        var s = Integer.parseInt(input[0]);
        var m = Integer.parseInt(input[1]);
        var n = Integer.parseInt(input[2]);
        var t = toIntArr(reader.readLine().split(" "));

        var solution = new IterativeSolution(s, m, n, t);
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
            var winner = bCache[k];
            if (winner == Player.B) {
                wins++;
            }
        }

        return wins;
    }

    private void initCaches() {
        for (int ti : t) {
            if (ti <= n) {
                bCache[ti] = Player.B;
                sCache[ti] = Player.S;
            }
        }

        for (int k = 1; k <= n; k++) {
            playAndCache(Player.B, k);
            playAndCache(Player.S, k);
        }
    }

    private void playAndCache(Player currentPlayer, int k) {
        var currentPlayerCache = getCache(currentPlayer);
        var opponentPlayer = Player.opponentOf(currentPlayer);
        var opponentPlayerCache = getCache(opponentPlayer);

        for (int ti : t) {
            if (k - ti > 0 && opponentPlayerCache[k - ti] == currentPlayer) {
                currentPlayerCache[k] = currentPlayer;
                break;
            }
        }

        if (currentPlayerCache[k] == null) {
            currentPlayerCache[k] = opponentPlayer;
        }
    }

    private Player[] getCache(Player currentPlayer) {
        return currentPlayer == Player.B ? bCache : sCache;
    }

    enum Player {
        B, S;

        static Player opponentOf(Player p) {
            return p == B ? S : B;
        }
    }
}
