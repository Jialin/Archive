package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

import static java.lang.Long.min;

public class TaskC {
  static final long INF = Long.MAX_VALUE;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    long a = in.nextLong();
    long b = in.nextLong();
    long c = in.nextLong();
    out.println(min(min(
        calc(a, b, c),
        calc(b, c, a)),
        calc(c, a, b)));
  }

  long calc(long a, long b, long c) {
    return min(min(min(min(min(
        calc(a, b, c, a - 1),
        calc(a, b, c, a)),
        calc(a, b, c, b - 1)),
        calc(a, b, c, b)),
        calc(a, b, c, c - 1)),
        calc(a, b, c, c));
  }

  long calc(long a, long b, long c, long x) {
    if (x < 0) return INF;
    return min(min(
        calc(a, b, c, x, x, x),
        calc(a, b, c, x + 1, x, x)),
        calc(a, b, c, x + 1, x + 1, x));
  }

  long calc(long a, long b, long c, long x, long y, long z) {
    if (a > x || b > y || c > z) return INF;
    return x - a + y - b + z - c;
  }
}
