package main;

import template.graph.tree.Tree;
import template.io.QuickScanner;
import template.io.QuickWriter;

import java.util.Arrays;

public class TaskE {
  static final int MAXBIT = 20;

  int n;
  int[] a;
  Tree tree;
  int[] marked;
  int[][] cnt;
  long res;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    n = in.nextInt();
    a = in.nextInt(n);
    tree = new Tree(n);
    for (int i = 1; i < n; ++i) {
      int x = in.nextInt() - 1;
      int y = in.nextInt() - 1;
      tree.add(x, y);
    }
    long res = 0;
    marked = new int[n];
    cnt = new int[2][n];
    for (int bit = 0; bit < MAXBIT; ++bit) {
      boolean hasMarked = false;
      for (int i = 0; i < n; ++i) {
        marked[i] = (a[i] >> bit) & 1;
        hasMarked |= marked[i] > 0;
      }
      res += hasMarked ? calc() << bit : 0;
    }
    out.println(res);
  }

  long calc() {
    Arrays.fill(cnt[0], 0);
    Arrays.fill(cnt[1], 0);
    res = 0;
    dfs(0, -1);
    return res;
  }

  void dfs(int u, int parent) {
    int marked = this.marked[u];
    for (int edgeIdx = tree.lastOut(u); edgeIdx >= 0; edgeIdx = tree.nextOut(edgeIdx)) {
      int v = tree.toIdx(edgeIdx);
      if (v == parent) continue;
      dfs(v, u);
      res += (long) cnt[0][u] * cnt[marked ^ 1][v] + (long) cnt[1][u] * cnt[marked][v];
      cnt[0][u] += cnt[0][v];
      cnt[1][u] += cnt[1][v];
    }
    if (marked > 0) {
      int tmp = cnt[0][u];
      cnt[0][u] = cnt[1][u];
      cnt[1][u] = tmp;
    }
    ++cnt[marked][u];
    res += cnt[1][u];
  }
}
