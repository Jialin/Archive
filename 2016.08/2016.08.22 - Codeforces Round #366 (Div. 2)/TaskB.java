package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

public class TaskB {
  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    int n = in.nextInt();
    int sg = 0;
    for (int i = 0; i < n; ++i) {
      sg ^= sg(in.nextInt());
      out.println(sg > 0 ? 1 : 2);
    }
  }

  int sg(int n) {
    return (n - 1) & 1;
  }
}
