package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

public class TaskC {
  int length;
  int tankSize;
  int oilPos;
  int loop;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    length = in.nextInt();
    tankSize = in.nextInt();
    oilPos = in.nextInt();
    loop = in.nextInt();
    out.println(calc());
  }

  int calc() {
    int res = 0;
    int remOil = tankSize;
    boolean forward = true;
    for (int i = 0; i < loop; ++i, forward = !forward) {
      int toOilStop = forward ? oilPos : length - oilPos;
      if (remOil < toOilStop) return -1;
      remOil -= toOilStop;
      int toOtherSide = length - toOilStop;
      if (remOil < toOtherSide) {
        ++res;
        remOil = tankSize - toOtherSide;
        if (remOil < 0) return -1;
      } else {
        remOil -= toOtherSide;
        if (i == loop - 1) break;
        if (remOil < toOtherSide) {
          ++res;
          remOil = tankSize - toOtherSide;
          if (remOil < 0) return -1;
        }
      }
    }
    return res;
  }
}
