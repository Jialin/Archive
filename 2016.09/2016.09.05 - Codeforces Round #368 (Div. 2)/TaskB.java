package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

public class TaskB {
  static final int INF = Integer.MAX_VALUE;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    int n = in.nextInt();
    int m = in.nextInt();
    int k = in.nextInt();
    IntWeightedBidirectionalGraph graph = new IntWeightedBidirectionalGraph(n, m);
    graph.init(n);
    for (int i = 0; i < m; ++i) {
      int u = in.nextInt() - 1;
      int v = in.nextInt() - 1;
      int l = in.nextInt();
      graph.add(u, v, l);
    }
    boolean[] used = new boolean[n];
    for (int i = 0; i < k; ++i) {
      used[in.nextInt() - 1] = true;
    }
    int res = INF;
    for (int i = 0; i < n; ++i) if (used[i]) {
      for (int edgeIdx = graph.lastOut[i]; edgeIdx >= 0; edgeIdx = graph.nextOut[edgeIdx]) {
        int j = graph.toIdx[edgeIdx];
        if (!used[j]) {
          res = Math.min(res, graph.weights[edgeIdx]);
        }
      }
    }
    out.println(res == INF ? -1 : res);
  }
}
