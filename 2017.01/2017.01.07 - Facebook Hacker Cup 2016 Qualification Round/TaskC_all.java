package main;

import template.array.LongArrayUtils;
import template.io.QuickScanner;
import template.io.QuickWriter;

public class TaskC_all {
  static int MAXN = 100000;

  int n, p;
  long[] b = new long[MAXN];

  public void solve(int unusedTaskIdx, QuickScanner in, QuickWriter out) {
    int taskCnt = in.nextInt();
    for (int taskIdx = 1; taskIdx <= taskCnt; ++taskIdx) {
      System.err.printf("Handling #%d\n", taskIdx);
      n = in.nextInt();
      p = in.nextInt();
      in.nextLong(n, b);
      for (int i = 1; i < n; ++i) {
        b[i] += b[i - 1];
      }
      long res = 0;
      for (int i = 0; i < n; ++i) {
        int j = LongArrayUtils.upperBound(b, i, n, p + (i > 0 ? b[i - 1] : 0));
        res += j - i;
      }
      out.printf("Case #%d: %d\n", taskIdx, res);
    }
  }
}
