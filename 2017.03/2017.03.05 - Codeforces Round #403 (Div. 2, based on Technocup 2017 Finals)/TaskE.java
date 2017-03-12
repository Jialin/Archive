package main;

import template.collections.list.IntArrayList;
import template.graph.basic.BidirectionalGraph;
import template.io.QuickScanner;
import template.io.QuickWriter;

public class TaskE {
  QuickWriter out;
  int n, m, k;
  BidirectionalGraph graph;
  boolean[] visited;
  IntArrayList res;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    this.out = out;
    n = in.nextInt();
    m = in.nextInt();
    k = in.nextInt();
    graph = new BidirectionalGraph(n, m);
    visited = new boolean[n];
    for (int i = 0; i < m; ++i) {
      graph.add(in.nextInt() - 1, in.nextInt() - 1);
    }
    res = new IntArrayList(n << 1);
    dfs(0);
    print();
  }

  void dfs(int u) {
    visited[u] = true;
    res.add(u);
    for (int edgeIdx = graph.lastOut(u); edgeIdx >= 0; edgeIdx = graph.nextOut(edgeIdx)) {
      int v = graph.toIdx(edgeIdx);
      if (visited[v]) continue;
      dfs(v);
      res.add(u);
    }
  }

  void print() {
    int sliceSize = (res.size + k - 1) / k;
    for (int i = 0, startIdx = 0; i < k; ++i, startIdx = Math.min(startIdx + sliceSize, res.size)) {
      if (startIdx == res.size) {
        out.println("1 1");
        continue;
      }
      out.print(Math.min(res.size - startIdx, sliceSize));
      for (int j = 0; j < sliceSize && startIdx + j < res.size; ++j) {
        out.print(' ');
        out.print(res.get(startIdx + j) + 1);
      }
      out.println();
    }
  }
}
