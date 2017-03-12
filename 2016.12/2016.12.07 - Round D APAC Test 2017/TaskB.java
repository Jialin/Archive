package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

public class TaskB {
  public void solve(int taskIdx, QuickScanner in, QuickWriter out) {
    System.err.printf("Handling #%d\n", taskIdx);
//    int limit = 27;
//    for (int n = 1; n <= limit; ++n) for (int m = n; m <= limit; ++m) if (n * m <= limit) {
//      System.out.printf("n:%d m:%d res:%d\n", n, m, calc(n, m));
//    }
    int n = in.nextInt();
    int m = in.nextInt();
    out.printf("Case #%d: %d\n", taskIdx, calc(n, m));
  }

  int calc(int n, int m) {
    if (n < m) {
      return calc(m, n);
    }
    if (m <= 2) {
      return (n - n / 3) * m;
    }
    int res = 0;
    for (int delta = 0; delta < 3; ++delta) {
      int currentRes = 0;
      for (int i = 0; i < n; ++i) {
        int currentDelta = (delta + i) % 3;
        currentRes += m - 1 - (m - currentDelta - 1) / 3;
      }
      res = Math.max(res, currentRes);
    }
    return res;
  }
}
