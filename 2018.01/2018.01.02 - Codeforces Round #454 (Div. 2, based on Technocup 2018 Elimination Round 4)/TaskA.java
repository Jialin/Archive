package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

public class TaskA {
  int a1, a2, a3, am;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    a1 = in.nextInt();
    a2 = in.nextInt();
    a3 = in.nextInt();
    am = in.nextInt();
    calc(out);
  }

  void calc(QuickWriter out) {
    for (int b3 = a3 << 1; b3 >= a3; --b3) {
      for (int b2 = a2 << 1; b2 >= Math.max(a2, b3 + 1); --b2) {
        for (int b1 = a1 << 1; b1 >= Math.max(a1, b2 + 1); --b1) {
          if (fitAndLike(b3) && fitButNotLike(b2) && fitButNotLike(b1)) {
            out.println(b1);
            out.println(b2);
            out.println(b3);
            return;
          }
        }
      }
    }
    out.println(-1);
  }

  boolean fitAndLike(int s) {
    return am <= s && s <= (am << 1);
  }

  boolean fitButNotLike(int s) {
    return (am << 1) < s;
  }
}
