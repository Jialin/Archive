package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

import java.util.Arrays;

public class TaskB {
  int n;
  int[] w;

  public void solve(int taskIdx, QuickScanner in, QuickWriter out) {
    System.err.printf("Handling #%d\n", taskIdx);
    n = in.nextInt();
    w = in.nextInt(n);
    Arrays.sort(w);
    int pnt = 0, res = 0;
    for (int i = n - 1; i >= 0; --i) {
      int cnt = 49 / w[i];
      if (cnt > i - pnt) break;
      pnt += cnt;
      ++res;
    }
    out.printf("Case #%d: %d\n", taskIdx, res);
  }
}
