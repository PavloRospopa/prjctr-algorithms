package rospopa.pavlo.ex5_5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.function.BiFunction;

public class RecursiveSolution {
    final int s;
    final int m;
    final int n;
    final int[] t;
    final Player[] bCache;
    final Player[] sCache;
    final BiFunction<Player, Integer, Player> memoizedPlayGame;

    RecursiveSolution(int s, int m, int n, int[] t) {
        this.s = s;
        this.m = m;
        this.n = n;
        this.t = t;
        this.bCache = new Player[n + 1];
        this.sCache = new Player[n + 1];
        this.memoizedPlayGame = memoize(this::playGame);
    }

    public static void main(String[] args) throws IOException {
        var reader = new BufferedReader(new InputStreamReader(System.in));
        var input = reader.readLine().split(" ");
        var s = Integer.parseInt(input[0]);
        var m = Integer.parseInt(input[1]);
        var n = Integer.parseInt(input[2]);
        var t = toIntArr(reader.readLine().split(" "));

        var solution = new RecursiveSolution(s, m, n, t);
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
            var winner = memoizedPlayGame.apply(Player.B, k);
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
            if (k - ti > 0 && memoizedPlayGame.apply(Player.opponentOf(currentPlayer), k - ti) == currentPlayer) {
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

    private BiFunction<Player, Integer, Player> memoize(BiFunction<Player, Integer, Player> playGameFunc) {
        return (currentPlayer, k) -> {
            var cache = currentPlayer == Player.B ? bCache : sCache;
            if (cache[k] == null) {
                var winner = playGameFunc.apply(currentPlayer, k);
                cache[k] = winner;
            }
            return cache[k];
        };
    }
}
