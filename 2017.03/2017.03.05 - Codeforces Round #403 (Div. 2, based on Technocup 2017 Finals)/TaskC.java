package main;

import template.graph.tree.Tree;
import template.io.QuickScanner;
import template.io.QuickWriter;

import java.util.Arrays;

public class TaskC {
  int n;
  Tree tree;
  int[] marker, color;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    n = in.nextInt();
    tree = new Tree(n);
    for (int i = 1; i < n; ++i) {
      tree.add(in.nextInt() - 1, in.nextInt() - 1);
    }
    int res = 0;
    for (int i = 0; i < n; ++i) {
      res = Math.max(res, tree.outDegree(i));
    }
    out.println(res + 1);
    marker = new int[res + 1];
    Arrays.fill(marker, -1);
    color = new int[n];
    color[0] = 1;
    dfs(0, -1);
    out.println(color);
  }

  int pnt;

  void dfs(int u, int parent) {
    if (parent >= 0) marker[color[parent] - 1] = u;
    marker[color[u] - 1] = u;
    pnt = 0;
    for (int edgeIdx = tree.lastOut(u); edgeIdx >= 0; edgeIdx = tree.nextOut(edgeIdx)) {
      int v = tree.toIdx(edgeIdx);
      if (v == parent) continue;
      mark(v, u);
    }
    for (int edgeIdx = tree.lastOut(u); edgeIdx >= 0; edgeIdx = tree.nextOut(edgeIdx)) {
      int v = tree.toIdx(edgeIdx);
      if (v == parent) continue;
      dfs(v, u);
    }
  }

  void mark(int u, int tag) {
    for ( ; marker[pnt] == tag; ++pnt) {}
    marker[pnt] = tag;
    color[u] = ++pnt;
  }
}
