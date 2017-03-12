package main;

import template.collections.disjointset.DisjointSet;
import template.io.QuickScanner;
import template.io.QuickWriter;

public class TaskC {
  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    int n = in.nextInt();
    DisjointSet dset = new DisjointSet(n);
    for (int i = 0; i < n; ++i) {
      dset.setFriend(i, in.nextInt() - 1);
    }
    out.println(dset.setCnt);
  }
}
