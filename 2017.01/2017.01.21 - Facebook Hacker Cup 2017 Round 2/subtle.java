package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

public class subtle {
  static final int INF = Integer.MAX_VALUE;

  int n, m, k;

  public void solve(int taskIdx, QuickScanner in, QuickWriter out) {
    System.err.printf("Handling #%d\n", taskIdx);
    n = in.nextInt();
    m = in.nextInt();
    k = in.nextInt();
    out.printf("Case #%d: %d\n", taskIdx, calc());
  }

  int calc() {
    int res = Math.min(
        Math.min(calcPlan1(n, m), calcPlan1(m, n)),
        Math.min(calcPlan2(n, m), calcPlan2(m, n)));
    return res == INF ? -1 : res;
  }

  /**
   * .
   * xxxx
   *
   *     x
   *     .
   */
  int calcPlan1(int n, int m) {
    if (n < 2 * k + 3) return INF;
    if (m <= k) return INF;
    return (m + k - 1) / k;
  }

  /**
   * . x
   * x x
   *   x
   *  x
   */
  int calcPlan2(int n, int m) {
    if (k == 1) {
      if (n < 5 || m < 3) return INF;
      return 5;
    }
    if (n < 2 * k + 3 || m < 2 * k + 1) return INF;
    return 4;
  }
}
