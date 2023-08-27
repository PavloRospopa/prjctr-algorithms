package rospopa.pavlo.ex1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Solution {

    public static void main(String[] args) {
        char[][] grid = new char[][]{
                {'1', '1', '0', '0', '0'},
                {'1', '1', '0', '0', '0'},
                {'0', '0', '1', '0', '0'},
                {'0', '0', '0', '1', '1'}
        };

        IslandExplorer explorer = new IslandExplorer(grid);
        System.out.println(explorer.exploreNumOfIslands());
        for (char[] line : grid) {
            System.out.println(Arrays.toString(line));
        }
    }

    static class IslandExplorer {
        // left, up, right, down
        static int[] mDir = new int[]{0, -1, 0, 1};
        static int[] nDir = new int[]{-1, 0, 1, 0};

        int m;
        int n;
        char[][] grid;

        IslandExplorer(char[][] grid) {
            this.m = grid.length;
            this.n = grid[0].length;
            this.grid = grid;
        }

        int exploreNumOfIslands() {
            int islands = 0;

            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (grid[i][j] == '1') {
                        dfs(i, j);
                        islands++;
                    }
                }
            }

            return islands;
        }

        void dfs(int i, int j) {
            grid[i][j] = '2';

            for (Cell c : neighbors(i, j)) {
                if (grid[c.i][c.j] != '2') {
                    dfs(c.i, c.j);
                }
            }
        }

        List<Cell> neighbors(int i, int j) {
            List<Cell> neighbors = new ArrayList<>(4);
            for (int t = 0; t < 4; t++) {
                int it = i + mDir[t];
                int jt = j + nDir[t];

                if (it >= 0 && it < m && jt >= 0 && jt < n && grid[it][jt] != '0') {
                    neighbors.add(new Cell(it, jt));
                }
            }

            return neighbors;
        }

        record Cell(int i, int j) {
        }
    }
}
