package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

public class TaskA {
  static int m = 5;

  QuickScanner in;
  int n;
  int[] cntA, cntB;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    n = in.nextInt();
    cntA = new int[m];
    cntB = new int[m];
    this.in = in;
    read(cntA);
    read(cntB);
    out.println(calc());
  }

  void read(int[] cnt) {
    for (int i = 0; i < n; ++i) {
      ++cnt[in.nextInt() - 1];
    }
  }

  int calc() {
    int res = 0;
    for (int i = 0; i < m; ++i) {
      int diff = Math.abs(cntA[i] - cntB[i]);
      if ((diff & 1) > 0) return -1;
      res += diff;
    }
    return res >> 2;
  }
}
