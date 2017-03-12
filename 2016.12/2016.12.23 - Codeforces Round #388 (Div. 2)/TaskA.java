package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

public class TaskA {
  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    int n = in.nextInt();
    out.println(n % 2 > 0 ? (n - 3) / 2 + 1 : n / 2);
    boolean first = true;
    while (n > 0) {
      if (first) {
        first = false;
      } else {
        out.print(' ');
      }
      int value = (n & 1) > 0 ? 3 : 2;
      out.print(value);
      n -= value;
    }
  }
}
