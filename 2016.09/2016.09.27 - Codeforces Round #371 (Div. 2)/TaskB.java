package main;

import template.array.IntArrayUtils;
import template.io.QuickScanner;
import template.io.QuickWriter;

import java.util.Arrays;

public class TaskB {
  int n, m;
  int[] a;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    n = in.nextInt();
    a = in.nextInt(n);
    Arrays.sort(a);
    m = IntArrayUtils.unique(a);
    out.println(possible() ? "YES" : "NO");
  }

  boolean possible() {
    if (m < 3) return true;
    if (m > 3) return false;
    return a[1] - a[0] == a[2] - a[1];
  }
}
