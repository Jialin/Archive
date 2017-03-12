package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

public class TaskA {
  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    long l1 = in.nextLong();
    long r1 = in.nextLong();
    long l2 = in.nextLong();
    long r2 = in.nextLong();
    long k = in.nextLong();
    long l = Math.max(l1, l2);
    long r = Math.min(r1, r2);
    long res = l <= k && k <= r ? -1 : 0;
    res += r - l + 1;
    out.println(Math.max(res, 0));
  }
}
