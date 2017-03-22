package main;

import template.collections.disjointset.DisjointSet;
import template.io.QuickScanner;
import template.io.QuickWriter;

public class TaskB {
  int n, m;
  DisjointSet dset;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    n = in.nextInt();
    m = in.nextInt();
    dset = new DisjointSet(n);
    for (int i = 0; i < m; ++i) {
      dset.setFriend(in.nextInt() - 1, in.nextInt() - 1);
    }
    long res = 0;
    for (int i = 0; i < n; ++i) if (dset.parent[i] < 0) {
      int size = -dset.parent[i];
      res += size * (size - 1L) / 2;
    }
    out.println(res == m ? "YES" : "NO");
  }
}
