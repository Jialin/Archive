package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

public class TaskA {
  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    int n = in.nextInt();
    int rem = (240 - in.nextInt()) / 5;
    int res = 0;
    for (int i = 1; i <= n && i <= rem; ++i, ++res) {
      rem -= i;
    }
    out.println(res);
  }
}
