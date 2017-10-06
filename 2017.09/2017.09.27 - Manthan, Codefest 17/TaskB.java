package main;

import template.array.LongArrayUtils;
import template.io.QuickScanner;
import template.io.QuickWriter;

import java.util.Arrays;

public class TaskB {
  int n;
  int[] a;
  long[][] dp;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    n = in.nextInt();
    int p = in.nextInt();
    int q = in.nextInt();
    int r = in.nextInt();
    a = in.nextInt(n);
    dp = new long[2][n];
    Arrays.fill(dp[0], 0);
    calc(p, dp[0], dp[1]);
    calc(q, dp[1], dp[0]);
    calc(r, dp[0], dp[1]);
    out.println(LongArrayUtils.max(dp[1]));
  }

  void calc(long coef, long[] source, long[] res) {
    Arrays.fill(res, Long.MIN_VALUE);
    for (int i = 0; i < n; ++i) {
      res[i] = Math.max(res[i], source[i] + coef * a[i]);
    }
    for (int i = 1; i < n; ++i) {
      res[i] = Math.max(res[i], res[i - 1]);
    }
  }
}
