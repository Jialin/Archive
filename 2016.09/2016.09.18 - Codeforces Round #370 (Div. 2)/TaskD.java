package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

import static java.lang.Math.min;
import static template.numbertheory.IntUtils.add;
import static template.numbertheory.IntUtils.mul;
import static template.numbertheory.IntUtils.sub;

public class TaskD {
  int a, b, k, T, k2;

  int n;
  int[][] dp;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    a = in.nextInt();
    b = in.nextInt();
    k = in.nextInt();
    k2 = k << 1;
    T = in.nextInt();
    n = (k * T << 1) | 1;
    dp = new int[2][n];
    int t = 0;
    dp[t][0] = 1;
    for (int i = 0; i < T; ++i) {
      for (int j = 1; j < n; ++j) dp[t][j] = add(dp[t][j], dp[t][j - 1]);
      for (int j = 0; j < n; ++j) dp[t ^ 1][j] = sub(dp[t][j], j >= k2 + 1 ? dp[t][j - k2 - 1] : 0);
      t ^= 1;
    }
    int[] sum = new int[n];
    for (int i = 0; i < n; ++i) {
      sum[i] = add(dp[t][i], i > 0 ? sum[i - 1] : 0);
    }
    int res = 0;
    for (int i = 0; i < n; ++i) {
      res = add(res, i + a >= b + 1 ? mul(dp[t][i], sum[min(i + a - b - 1, n - 1)]) : 0);
    }
    out.println(res);
  }
}
