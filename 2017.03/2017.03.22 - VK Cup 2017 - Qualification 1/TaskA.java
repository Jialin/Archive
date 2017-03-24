package main;

import template.array.IntArrayUtils;
import template.io.QuickScanner;
import template.io.QuickWriter;

public class TaskA {
  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    int n = in.nextInt();
    int[] a = in.nextInt(n);
    IntArrayUtils.sort(a);
    out.println(a[n >> 1]);
  }
}
