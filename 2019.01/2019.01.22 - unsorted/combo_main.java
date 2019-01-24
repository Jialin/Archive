package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

/*
ID: ouyang.ji1
LANG: JAVA
TASK: combo
*/
public class combo_main {
  int n;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    n = in.nextInt();
    int[] fj = in.nextInt(3);
    int[] master =  in.nextInt(3);
    out.println((calc() << 1) - calcBoth(fj, master));
  }

  int calc() {
    int subRes = Math.min(5, n);
    int res = 1;
    for (int i = 0; i < 3; ++i) {
      res *= subRes;
    }
    return res;
  }

  int calcBoth(int[] fj, int[] master) {
    int res = 1;
    for (int i = 0; i < 3; ++i) {
      int abs = Math.abs(fj[i] - master[i]);
      int gap = Math.min(abs, n - abs);
      // 0 -> 5
      // 1 -> 4
      // 2 -> 3
      // 3 -> 2
      // 4 -> 1
      // 5 -> 0
      res *= Math.min(Math.max(5 - gap, 0), n);
    }
    return res;
  }
}
