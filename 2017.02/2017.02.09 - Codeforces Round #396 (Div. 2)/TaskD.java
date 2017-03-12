package main;

import template.array.LongArrayUtils;
import template.collections.disjointset.DisjointSet;
import template.io.QuickScanner;
import template.io.QuickWriter;
import template.numbertheory.hash.HashUtils;

public class TaskD {
  char[] token = new char[20];

  QuickScanner in;
  int n, m, k;
  long[] hashs;
  DisjointSet dset;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    this.in = in;
    n = in.nextInt();
    m = in.nextInt();
    k = in.nextInt();
    hashs = new long[n];
    for (int i = 0; i < n; ++i) {
      hashs[i] = getHash();
    }
    LongArrayUtils.sort(hashs);
    dset = new DisjointSet(n << 1);
    for (int i = 0; i < m; ++i) {
      int op = in.nextInt();
      int x = getIndex();
      int y = getIndex();
      if (op == 1) {
        if (dset.isFriend(x, y + n)) {
          out.println("NO");
          continue;
        }
        out.println("YES");
        dset.setFriend(x, y);
        dset.setFriend(x + n, y + n);
      } else {
        if (dset.isFriend(x, y)) {
          out.println("NO");
          continue;
        }
        out.println("YES");
        dset.setFriend(x, y + n);
        dset.setFriend(x + n, y);
      }
    }
    for (int i = 0; i < k; ++i) {
      int x = getIndex();
      int y = getIndex();
      if (dset.isFriend(x, y)) {
        out.println(1);
      } else if (dset.isFriend(x, y + n)) {
        out.println(2);
      } else {
        out.println(3);
      }
    }
  }

  long getHash() {
    int n = in.next(token);
    return HashUtils.calc(token, 0, n);
  }

  int getIndex() {
    return LongArrayUtils.lowerBound(hashs, getHash());
  }
}
