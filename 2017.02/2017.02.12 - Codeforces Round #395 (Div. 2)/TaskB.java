package main;

import template.array.IntArrayUtils;
import template.io.QuickScanner;
import template.io.QuickWriter;

public class TaskB {
  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    int n = in.nextInt();
    int[] a = in.nextInt(n);
    for (int i = 0, j = n - 1; i < j; i += 2, j -= 2) {
      IntArrayUtils.swap(a, i, j);
    }
    out.println(a);
  }
}
