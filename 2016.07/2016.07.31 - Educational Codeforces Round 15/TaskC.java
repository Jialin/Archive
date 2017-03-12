package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

import static java.lang.Integer.max;
import static java.lang.Math.abs;
import static java.lang.Math.min;

public class TaskC {
  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    int n = in.nextInt();
    int m = in.nextInt();
    int[] a = in.nextInt(n);
    int[] b = in.nextInt(m);
    int res = 0;
    int j = 0;
    for (int i = 0; i < n; ++i) {
      for ( ; j < m && b[j] < a[i]; ++j) ;
      int subRes;
      if (j == 0) {
        subRes = abs(a[i] - b[0]);
      } else if (j == m) {
        subRes = abs(a[i] - b[m - 1]);
      } else {
        subRes = min(abs(a[i] - b[j - 1]), abs(a[i] - b[j]));
      }
      res = max(res, subRes);
    }
    out.println(res);
  }
}
