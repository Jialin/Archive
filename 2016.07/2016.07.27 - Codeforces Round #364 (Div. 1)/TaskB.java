package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

import static java.lang.Math.min;

public class TaskB {

  int n, k;
  boolean[] marked;
  int[] sum;
  BidirectionalGraph tree;
  long res;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    n = in.nextInt();
    k = in.nextInt();
    marked = new boolean[n];
    for (int i = k << 1; i > 0; --i) {
      marked[in.nextInt() - 1] = true;
    }
    tree = new BidirectionalGraph(n, n - 1);
    for (int i = 1; i < n; ++i) {
      tree.add(in.nextInt() - 1, in.nextInt() - 1);
    }
    sum = new int[n];
    res = 0;
    dfs(0, -1);
    out.println(res);
  }

  void dfs(int u, int parent) {
    sum[u] = marked[u] ? 1 : 0;
    for (int edgeIndex : tree.outgoingEdgeIndex(u)) {
      int v = tree.toIndex[edgeIndex];
      if (v != parent) {
        dfs(v, u);
        sum[u] += sum[v];
      }
    }
    res += min(sum[u], (k << 1) - sum[u]);
  }
}
