package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

public class TaskA {
  public void solve(int taskIdx, QuickScanner in, QuickWriter out) {
    System.err.printf("Handling #%d\n", taskIdx);
    int n = in.nextInt();
    int m = in.nextInt();
    out.printf("Case #%d: %.8f\n", taskIdx, (double) (n - m) / (n + m));
  }
}
