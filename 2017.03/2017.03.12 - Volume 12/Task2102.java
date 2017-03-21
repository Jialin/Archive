package main;

import template.io.QuickScanner;
import template.io.QuickWriter;
import template.numbertheory.number.LongUtils;

public class Task2102 {
  long[] p = new long[32];
  int[] r = new int[32];

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    int m = LongUtils.factorize(in.nextLong(), p, r);
    int res = 0;
    for (int i = 0; i < m; ++i) {
      res += r[i];
    }
    out.println(res == 20 ? "Yes" : "No");
  }
}
