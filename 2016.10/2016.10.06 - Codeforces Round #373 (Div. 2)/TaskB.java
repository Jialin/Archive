package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

public class TaskB {
  int n;
  String s;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    n = in.nextInt();
    s = in.next();
    out.println(Math.min(calc(0), calc(1)));
  }

  int calc(int idx) {
    int res1 = 0, res2 = 0;
    for (int i = idx; i < n; i += 2) if (s.charAt(i) != 'r') {
      ++res1;
    }
    for (int i = idx ^ 1; i < n; i += 2) if (s.charAt(i) != 'b') {
      ++res2;
    }
    return Math.max(res1, res2);
  }
}
