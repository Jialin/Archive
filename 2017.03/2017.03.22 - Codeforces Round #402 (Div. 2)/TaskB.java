package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

public class TaskB {
  int n, m;
  char[] s;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    s = new char[16];
    n = in.next(s);
    m = in.nextInt();
    out.println(calc());
  }

  int calc() {
    int res = 0;
    for (int i = n - 1; i >= 0; --i) {
      if (s[i] == '0') {
        --m;
        if (m == 0) return res;
      } else {
        ++res;
      }
    }
    return n - 1;
  }
}
