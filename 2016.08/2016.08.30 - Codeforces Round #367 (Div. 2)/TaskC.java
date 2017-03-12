package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

import static java.lang.Math.min;

public class TaskC {
  static long INF = Long.MAX_VALUE;

  int n;
  int[] c;
  String[][] s;
  long[][] dp;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    n = in.nextInt();
    c = in.nextInt(n);
    s = new String[n][2];
    for (int i = 0; i < n; ++i) {
      s[i][0] = in.next();
      s[i][1] = new StringBuilder(s[i][0]).reverse().toString();
    }
    dp = new long[2][2];
    int t = 0;
    dp[t][0] = 0;
    dp[t][1] = c[0];
    for (int i = 1; i < n; ++i) {
      dp[t ^ 1][0] = dp[t ^ 1][1] = INF;
      for (int a = 0; a < 2; ++a) {
        for (int b = 0; b < 2; ++b) if (dp[t][a] < INF && s[i - 1][a].compareTo(s[i][b]) <= 0) {
          dp[t ^ 1][b] = min(dp[t ^ 1][b], dp[t][a] + (b > 0 ? c[i] : 0));
        }
      }
      t ^= 1;
    }
    long res = min(dp[t][0], dp[t][1]);
    out.println(res == INF ? -1 : res);
  }
}
