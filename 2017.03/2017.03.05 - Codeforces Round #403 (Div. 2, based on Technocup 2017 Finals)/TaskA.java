package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

public class TaskA {
  int n;
  int res, cnt;
  boolean[] took;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    res = -1;
    cnt = 0;
    n = in.nextInt();
    took = new boolean[n];
    for (int i = 0; i < n << 1; ++i) {
      int idx = in.nextInt() - 1;
      if (took[idx]) {
        --cnt;
      } else {
        took[idx] = true;
        ++cnt;
        res = Math.max(res, cnt);
      }
    }
    out.println(res);
  }
}
