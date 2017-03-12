package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

import static java.lang.Math.abs;
import static java.lang.Math.min;

public class TaskE {
  static int maxDouble = 26;

  int n;
  long x, y;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    n = in.nextInt();
    x = in.nextInt();
    y = in.nextInt();
    long res = x * n;
    for (int d = 1; d < maxDouble; ++d) {
      int bit = n / (1 << d);
      res = min(min(
          res,
          x * (bit + calc(abs(n - (1 << d) * bit))) + y * d),
          x * (bit + 1 + calc(abs(n - (1 << d) * (bit + 1)))) + y * d);
    }
    out.println(res);
  }

  int calc(int x) {
    int res = 0;
    while (x > 0) {
      if ((x & 1) == 0) {
        x >>= Integer.numberOfTrailingZeros(x);
        continue;
      }
      ++res;
      if ((x & 2) == 0) {
        x >>= 1;
        continue;
      }
      ++x;
    }
    return res;
  }
}
