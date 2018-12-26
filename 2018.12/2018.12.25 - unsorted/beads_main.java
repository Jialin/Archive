package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

/*
ID: ouyang.ji1
LANG: JAVA
TASK: beads
*/
public class beads_main {
  int n, n2;
  char[] beads;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    n = in.nextInt();
    n2 = n << 1;
    beads = new char[n2];
    in.next(beads);
    for (int i = 0, j = n; i < n; ++i, ++j) {
      beads[j] = beads[i];
    }
    int res = 0;
    for (int i = 0; i < n; ++i) {
      int x = calc(i, 1);
      int y = calc(i == 0 ? n - 1 : i - 1, -1);
      res = Math.max(res, x + y);
    }
    out.println(Math.min(res, n));
  }

  int calc(int idx, int delta) {
    int res = 0;
    char c = 'w';
    for ( ; 0 <= idx && idx < n2; idx += delta, ++res) {
      if (c == 'w') {
        c = beads[idx];
      } else if (beads[idx] != 'w' && beads[idx] != c) {
        break;
      }
    }
    return res;
  }
}
