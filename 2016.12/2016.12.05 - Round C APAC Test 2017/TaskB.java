package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

public class TaskB {
  int n, m, k;
  int[][] sum;

  public void solve(int taskIdx, QuickScanner in, QuickWriter out) {
    System.err.printf("Handling #%d\n", taskIdx);
    n = in.nextInt();
    m = in.nextInt();
    k = in.nextInt();
    sum = new int[n][m];
    for (int i = 0; i < k; ++i) {
      int x = in.nextInt();
      int y = in.nextInt();
      ++sum[x][y];
    }
    for (int i = 0; i < n; ++i) for (int j = 0; j < m; ++j) {
      sum[i][j] +=
          (i > 0 ? sum[i - 1][j] : 0)
              + (j > 0 ? sum[i][j - 1] : 0)
              - (i > 0 && j > 0 ? sum[i - 1][j - 1] : 0);
    }
    long res = 0;
    for (int i = 0; i < n; ++i) for (int j = 0; j < m; ++j) {
      int lower = 1, upper = Math.min(n - i, m - j), size = 0;
      while (lower <= upper) {
        int medium = (lower + upper) >> 1;
        if (calc(i, j, i + medium - 1, j + medium - 1) == 0) {
          size = medium;
          lower = medium + 1;
        } else {
          upper = medium - 1;
        }
      }
      res += size;
    }
    out.printf("Case #%d: %d\n", taskIdx, res);
  }

  int calc(int x1, int y1, int x2, int y2) {
    return calc(x2, y2) - calc(x1 - 1, y2) - calc(x2, y1 - 1) + calc(x1 - 1, y1 - 1);
  }

  int calc(int x, int y) {
    return x < 0 || y < 0 ? 0 : sum[x][y];
  }
}
