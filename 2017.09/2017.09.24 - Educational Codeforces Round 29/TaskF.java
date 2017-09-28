package main;

import template.graph.flow.IntMinCostMaxFlow;
import template.io.QuickScanner;
import template.io.QuickWriter;

public class TaskF {
  int n, q;
  int[] lower, upper, cnt;
  IntMinCostMaxFlow mcmf;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    n = in.nextInt();
    q = in.nextInt();
    lower = new int[n];
    upper = new int[n];
    for (int i = 0; i < n; ++i) upper[i] = n - 1;
    for (int remQ = q; remQ > 0; --remQ) {
      int op = in.nextInt();
      int l = in.nextInt() - 1;
      int r = in.nextInt();
      int v = in.nextInt() - 1;
      for (int i = l; i < r; ++i) {
        if (op == 1) {
          lower[i] = Math.max(lower[i], v);
        } else {
          upper[i] = Math.min(upper[i], v);
        }
      }
    }
    out.println(calc());
  }

  long calc() {
    for (int i = 0; i < n; ++i) if (lower[i] > upper[i]) return -1;
    cnt = new int[n];
    for (int i = 0; i < n; ++i) for (int j = lower[i]; j <= upper[i]; ++j) ++cnt[j];
    mcmf = new IntMinCostMaxFlow((n << 1) + 2, n * n * 3);
    int source = n << 1, sink = source | 1;
    mcmf.init(sink + 1);
    for (int i = 0; i < n; ++i) for (int j = 0; j < cnt[i]; ++j) {
      mcmf.add(source, i, 1, (j << 1) | 1);
    }
    for (int i = 0; i < n; ++i) for (int j = lower[i]; j <= upper[i]; ++j) {
      mcmf.add(j, n + i, 1, 0);
    }
    for (int i = 0; i < n; ++i) {
      mcmf.add(n + i, sink, 1, 0);
    }
    mcmf.calc(source, sink);
    if (mcmf.resFlow != n) return -1;
    return mcmf.resCost;
  }
}
