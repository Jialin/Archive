package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

public class TaskA {
  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    int x = in.nextInt();
    for ( ; x % 10 == 0; x /= 10) {}
    String forward = String.valueOf(x);
    String backward = new StringBuilder(forward).reverse().toString();
    out.println(forward.equals(backward) ? "YES" : "NO");
  }
}
