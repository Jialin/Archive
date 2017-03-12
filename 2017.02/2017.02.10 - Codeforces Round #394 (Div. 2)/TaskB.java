package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

public class TaskB {
  int n, L;
  int[] a, b;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    n = in.nextInt();
    L = in.nextInt();
    a = in.nextInt(n);
    b = in.nextInt(n);
    out.println(possible() ? "YES" : "NO");
  }

  boolean possible() {
    for (int i = 0; i < n; ++i) {
      int delta = a[0] - b[i];
      if (delta < 0) delta += L;
      boolean possible = true;
      for (int j = i, k = 0; k < n; j = j + 1 == n ? 0 : j + 1, ++k) {
        possible &= (b[j] + delta >= L ? b[j] + delta - L : b[j] + delta) == a[k];
      }
      if (possible) return true;
    }
    return false;
  }
}
