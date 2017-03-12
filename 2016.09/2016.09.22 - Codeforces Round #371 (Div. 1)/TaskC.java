package main;

import template.array.IntArrayUtils;
import template.io.QuickScanner;
import template.io.QuickWriter;

import java.util.Arrays;

import static java.lang.Math.abs;
import static java.lang.Math.min;

public class TaskC {
  static final long INF = Long.MAX_VALUE;

  int n, m;
  int[] a;
  int[] b;
  long[][] dp;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    n = in.nextInt();
    a = in.nextInt(n);
    for (int i = 1; i < n; ++i) a[i] -= i;
    b = Arrays.copyOf(a, n);
    Arrays.sort(b);
    m = IntArrayUtils.unique(b);
    dp = new long[2][m];
    int t = 0;
    for (int j = 0; j < m; ++j) {
      dp[t][j] = a[0] <= b[j] ? 0 : abs(a[0] - b[j]);
    }
    for (int i = 1; i < n; ++i) {
      for (int j = 0; j < m; ++j) dp[t ^ 1][j] = dp[t][j] + abs(a[i] - b[j]);
      for (int j = 1; j < m; ++j) dp[t ^ 1][j] = min(dp[t ^ 1][j], dp[t ^ 1][j - 1]);
      t ^= 1;
    }
    out.println(dp[t][m - 1]);
  }
}
