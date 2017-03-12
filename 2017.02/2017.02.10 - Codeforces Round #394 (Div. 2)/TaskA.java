package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

public class TaskA {
  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    int a = in.nextInt();
    int b = in.nextInt();
    out.println(a + b > 0 && Math.abs(a - b) <= 1 ? "YES" : "NO");
  }
}
