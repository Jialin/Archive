package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

public class TaskA {
  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    int n = in.nextInt();
    int res = 1;
    for (int i = 2; i <= n / i; ++i) if ((n % i) == 0) {
      res = i;
    }
    out.println(res, n / res);
  }
}
