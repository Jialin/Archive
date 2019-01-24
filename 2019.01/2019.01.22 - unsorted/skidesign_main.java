package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

/*
ID: ouyang.ji1
LANG: JAVA
TASK: skidesign
*/
public class skidesign_main {
  int n;
  int[] hs;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    n = in.nextInt();
    hs = in.nextInt(n);
    int res = Integer.MAX_VALUE;
    for (int minH = 0; minH <= 100; ++minH) {
      res = Math.min(res, calc(minH));
    }
    out.println(res);
  }

  int calc(int minH) {
    int maxH = minH + 17;
    int res = 0;
    for (int h : hs) {
      if (h < minH) {
        res += sqr(minH - h);
      } else {
        res += sqr(Math.max(h - maxH, 0));
      }
    }
    return res;
  }

  int sqr(int x) {
    return x * x;
  }
}
