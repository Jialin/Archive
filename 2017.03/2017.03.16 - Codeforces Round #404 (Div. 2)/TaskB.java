package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

public class TaskB {
  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    int n = in.nextInt();
    int minR1 = Integer.MAX_VALUE, maxL1 = 0;
    for (int i = 0; i < n; ++i) {
      maxL1 = Math.max(maxL1, in.nextInt());
      minR1 = Math.min(minR1, in.nextInt());
    }
    int m = in.nextInt();
    int minR2 = Integer.MAX_VALUE, maxL2 = 0;
    for (int i = 0; i < m; ++i) {
      maxL2 = Math.max(maxL2, in.nextInt());
      minR2 = Math.min(minR2, in.nextInt());
    }
    out.println(Math.max(Math.max(maxL1 - minR2, maxL2 - minR1), 0));
  }
}
