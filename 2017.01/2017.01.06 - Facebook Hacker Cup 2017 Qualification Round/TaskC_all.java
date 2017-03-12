package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

import java.util.Arrays;

public class TaskC_all {
  static int MAXN = 400 + 1;

  double[] p = new double[MAXN];
  int X, Y, Z;

  public void solve(int _, QuickScanner in, QuickWriter out) {
    int taskCnt = in.nextInt();
    for (int taskIdx = 1; taskIdx <= taskCnt; ++taskIdx) {
      System.err.printf("Handling #%d\n", taskIdx);
      double res = 0;
      int H = in.nextInt();
      for (int rem = in.nextInt(); rem > 0; --rem) {
        String[] s = in.next().split("d");
        X = Integer.valueOf(s[0]);
        parse(s[1]);
        Arrays.fill(p, 0);
        p[0] = 1;
        for (int x = 0; x < X; ++x) {
          for (int y = MAXN - Y - 1; y >= 0; --y) {
            double delta = p[y] / Y;
            for (int i = 0, j = y + 1; i < Y; ++i, ++j) {
              p[j] += delta;
            }
            p[y] = 0;
          }
        }
        double curRes = 0;
        for (int i = 0; i < MAXN; ++i) if (i + Z >= H) {
          curRes += p[i];
        }
        res = Math.max(res, curRes);
      }
      out.printf("Case #%d: %.9f\n", taskIdx, res);
    }
  }

  void parse(String s) {
    int idx = s.indexOf('+');
    if (idx >= 0) {
      Y = Integer.valueOf(s.substring(0, idx));
      Z = Integer.valueOf(s.substring(idx));
      return;
    }
    idx = s.indexOf('-');
    if (idx >= 0) {
      Y = Integer.valueOf(s.substring(0, idx));
      Z = Integer.valueOf(s.substring(idx));
      return;
    }
    Y = Integer.valueOf(s);
    Z = 0;
  }
}
