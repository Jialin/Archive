package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

public class TaskA {
  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    int a = in.nextInt();
    int b = in.nextInt();
    int res;
    for (res = 0; a <= b; ++res) {
      a *= 3;
      b <<= 1;
    }
    out.println(res);
  }
}
