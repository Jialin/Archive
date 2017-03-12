package main;

import template.collections.list.IntArrayList;
import template.io.QuickScanner;
import template.io.QuickWriter;

public class fighting_per {
  int n, R, res;
  int[] x, y;
  IntArrayList disX, disY;

  public void solve(int taskIdx, QuickScanner in, QuickWriter out) {
    System.err.printf("Handling #%d\n", taskIdx);
    n = in.nextInt();
    R = in.nextInt();
    x = new int[n];
    y = new int[n];
    disX = new IntArrayList(n << 1);
    disY = new IntArrayList(n << 1);
    for (int i = 0; i < n; ++i) {
      x[i] = in.nextInt();
      y[i] = in.nextInt();
      disX.add(x[i]);
      disX.add(x[i] - R);
      disY.add(y[i]);
      disY.add(y[i] - R);
    }
    disX.sortAndUnique();
    disY.sortAndUnique();
    res = 0;
    dfs(0, 0);
    out.printf("Case #%d: %d\n", taskIdx, res);
  }

  void dfs(int depth, long mask) {
    if (depth > 1) {
      res = Math.max(res, Long.bitCount(mask));
      return;
    }
    for (int i = 0; i < disX.size; ++i) for (int j = 0; j < disY.size; ++j) {
      int X = disX.get(i);
      int Y = disY.get(j);
      dfs(depth + 1, calcMask(X, Y, mask));
    }
  }

  long calcMask(int X, int Y, long mask) {
    long res = mask;
    for (int i = 0; i < n; ++i) {
      if (X <= x[i] && x[i] <= X + R
          && Y <= y[i] && y[i] <= Y + R) {
        res |= 1L << i;
      }
    }
    return res;
  }
}
