package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

public class TaskA_per {
  int bCnt;
  String s;

  public void solve(int taskIdx, QuickScanner in, QuickWriter out) {
    System.err.printf("Handling #%d\n", taskIdx);
    s = in.next();
    bCnt = 0;
    for (int i = 0; i < s.length(); ++i) if (s.charAt(i) == 'B') {
      ++bCnt;
    }
    long lower = in.nextLong();
    long upper = in.nextLong();
    out.printf("Case #%d: %d\n", taskIdx, calc(upper) - calc(lower - 1));
  }

  long calc(long bound) {
    if (bound == 0) return 0;
    long res = bound / s.length() * bCnt;
    for (int i = (int) (bound % s.length()) - 1; i >= 0; --i) if (s.charAt(i) == 'B') {
      ++res;
    }
    return res;
  }
}
