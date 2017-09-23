package main;

import template.array.IntArrayUtils;
import template.collections.list.IntArrayList;
import template.io.QuickScanner;
import template.io.QuickWriter;

import java.util.Arrays;

public class TaskD {
  int n;
  int[] a;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    n = in.nextInt();
    a = in.nextInt(n);
    initDis();
    out.println(calc());
  }

  int[][] dp;
  int[] maxDis, max7;

  int calc() {
    dp = new int[n + 1][n + 1];
    maxDis = new int[dis.size];
    max7 = new int[7];
    for (int x = 0; x <= n; ++x) {
      Arrays.fill(maxDis, dp[0][x]);
      Arrays.fill(max7, dp[0][x]);
      for (int y = 1; y <= n; ++y) {
        if (x == y) continue;
        if (x < y) {
          dp[x][y] = calc(y - 1) + 1;
        }
        update(y - 1, x < y ? dp[x][y] : dp[y][x]);
      }
    }
    int res = 0;
    for (int x = 0; x <= n; ++x) {
      res = Math.max(res, IntArrayUtils.max(dp[x], x + 1, n + 1));
    }
    return res;
  }

  void update(int idx, int value) {
    int idxDis = aDis[idx];
    if (idxDis > 0 && dis.get(idxDis - 1) + 1 == dis.get(idxDis)) {
      maxDis[idxDis - 1] = Math.max(maxDis[idxDis - 1], value);
    }
    if (idxDis + 1 < dis.size && dis.get(idxDis) + 1 == dis.get(idxDis + 1)) {
      maxDis[idxDis + 1] = Math.max(maxDis[idxDis + 1], value);
    }
    max7[a7[idx]] = Math.max(max7[a7[idx]], value);
  }

  int calc(int idx) {
    return Math.max(max7[a7[idx]], maxDis[aDis[idx]]);
  }

  int[] a7, aDis;
  IntArrayList dis;

  void initDis() {
    a7 = new int[n];
    aDis = new int[n];
    for (int i = 0; i < n; ++i) a7[i] = a[i] % 7;
    dis = new IntArrayList(n);
    dis.addAll(a);
    dis.sortAndUnique();
    for (int i = 0; i < n; ++i) aDis[i] = dis.lowerBound(a[i]);
  }
}
