package main;

import template.graph.tree.Tree;
import template.io.QuickScanner;
import template.io.QuickWriter;

public class TaskB {
  Tree tree;
  int oddCnt, evenCnt;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    int n = in.nextInt();
    tree = new Tree(n);
    for (int i = 1; i < n; ++i) {
      tree.add(in.nextInt() - 1, in.nextInt() - 1);
    }
    oddCnt = evenCnt = 0;
    dfs(0, -1, true);
    out.println((long) oddCnt * evenCnt - n + 1);
  }

  void dfs(int u, int parent, boolean even) {
    if (even) {
      ++evenCnt;
    } else {
      ++oddCnt;
    }
    for (int v : tree.outNodes(u)) if (v != parent) {
      dfs(v, u, !even);
    }
  }
}
