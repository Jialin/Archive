package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

public class TaskA {
  static int MAXN = 100000;

  int n, m;
  char[] a = new char[MAXN];
  char[] b = new char[MAXN];

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    n = in.next(a);
    m = in.next(b);
    out.println(isSame() ? -1 : Math.max(n, m));
  }

  boolean isSame() {
    if (n != m) return false;
    for (int i = 0; i < n; ++i) if (a[i] != b[i]) {
      return false;
    }
    return true;
  }
}
