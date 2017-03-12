package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

import static java.lang.Math.max;

public class TaskA {
  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    int n = in.nextInt();
    int res = 0, currentCnt = 0, last = Integer.MAX_VALUE;
    for (int i = 0; i < n; ++i) {
      int v = in.nextInt();
      if (last < v) {
        ++currentCnt;
      } else {
        currentCnt = 0;
      }
      res = max(res, currentCnt);
      last = v;
    }
    out.println(res + 1);
  }
}
