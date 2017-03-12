package main;

import template.collections.rmq.IntRangeMaximumQuery2D;
import template.io.QuickScanner;
import template.io.QuickWriter;

import static java.lang.Math.min;

public class TaskD {

  int n, m;
  int[][] dp;
  char[] s = new char[1];

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    n = in.nextInt();
    m = in.nextInt();
    dp = new int[n][m];
    for (int i = 0; i < n; ++i) {
      for (int j = 0; j < m; ++j) {
        in.next(s);
        if (s[0] == '0') {
          dp[i][j] = 0;
        } else if (i > 0 && j > 0) {
          dp[i][j] = 1 + min(min(dp[i - 1][j], dp[i][j - 1]), dp[i - 1][j - 1]);
        } else {
          dp[i][j] = 1;
        }
      }
    }
    IntRangeMaximumQuery2D rmq = new IntRangeMaximumQuery2D(n, m);
    rmq.init(n, m, dp);
    for (int remQ = in.nextInt(); remQ > 0; --remQ) {
      int x1 = in.nextInt() - 1;
      int y1 = in.nextInt() - 1;
      int x2 = in.nextInt();
      int y2 = in.nextInt();
      int lower = 1, upper = min(x2 - x1, y2 - y1), res = 0;
      while (lower <= upper) {
        int medium = (lower + upper) >> 1;
        if (rmq.calc(x1 + medium - 1, y1 + medium - 1, x2, y2) >= medium) {
          res = medium;
          lower = medium + 1;
        } else {
          upper = medium - 1;
        }
      }
      out.println(res);
    }
  }
}
