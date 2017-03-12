package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

public class TaskA {
  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    int k = in.nextInt();
    int r = in.nextInt();
    for (int i = 1; i <= 10; ++i) {
      int rem = k * i % 10;
      if (rem == 0 || rem == r) {
        out.println(i);
        break;
      }
    }
  }
}
