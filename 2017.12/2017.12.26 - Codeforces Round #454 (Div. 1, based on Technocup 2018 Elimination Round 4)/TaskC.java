package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

import java.util.Arrays;

public class TaskC {
  int n, m;
  int[] adjMask;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    n = in.nextInt();
    m = in.nextInt();
    adjMask = new int[n];
    for (int i = 0; i < n; ++i) adjMask[i] = 1 << i;
    for (int i = 0; i < m; ++i) {
      int u = in.nextInt() - 1;
      int v = in.nextInt() - 1;
      adjMask[u] |= 1 << v;
      adjMask[v] |= 1 << u;
    }
    int res = calc();
    if (res == 0) {
      out.println(0);
      return;
    }
    out.println(calc());
    boolean first = true;
    for (int mask = (1 << n) - 1; mask > 0; mask = fromDP[mask]) {
      if (!first) out.print(' ');
      first = false;
      out.print(idx[mask] + 1);
    }
    out.println();
  }

  static int INF = Integer.MAX_VALUE;

  int[] dp;
  int[] idx;
  int[] fromDP;

  int calc() {
    if ((m << 1) == n * (n - 1)) return 0;
    dp = new int[1 << n];
    idx = new int[1 << n];
    fromDP = new int[1 << n];
    Arrays.fill(dp, 1, 1 << n, INF);
    for (int i = 0; i < n; ++i) {
      dp[adjMask[i]] = 1;
      idx[adjMask[i]] = i;
    }
    for (int mask = 1; mask < 1 << n; ++mask) if (dp[mask] != INF) {
      for (int i = 0; i < n; ++i) if ((mask & (1 << i)) > 0) {
        int nxtMask = mask | adjMask[i];
        if (dp[nxtMask] > dp[mask] + 1) {
          fromDP[nxtMask] = mask;
          idx[nxtMask] = i;
          dp[nxtMask] = dp[mask] + 1;
        }
      }
    }
    return dp[(1 << n) - 1];
  }
}
