package main;

import template.array.IntArrayUtils;
import template.io.QuickScanner;
import template.io.QuickWriter;

public class TaskDsmall {
  int n;
  int[] a, d;
  int aCnt, dCnt;
  int[] aDis, dDis;

  boolean[][] win;

  public void solve(int taskIdx, QuickScanner in, QuickWriter out) {
    System.err.printf("Handling #%d\n", taskIdx);
    n = in.nextInt();
    a = new int[n];
    d = new int[n];
    aDis = new int[n + 1];
    dDis = new int[n + 1];
    for (int i = 0; i < n; ++i) {
      a[i] = in.nextInt();
      d[i] = in.nextInt();
      aDis[i] = a[i];
      dDis[i] = d[i];
    }
    aCnt = IntArrayUtils.sortAndUnique(aDis, 0, n + 1);
    dCnt = IntArrayUtils.sortAndUnique(dDis, 0, n + 1);
    for (int i = 0; i < n; ++i) {
      a[i] = IntArrayUtils.lowerBound(aDis, 0, aCnt, a[i]);
      d[i] = IntArrayUtils.lowerBound(dDis, 0, dCnt, d[i]);
    }
    win = new boolean[aCnt][dCnt];
    for (int i = aCnt - 1; i >= 0; --i) {
      for (int j = dCnt - 1; j >= 0; --j) {
        for (int k = 0; k < n; ++k) if (a[k] > i || d[k] > j) {
          int I = Math.max(a[k], i);
          int J = Math.max(d[k], j);
          if (!win[I][J]) {
            win[i][j] = true;
            break;
          }
        }
      }
    }
    out.printf("Case #%d: %s\n", taskIdx, win[0][0] ? "YES" : "NO");
  }
}
