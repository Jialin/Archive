package main;

import template.io.QuickScanner;
import template.io.QuickWriter;
import template.numbertheory.number.IntUtils;

public class TaskA {
  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    int n = in.nextInt();
    int m = in.nextInt();
    out.println(in.nextInt() / IntUtils.lcm(n, m));
  }
}
