package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

public class TaskB {
  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    long a = in.nextLong();
    long b = in.nextLong();
    long res = 1;
    for (long i = a + 1; i <= b; ++i) {
      res = res * i % 10;
      if (res == 0) break;
    }
    out.println(res);
  }
}
