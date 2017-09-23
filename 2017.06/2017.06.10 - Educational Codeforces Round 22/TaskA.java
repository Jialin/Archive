package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

public class TaskA {
  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    int thinkTime = 0;
    for (int i = in.nextInt(); i > 0; --i) {
      thinkTime += in.nextInt();
    }
    int res = -1;
    for (int i = in.nextInt(); i > 0; --i) {
      int lower = in.nextInt();
      int upper = in.nextInt();
      if (lower <= thinkTime && thinkTime <= upper) {
        res = thinkTime;
        break;
      }
      if (thinkTime <= lower) {
        res = lower;
        break;
      }
    }
    out.println(res);
  }
}
