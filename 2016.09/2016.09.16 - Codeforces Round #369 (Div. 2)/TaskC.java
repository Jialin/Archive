package main;

import template.collections.top.LongTopMinimum;
import template.io.QuickScanner;
import template.io.QuickWriter;

public class TaskC {
  static final long INF = Long.MAX_VALUE >> 1;

  int n, m, k;
  int[] color;
  int[][] colorCost;
  long[][][] dp;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    n = in.nextInt();
    m = in.nextInt();
    k = in.nextInt();
    dp = new long[2][m + 1][k + 1];
    color = in.nextInt(n);
    colorCost = new int[n][0];
    for (int i = 0; i < n; ++i) colorCost[i] = in.nextInt(m);
    int t = 0;
    init(t);
    dp[t][0][0] = 0;
    LongTopMinimum ltm = new LongTopMinimum();
    for (int i = 0; i < n; ++i) {
      init(t ^ 1);
      if (color[i] > 0) {
        // Same
        for (int diff = 0; diff <= k; ++diff) {
          update(t ^ 1, color[i], diff, dp[t][color[i]][diff]);
        }
        // Diff
        for (int prevColor = 0; prevColor <= m; ++prevColor) if (prevColor != color[i]) {
          for (int diff = 0; diff < k; ++diff) {
            update(t ^ 1, color[i], diff + 1, dp[t][prevColor][diff]);
          }
        }
      } else {
        // Same
        for (int prevColor = 1; prevColor <= m; ++prevColor) for (int diff = 0; diff <= k; ++diff) {
          update(t ^ 1, prevColor, diff, dp[t][prevColor][diff] + colorCost[i][prevColor - 1]);
        }
        // Diff
        for (int diff = 0; diff < k; ++diff) {
          ltm.init();
          for (int prevColor = 0; prevColor <= m; ++prevColor) {
            ltm.add(dp[t][prevColor][diff], prevColor);
          }
          for (int currColor = 1; currColor <= m; ++currColor) {
            update(t ^ 1, currColor, diff + 1, ltm.calc(currColor) + colorCost[i][currColor - 1]);
          }
        }
      }
      t ^= 1;
    }
    long res = INF;
    for (int color = 1; color <= m; ++color) {
      res = Math.min(res, dp[t][color][k]);
    }
    out.println(res == INF ? -1 : res);
  }

  void init(int t) {
    for (int color = 0; color <= m; ++color) for (int diff = 0; diff <= k; ++diff) {
      dp[t][color][diff] = INF;
    }
  }

  void update(int t, int color, int diff, long value) {
    dp[t][color][diff] = Math.min(dp[t][color][diff], value);
  }
}
