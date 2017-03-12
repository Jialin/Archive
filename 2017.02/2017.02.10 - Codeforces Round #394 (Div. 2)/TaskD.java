package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

public class TaskD {
  int n, l, r;
  int[] a, b, p, pIdx;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    n = in.nextInt();
    l = in.nextInt();
    r = in.nextInt();
    a = in.nextInt(n);
    b = new int[n];
    p = new int[n];
    pIdx = new int[n];
    for (int i = 0; i < n; ++i) {
      p[i] = in.nextInt() - 1;
      pIdx[p[i]] = i;
    }
    if (found()) {
      out.println(b);
    } else {
      out.println(-1);
    }
  }

  boolean found() {
    int lastC = -1;
    for (int i = 0; i < n; ++i) {
      int j = pIdx[i];
      int c = i > 0 ? Math.max(lastC + 1, l - a[j]) : l - a[j];
      b[j] = c + a[j];
      if (b[j] > r) return false;
      lastC = c;
    }
    return true;
  }
}
