package main;

import template.collections.list.IntArrayList;
import template.io.QuickScanner;
import template.io.QuickWriter;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

public class TaskD {
  static long INF = Long.MAX_VALUE >> 1;

  int n;
  int[][] a;
  long[][] sum;
  long[][] dp;

  static int WIDTH = 2;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    for (int start = 0; start < 3; ++start) for (int end = 0; end < 3; ++end) {
      for (int width = 0; width < WIDTH; ++width) {
        maskSet[start][end][width] = new TreeSet<>();
      }
    }
    for (int width = 1; width <= WIDTH; ++width) {
      generate(width);
    }
    n = in.nextInt();
    a = new int[3][n];
    for (int i = 0; i < 3; ++i) for (int j = 0; j < n; ++j) {
      a[i][j] = in.nextInt();
    }
    sum = new long[8][n];
    for (int j = 0; j < n; ++j) {
      for (int mask = 0; mask < 8; ++mask) {
        sum[mask][j] = 0;
        for (int i = 0; i < 3; ++i) if ((mask & (1 << i)) > 0) {
          sum[mask][j] += a[i][j];
        }
      }
    }
    dp = new long[4][3];
    int t = 0;
    Arrays.fill(dp[t], -INF);
    Arrays.fill(dp[t + 1], -INF);
    dp[t][0] = 0;
    for (int i = 0; i < n; ++i) {
      Arrays.fill(dp[(t + 2) & 3], -INF);
      for (int width = 0; width < WIDTH; ++width) if (i + width < n) {
        int nxtT = (t + width + 1) & 3;
        for (int start = 0; start < 3; ++start) for (int end = 0; end < 3; ++end) {
          IntArrayList currentMasks = masks[start][end][width];
          for (int maskIdx = 0; maskIdx < currentMasks.size; ++maskIdx) {
            int mask = currentMasks.get(maskIdx);
            long currentSum = 0;
            for (int j = 0; j <= width; ++j) {
              currentSum += sum[(mask >> (j * 3)) & 7][i + j];
            }
            dp[nxtT][end] = Math.max(dp[nxtT][end], dp[t][start] + currentSum);
          }
        }
      }
      t = (t + 1) & 3;
    }
    out.println(dp[t][2]);
  }

  boolean[][] mark = new boolean[3][WIDTH];
  Set<Integer>[][][] maskSet = new Set[3][3][WIDTH];
  IntArrayList[][][] masks = new IntArrayList[3][3][WIDTH];

  void generate(int width) {
    for (int start = 0; start < 3; ++start) {
      for (int x = 0; x < 3; ++x) {
        Arrays.fill(mark[x], false);
      }
      dfs(start, 0, start, width, 0);
    }
    for (int start = 0; start < 3; ++start) for (int end = 0; end < 3; ++end) {
      masks[start][end][width - 1] = new IntArrayList(maskSet[start][end][width - 1]);
    }
  }

  void dfs(int x, int y, int start, int width, int mask) {
    if (y == width) {
      maskSet[start][x][width - 1].add(mask);
      return;
    }
    if (x < 0 || x >= 3 || y < 0 || mark[x][y]) return;
    mark[x][y] = true;
    mask |= 1 << (y * 3 + x);
    dfs(x - 1, y, start, width, mask);
    dfs(x + 1, y, start, width, mask);
    dfs(x, y - 1, start, width, mask);
    dfs(x, y + 1, start, width, mask);
    mark[x][y] = false;
  }
}
