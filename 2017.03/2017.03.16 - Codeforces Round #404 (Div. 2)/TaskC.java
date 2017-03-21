package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

public class TaskC {
  long n, m;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    n = in.nextLong();
    m = in.nextLong();
    out.println(calc());
  }

  long calc() {
    if (n <= m) return n;
    long bound = (n - m) << 1;
    long lower = 1, upper = n, res = 0;
    while (lower <= upper) {
      long medium = (lower + upper) >> 1;
      if (medium + 1 <= bound / medium && medium * (medium + 1) < bound) {
        lower = medium + 1;
        res = medium;
      } else {
        upper = medium - 1;
      }
    }
    return res + 1 + m;
  }
}
