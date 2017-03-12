package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

public class TaskA {
  int n;
  int[] a;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    n = in.nextInt();
    a = in.nextInt(n);
    out.println(calc());
  }

  String calc() {
    if (a[n - 1] == 0) return "UP";
    if (a[n - 1] == 15) return "DOWN";
    if (n > 1) {
      return a[n - 2] < a[n - 1] ? "UP" : "DOWN";
    }
    return "-1";
  }
}
