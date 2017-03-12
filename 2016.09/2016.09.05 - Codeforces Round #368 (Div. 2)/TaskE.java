package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

import java.util.Arrays;

import static java.lang.Math.abs;
import static java.lang.Math.max;
import static java.lang.Math.min;

public class TaskE {
  static final int NINF = Integer.MIN_VALUE;

  QuickScanner in;
  Garland[] garlands;
  int n, m, k;
  int[][] tagGarland, tagIdx;
  boolean[] on;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    this.in = in;
    n = in.nextInt();
    m = in.nextInt();
    k = in.nextInt();
    tagGarland = new int[n + 2][m + 2];
    tagIdx = new int[n + 2][m + 2];
    for (int i = 0; i < tagGarland.length; ++i) {
      Arrays.fill(tagGarland[i], NINF);
      Arrays.fill(tagIdx[i], NINF);
    }
    garlands = new Garland[k];
    for (int i = 0; i < k; ++i) {
      garlands[i] = new Garland(i, in.nextInt());
    }
    on = new boolean[k];
    Arrays.fill(on, true);
    for (int remainQuery = in.nextInt(); remainQuery > 0; --remainQuery) {
      if (in.next().charAt(0) == 'S') {
        int idx = in.nextInt() - 1;
        on[idx] = !on[idx];
      } else {
        int x1 = in.nextInt();
        int y1 = in.nextInt();
        int x2 = in.nextInt();
        int y2 = in.nextInt();
        long res = 0;
        for (int i = 0; i < k; ++i)
          if (on[i] && garlands[i].inside(x1, y1, x2, y2)) {
            res += garlands[i].ps[garlands[i].n - 1];
          }
        for (int x = x1; x <= x2; ++x) {
          res += update(x, y1 - 1, x, y1);
          res += update(x, y2 + 1, x, y2);
        }
        for (int y = y1; y <= y2; ++y) {
          res += update(x1 - 1, y, x1, y);
          res += update(x2 + 1, y, x2, y);
        }
        out.println(res);
      }
    }
  }

  private long update(int x1, int y1, int x2, int y2) {
    if (tagGarland[x1][y1] < 0
        || tagGarland[x1][y1] != tagGarland[x2][y2]
        || !on[tagGarland[x1][y1]]
        || abs(tagIdx[x1][y1] - tagIdx[x2][y2]) != 1) return 0;
    return tagIdx[x1][y1] < tagIdx[x2][y2]
        ? -garlands[tagGarland[x1][y1]].ps[tagIdx[x1][y1]]
        : garlands[tagGarland[x1][y1]].ps[tagIdx[x2][y2]];
  }

  class Garland {
    int n;
    int minX, maxX, minY, maxY;
    int lastX, lastY;
    long[] ps;

    Garland(int idx, int n) {
      this.n = n;
      ps = new long[n];
      for (int i = 0; i < n; ++i) {
        lastX = in.nextInt();
        lastY = in.nextInt();
        tagGarland[lastX][lastY] = idx;
        tagIdx[lastX][lastY] = i;
        int w = in.nextInt();
        if (i > 0) {
          minX = min(minX, lastX);
          maxX = max(maxX, lastX);
          minY = min(minY, lastY);
          maxY = max(maxY, lastY);
          ps[i] = ps[i - 1] + w;
        } else {
          minX = maxX = lastX;
          minY = maxY = lastY;
          ps[i] = w;
        }
      }
    }

    boolean inside(int x1, int y1, int x2, int y2) {
      return x1 <= lastX && lastX <= x2
          && y1 <= lastY && lastY <= y2;
    }
  }
}
