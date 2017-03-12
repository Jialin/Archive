package main;

import template.array.IntArrayUtils;
import template.io.QuickScanner;
import template.io.QuickWriter;

public class TaskB {
  int n;
  int[] a;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    n = in.nextInt();
    a = in.nextInt(n);
    IntArrayUtils.sort(a);
    out.println(found() ? "YES" : "NO");
  }

  boolean found() {
    for (int i = 2; i < n; ++i) if (a[i - 2] + a[i - 1] > a[i]) {
      return true;
    }
    return false;
  }
}
