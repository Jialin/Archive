package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

public class TaskC {
  long n, k;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    n = in.nextLong();
    k = in.nextLong();
    long res = -1;
    for (int i = 1; i <= n / i; ++i) if ((n % i) == 0) {
      if (valid(i)) res = Math.max(res, n / i);
      if (valid(n / i)) res = Math.max(res, i);
    }
    if (res < 0) {
      out.println(-1);
    } else {
      long delta = calc(n / res);
      long j = res;
      for (int i = 1; i < k; ++i, j += res) {
        out.print(j);
        out.print(' ');
      }
      out.println((k + delta) * res);
    }
  }

  boolean valid(long sum) {
    if (k >= (sum << 1) / k) return false;
    return ((k * (k + 1)) >> 1) <= sum;
  }

  long calc(long sum) {
    return sum - ((k * (k + 1)) >> 1);
  }
}
