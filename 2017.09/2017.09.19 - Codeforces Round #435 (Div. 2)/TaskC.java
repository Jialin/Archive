package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

public class TaskC {
  static int OFFSET = 1 << 19;

  int n, x;
  int[] res;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    n = in.nextInt();
    x = in.nextInt();
    res = new int[n];
    if (calc()) {
      out.println("YES");
      out.println(res);
    } else {
      out.println("NO");
    }
  }

  boolean calc() {
    if (n == 1) {
      res[0] = x;
      return true;
    }
    if (n == 2) {
      res[0] = 0;
      res[1] = x;
      return x > 0;
    }
    int xorSum = x;
    for (int i = 0; i < n - 3; ++i) {
      res[i] = i;
      xorSum ^= i;
    }
    if (xorSum == n - 3) {
      res[n - 3] = n - 2;
      xorSum ^= n - 2;
    } else {
      res[n - 3] = n - 3;
      xorSum ^= n - 3;
    }
    res[n - 2] = OFFSET;
    res[n - 1] = OFFSET ^ xorSum;
    return true;
  }
}
