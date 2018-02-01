package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

public class TaskC {
  int n, r;
  int[] xs;
  double[] ys;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    n = in.nextInt();
    r = in.nextInt();
    int r2 = r << 1;
    xs = in.nextInt(n);
    ys = new double[n];
    for (int i = 0; i < n; ++i) {
      double res = r;
      for (int j = 0; j < i; ++j) if (Math.abs(xs[i] - xs[j]) <= r2) {
        double deltaY = Math.sqrt(r2 * r2 - (xs[i] - xs[j]) * (xs[i] - xs[j]));
        res = Math.max(res, ys[j] + deltaY);
      }
      ys[i] = res;
    }
    for (int i = 0; i < n; ++i) {
      if (i > 0) out.print(' ');
      out.print(ys[i]);
    }
    out.println();
  }
}
