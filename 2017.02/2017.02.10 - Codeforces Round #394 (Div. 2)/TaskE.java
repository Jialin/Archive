package main;

import template.graph.tree.Tree;
import template.io.QuickScanner;
import template.io.QuickWriter;

public class TaskE {
  static int[] DX = new int[]{-1, 0, 1, 0};
  static int[] DY = new int[]{0, 1, 0, -1};

  int n;
  Tree tree;
  long[] resX, resY;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    n = in.nextInt();
    tree = new Tree(n);
    for (int i = 1; i < n; ++i) {
      tree.add(in.nextInt() - 1, in.nextInt() - 1);
    }
    if (possible()) {
      out.println("YES");
      resX = new long[n];
      resY = new long[n];
      solve();
      for (int i = 0; i < n; ++i) {
        out.println(resX[i], resY[i]);
      }
    } else {
      out.println("NO");
    }
  }

  boolean possible() {
    for (int i = 0; i < n; ++i) {
      if (tree.outDegree(i) > 4) return false;
    }
    return true;
  }

  void solve() {
    dfs(0, -1, 0, 0, 1L << 32, 0);
  }

  void dfs(int u, int parent, long x, long y, long step, int dir) {
    resX[u] = x;
    resY[u] = y;
    dir = (dir + 2) & 3;
    for (int edgeIdx = tree.lastOut(u); edgeIdx >= 0; edgeIdx = tree.nextOut(edgeIdx)) {
      int v = tree.toIdx(edgeIdx);
      if (v == parent) continue;
      dir = (dir + 1) & 3;
      dfs(v, u, x + step * DX[dir], y + step * DY[dir], step >> 1, dir);
    }
  }
}
