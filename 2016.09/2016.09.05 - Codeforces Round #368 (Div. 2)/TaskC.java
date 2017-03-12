package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

public class TaskC {
  long n, a, b;
  int bit;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    n = in.nextInt();
    for (bit = 0; (n & 1) == 0; n >>= 1, ++bit) ;
    calc();
    if (a > 0) {
      out.printf("%d %d\n", a << bit, b << bit);
    } else {
      out.println("-1");
    }
  }

  void calc() {
    if (n == 1) {
      if (bit < 2) {
        a = -1;
      } else {
        n = 4;
        bit -= 2;
        a = 3;
        b = 5;
      }
    } else {
      a = (n * n) >> 1;
      b = a + 1;
    }
  }
}
