package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

public class TaskD {
  static int OFFSET = 1000000000;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    int n = in.nextInt();
    out.println("YES");
    for (int i = 0; i < n; ++i) {
      int x = (in.nextInt() + OFFSET) & 1;
      int y = (in.nextInt() + OFFSET) & 1;
      in.nextInt();
      in.nextInt();
      out.println(((x << 1) | y) + 1);
    }
  }
}
