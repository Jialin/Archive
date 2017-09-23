package main;

import template.graph.tree.Tree;
import template.io.QuickScanner;
import template.io.QuickWriter;

public class TaskC {
  int n, x;
  Tree tree;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    n = in.nextInt();
    x = in.nextInt() - 1;
    tree = new Tree(n);
    for (int i = 1; i < n; ++i) {
      tree.add(in.nextInt() - 1, in.nextInt() - 1);
    }
    parent = new int[n];
    depth = new int[n];
    maxDepth = new int[n];
    dfs(0, -1, 0);
    int upCnt = (depth[x] - 1) >> 1;
    for (int i = 0; i < upCnt; ++i, x = parent[x]) {}
    out.println(maxDepth[x] << 1);
  }

  int[] parent, depth, maxDepth;

  void dfs(int u, int parent, int depth) {
    this.parent[u] = parent;
    this.depth[u] = depth;
    int res = depth;
    for (int v : tree.outNodes(u)) if (v != parent) {
      dfs(v, u, depth + 1);
      res = Math.max(res, maxDepth[v]);
    }
    maxDepth[u] = res;
  }
}
