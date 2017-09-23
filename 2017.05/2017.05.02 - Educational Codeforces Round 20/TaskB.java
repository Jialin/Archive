package main;

import template.array.IntArrayUtils;
import template.io.QuickScanner;
import template.io.QuickWriter;

public class TaskB {
  static int INF = Integer.MAX_VALUE >> 1;

  int n;
  int[] a, forward, backward;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    n = in.nextInt();
    a = in.nextInt(n);
    forward = new int[n];
    calc(forward);
    IntArrayUtils.reverse(a);
    backward = new int[n];
    calc(backward);
    for (int i = 0; i < n; ++i) {
      out.print(Math.min(forward[i], backward[n - 1 - i]));
      out.print(i + 1 == n ? '\n' : ' ');
    }
  }

  void calc(int[] dp) {
    int last = INF;
    for (int i = 0; i < n; ++i) {
      if (a[i] == 0) last = -1;
      if (last != INF) ++last;
      dp[i] = last;
    }
  }
}
