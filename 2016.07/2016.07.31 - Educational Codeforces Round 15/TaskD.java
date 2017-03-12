package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

import static java.lang.Math.min;

public class TaskD {
  long d, k, a, b, t;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    d = in.nextLong();
    k = in.nextLong();
    a = in.nextLong();
    b = in.nextLong();
    t = in.nextLong();
    out.println(calc());
  }

  long calc() {
    if (d <= k) return d * a;
    long res = k * a;
    d -= k;
    if (k * b <= t + k * a) {
      return res + d * b;
    }
    long loop = d / k;
    d %= k;
    res += loop * (t + k * a);
    return res + min(d * b, t + d * a);
  }
}
