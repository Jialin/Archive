package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

public class TaskB {
  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    int n = in.nextInt();
    int k = in.nextInt();
    int[] a = in.nextInt(n);
    int res = 0;
    for (int i = 1; i < n; ++i) {
      int delta = k - a[i - 1] - a[i];
      if (delta < 0) continue;
      res += delta;
      a[i] += delta;
    }
    out.println(res);
    out.println(a);
  }
}
