package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

public class TaskA {
  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    int n = in.nextInt();
    for (int i = 0; i + 1 < n; ++i) {
      if (i > 0) out.print(' ');
      if ((i & 1) > 0) {
        out.print("I love that");
      } else {
        out.print("I hate that");
      }
    }
    if (n > 1) out.print(' ');
    if ((n & 1) > 0) {
      out.println("I hate it");
    } else {
      out.println("I love it");
    }
  }
}
