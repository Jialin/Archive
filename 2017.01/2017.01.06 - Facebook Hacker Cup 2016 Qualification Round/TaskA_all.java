package main;

import template.collections.list.IntArrayList;
import template.io.QuickScanner;
import template.io.QuickWriter;

public class TaskA_all {
  static int MAXN = 2000;

  int[] x = new int[MAXN];
  int[] y = new int[MAXN];
  IntArrayList dist = new IntArrayList(MAXN);
  IntArrayList distCnt = new IntArrayList(MAXN);

  public void solve(int unusedTaskIdx, QuickScanner in, QuickWriter out) {
    int taskCnt = in.nextInt();
    for (int taskIdx = 1; taskIdx <= taskCnt; ++taskIdx) {
      System.err.printf("Handling #%d\n", taskIdx);
      int n = in.nextInt();
      for (int i = 0; i < n; ++i) {
        x[i] = in.nextInt();
        y[i] = in.nextInt();
      }
      long res = 0;
      for (int i = 0; i < n; ++i) {
        dist.clear();
        for (int j = 0; j < n; ++j) if (i != j) {
          dist.add(sqr(x[i] - x[j]) + sqr(y[i] - y[j]));
        }
        dist.sort();
        dist.unique(distCnt);
        for (int j = 0; j < distCnt.size; ++j) {
          int cnt = distCnt.get(j);
          res += ((cnt - 1L) * cnt) >> 1;
        }
      }
      out.printf("Case #%d: %d\n", taskIdx, res);
    }
  }

  int sqr(int x) {
    return x * x;
  }
}
