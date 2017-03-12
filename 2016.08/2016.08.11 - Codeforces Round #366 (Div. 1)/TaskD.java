package main;

import template.array.IntArrayUtils;
import template.graph.flow.IntBoundedMaxFlow;
import template.io.QuickScanner;
import template.io.QuickWriter;

import java.util.Arrays;

public class TaskD {
  int n, m;
  long r, b;
  boolean rbSwap;
  int[] x, y;

  int disXCnt, disYCnt;
  int[] disX, disY, xCnt, yCnt, xLimit, yLimit;
  IntBoundedMaxFlow bmf;
  int[] edges;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    n = in.nextInt();
    m = in.nextInt();
    r = in.nextLong();
    b = in.nextLong();
    rbSwap = r > b;
    if (rbSwap) {
      r ^= b;
      b ^= r;
      r ^= b;
    }
    x = new int[n];
    y = new int[n];
    disX = new int[n];
    disY = new int[n];
    for (int i = 0; i < n; ++i) {
      x[i] = in.nextInt();
      y[i] = in.nextInt();
      disX[i] = x[i];
      disY[i] = y[i];
    }
    Arrays.sort(disX);
    Arrays.sort(disY);
    disXCnt = IntArrayUtils.unique(disX);
    disYCnt = IntArrayUtils.unique(disY);
    xCnt = new int[disXCnt];
    yCnt = new int[disYCnt];
    for (int i = 0; i < n; ++i) {
      x[i] = Arrays.binarySearch(disX, 0, disXCnt, x[i]);
      y[i] = Arrays.binarySearch(disY, 0, disYCnt, y[i]);
      ++xCnt[x[i]];
      ++yCnt[y[i]];
    }
    xLimit = new int[disXCnt];
    yLimit = new int[disYCnt];
    for (int i = 0; i < disXCnt; ++i) xLimit[i] = xCnt[i];
    for (int i = 0; i < disYCnt; ++i) yLimit[i] = yCnt[i];
    for (int i = 0; i < m; ++i) {
      int t = in.nextInt();
      int l = in.nextInt();
      int d = in.nextInt();
      if (t == 1) {
        int idx = Arrays.binarySearch(disX, 0, disXCnt, l);
        if (idx < 0) continue;
        xLimit[idx] = Math.min(xLimit[idx], d);
      } else {
        int idx = Arrays.binarySearch(disY, 0, disYCnt, l);
        if (idx < 0) continue;
        yLimit[idx] = Math.min(yLimit[idx], d);
      }
    }
    bmf = new IntBoundedMaxFlow(disXCnt + disYCnt + 2, disXCnt + disYCnt + n);
    bmf.init(disXCnt + disYCnt + 2);
    int source = disXCnt + disYCnt, sink = source + 1;
    edges = new int[n];
    for (int i = 0; i < n; ++i) {
      edges[i] = bmf.add(x[i], disXCnt + y[i], 0, 1);
    }
    for (int i = 0; i < disXCnt; ++i) {
      bmf.add(source, i, (xCnt[i] - xLimit[i] + 1) >> 1, (xCnt[i] + xLimit[i]) >> 1);
    }
    for (int i = 0; i < disYCnt; ++i) {
      bmf.add(disXCnt + i, sink, (yCnt[i] - yLimit[i] + 1) >> 1, (yCnt[i] + yLimit[i]) >> 1);
    }
    int cnt = bmf.calc(source, sink);
    if (cnt == -1) {
      out.println("-1");
      return;
    }
    long res = r * cnt + b * (n - cnt);
    out.println(res);
    for (int i = 0; i < n; ++i) {
      boolean red = (bmf.flow[edges[i]] > 0) ^ rbSwap;
      out.print(red ? 'r' : 'b');
    }
    out.println();
  }
}
