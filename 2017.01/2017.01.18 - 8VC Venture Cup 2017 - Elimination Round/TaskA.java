package main;

import template.io.QuickScanner;
import template.io.QuickWriter;
import template.numbertheory.number.IntUtils;

public class TaskA {
  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    int n = in.nextInt();
    for (int m = 1; m <= 1000; ++m) if (!IntUtils.isPrime(n * m + 1)) {
      out.println(m);
      break;
    }
  }
}
