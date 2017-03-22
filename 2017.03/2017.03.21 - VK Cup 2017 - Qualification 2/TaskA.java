package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

public class TaskA {
  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    int n = in.nextInt();
    int k = in.nextInt();
    for (int i = 0, j = 0; i < n; ++i, j = j + 1 == k ? 0 : j + 1) {
      out.print((char) ('a' + j));
    }
    out.println();
  }
}
