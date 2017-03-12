package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

public class TaskA {
  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    int n = in.nextInt();
    int m = in.nextInt();
    boolean bw = true;
    for (int i = 0; i < n; ++i) for (int j = 0; j < m; ++j) {
      char c = in.next().charAt(0);
      if (c != 'W' && c != 'B' && c != 'G') {
        bw = false;
      }
    }
    out.println(bw ? "#Black&White" : "#Color");
  }
}
