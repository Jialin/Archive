package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

public class TaskC {
  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    long n = in.nextLong();
    long a = 1, b = 2;
    int res = 0;
    while (b <= n) {
      ++res;
      long c = a + b;
      a = b;
      b = c;
    }
    out.println(res);
  }
}
