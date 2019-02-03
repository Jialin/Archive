package main;

import template.array.IntArrayUtils;
import template.io.QuickScanner;
import template.io.QuickWriter;

import java.util.Arrays;

/*
ID: ouyang.ji1
LANG: JAVA
TASK: numtri
*/
public class numtri_main {
  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    int n = in.nextInt();
    int[] values = new int[n];
    int[] dpPrev = new int[n];
    int[] dpCurr = new int[n];
    dpPrev[0] = in.nextInt();
    for (int i = 2; i <= n; ++i) {
      for (int j = 0; j < i; ++j) {
        values[j] = in.nextInt();
      }
      Arrays.fill(dpCurr, 0, i, 0);
      dpCurr[0] = dpPrev[0] + values[0];
      dpCurr[i - 1] = dpPrev[i - 2] + values[i - 1];
      for (int j = 1; j + 1 < i; ++j) {
        dpCurr[j] = Math.max(dpPrev[j - 1], dpPrev[j]) + values[j];
      }
      int[] tmp = dpPrev;
      dpPrev = dpCurr;
      dpCurr = tmp;
    }
    out.println(IntArrayUtils.max(dpPrev));
  }
}
