package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

public class TaskA {
  int n, k;
  double l, v1, v2;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    n = in.nextInt();
    l = in.nextInt();
    v1 = in.nextInt();
    v2 = in.nextInt();
    k = in.nextInt();
    out.printf("%.10f\n", calc());
  }

  double calc() {
    int round = (n - 1) / k;
    double lower = l / v2, upper = l / v1;
    for (int loop = 0; loop < 100; ++loop) {
      double medium = (lower + upper) / 2;
      double t = (l - v1 * medium) / (v2 - v1);
      double t0 = (v2 - v1) * t / (v1 + v2) + t;
      if (t0 * round * v1 + t * v2 <= l) {
        upper = medium;
      } else {
        lower = medium;
      }
    }
    return (lower + upper) / 2;
  }
}
