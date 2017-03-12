package main;

import template.array.IntArrayUtils;
import template.io.QuickScanner;
import template.io.QuickWriter;

public class TaskB {
  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    int n = in.nextInt();
    int[] x = in.nextInt(n);
    out.println(IntArrayUtils.kth(x, (n - 1) >> 1));
  }
}
