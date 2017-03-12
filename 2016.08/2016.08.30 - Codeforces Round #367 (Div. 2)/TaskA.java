package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

import static java.lang.Math.hypot;
import static java.lang.Math.min;

public class TaskA {
  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    int a = in.nextInt();
    int b = in.nextInt();
    double res = Double.MAX_VALUE;
    for (int i = in.nextInt(); i > 0; --i) {
      int x = in.nextInt();
      int y = in.nextInt();
      int v = in.nextInt();
      res = min(res, hypot(a - x, b - y) / v);
    }
    out.printf("%.13f\n", res);
  }
}
