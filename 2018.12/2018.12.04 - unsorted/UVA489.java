package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

public class UVA489 {
  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    while (true) {
      int taskIdx = in.nextInt();
      if (taskIdx == -1) {
        break;
      }
      int mask = 0;
      for (char c : in.next().toCharArray()) {
        mask |= 1 << (c - 'a');
      }
      int remainMask = mask;
      int strokeCnt = 0;
      boolean isWin = false;
      boolean isLose = false;
      for (char c : in.next().toCharArray()) {
        int cMask = 1 << (c - 'a');
        if ((mask & cMask) > 0) {
          remainMask = remainMask & ~cMask;
          if (remainMask == 0) {
            isWin = true;
            break;
          }
        } else {
          ++strokeCnt;
          if (strokeCnt == 7) {
            isLose = true;
            break;
          }
        }
      }
      out.printf("Round %d\n", taskIdx);
      if (isWin) {
        out.println("You win.");
      } else if (isLose) {
        out.println("You lose.");
      } else {
        out.println("You chickened out.");
      }
    }
  }
}
