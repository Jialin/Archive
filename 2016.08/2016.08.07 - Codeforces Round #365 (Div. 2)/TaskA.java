package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

public class TaskA {
  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    int winM = 0, winC = 0;
    for (int i = in.nextInt(); i > 0; --i) {
      int m = in.nextInt();
      int c = in.nextInt();
      if (c > m) ++winC;
      else if (m > c) ++winM;
    }
    if (winM > winC) out.println("Mishka");
    else if (winC > winM) out.println("Chris");
    else out.println("Friendship is magic!^^");
  }
}
