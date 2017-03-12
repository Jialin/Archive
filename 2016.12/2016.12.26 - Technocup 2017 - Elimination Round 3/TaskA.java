package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

public class TaskA {
  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    int n = in.nextInt();
    int m = in.nextInt();
    int k = in.nextInt() - 1;
    out.printf("%d %d %c\n", k / 2 / m + 1, k / 2 % m + 1, k % 2 > 0 ? 'R' : 'L');
  }
}
