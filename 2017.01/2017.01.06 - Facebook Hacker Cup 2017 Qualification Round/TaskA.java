package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

public class TaskA {
  int percent;
  int x;
  int y;

  public void solve(int taskIdx, QuickScanner in, QuickWriter out) {
    System.err.printf("Handling #%d\n", taskIdx);
    percent = in.nextInt();
    x = in.nextInt() - 50;
    y = in.nextInt() - 50;
    out.printf("Case #%d: %s\n", taskIdx, covered()? "black" : "white");
  }

  boolean covered() {
    if (percent == 0 || x * x + y * y > 2500) return false;
    if (percent == 100) return true;
    return calcPercent(x, y) <= percent;
  }

  double calcPercent(int x, int y) {
    return Math.atan2(-x, -y) / Math.PI * 50 + 50;
  }
}
