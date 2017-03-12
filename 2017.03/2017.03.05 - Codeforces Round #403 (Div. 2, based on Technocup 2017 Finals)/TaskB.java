package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

public class TaskB {
  int n;
  int[] x, v;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    n = in.nextInt();
    x = new int[n];
    v = new int[n];
    for (int i = 0; i < n; ++i) x[i] = in.nextInt();
    for (int i = 0; i < n; ++i) v[i] = in.nextInt();
    double lower = 0, upper = 1E10;
    for (int loop = 0; loop < 100; ++loop) {
      double medium = (lower + upper) / 2;
      double minRight = 1E100, maxLeft = -1E100;
      for (int i = 0; i < n; ++i) {
        minRight = Math.min(minRight, x[i] + v[i] * medium);
        maxLeft = Math.max(maxLeft, x[i] - v[i] * medium);
      }
      if (maxLeft <= minRight) {
        upper = medium;
      } else {
        lower = medium;
      }
    }
    out.println((lower + upper) / 2);
  }
}
