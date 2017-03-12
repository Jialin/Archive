package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

import static java.lang.Math.abs;

public class TaskB {
  int n;
  int[] l, r;
  int sumL, sumR;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    n = in.nextInt();
    l = new int[n];
    r = new int[n];
    sumL = sumR = 0;
    for (int i = 0; i < n; ++i) {
      l[i] = in.nextInt();
      r[i] = in.nextInt();
      sumL += l[i];
      sumR += r[i];
    }
    int res = abs(sumL - sumR), resIdx = 0;
    for (int i = 0; i < n; ++i) {
      int curRes = abs(sumL - sumR + (r[i] - l[i]) * 2);
      if (res < curRes) {
        res = curRes;
        resIdx = i + 1;
      }
    }
    out.println(resIdx);
  }
}
