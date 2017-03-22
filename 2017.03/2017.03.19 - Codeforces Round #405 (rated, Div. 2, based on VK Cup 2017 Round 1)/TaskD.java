package main;

import template.graph.tree.Tree;
import template.io.QuickScanner;
import template.io.QuickWriter;

public class TaskD {
  int n, m;
  Tree tree;

  int[] cnt;
  long[] sumDist;
  int[][] sumRem;
  long res;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    n = in.nextInt();
    m = in.nextInt();
    tree = new Tree(n);
    for (int i = 1; i < n; ++i) {
      tree.add(in.nextInt() - 1, in.nextInt() - 1);
    }
    cnt = new int[n];
    sumDist = new long[n];
    sumRem = new int[m][n];
    res = 0;
    dfs(0, -1);
    out.println(res / m + n * (n - 1L) / 2);
  }

  void dfs(int u, int parent) {
    cnt[u] = 1;
    sumRem[0][u] = 1;
    for (int edgeIdx = tree.lastOut(u); edgeIdx >= 0; edgeIdx = tree.nextOut(edgeIdx)) {
      int v = tree.toIdx(edgeIdx);
      if (v == parent) continue;
      dfs(v, u);
      res += (sumDist[v] + cnt[v]) * cnt[u] + sumDist[u] * cnt[v];
      cnt[u] += cnt[v];
      sumDist[u] += sumDist[v] + cnt[v];
      for (int i = 0; i < m; ++i) if (sumRem[i][u] > 0) {
        for (int j = 0; j < m; ++j) if (sumRem[j][v] > 0) {
          int k = i + j + 1;
          if (k >= m) k -= m;
          res -= (k == 0 ? m : k) * sumRem[i][u] * (long) sumRem[j][v];
        }
      }
      for (int i = 0; i < m; ++i) {
        sumRem[nxtRem(i)][u] += sumRem[i][v];
      }
    }
  }

  int nxtRem(int rem) {
    ++rem;
    return rem == m ? 0 : rem;
  }
}
