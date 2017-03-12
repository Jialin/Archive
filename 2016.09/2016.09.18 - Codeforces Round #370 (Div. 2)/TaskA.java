package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

public class TaskA {
  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    int n = in.nextInt();
    int[] b = in.nextInt(n);
    int[] a = new int[n];
    for (int i = n - 1; i >= 0; --i) {
      a[i] = b[i] + (i == n - 1 ? 0 : b[i + 1]);
    }
    out.println(a);
  }
}
