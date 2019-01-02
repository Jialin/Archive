package main;

import template.array.IntArrayUtils;
import template.io.QuickScanner;
import template.io.QuickWriter;

/*
ID: ouyang.ji1
LANG: JAVA
TASK: milk2
*/
public class milk2_main {
  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    int n = in.nextInt();
    int[] start = new int[n];
    int[] end = new int[n];
    in.nextInts(n, start, end);
    IntArrayUtils.sort(start, end);
    int mergedStart = -1;
    int mergedEnd = -1;
    int positive = 0;
    int zero = 0;
    for (int i = 0; i < n; ++i) {
      if (mergedEnd < start[i]) {
        if (mergedEnd >= 0) {
          zero = Math.max(zero, start[i] - mergedEnd);
        }
        mergedStart = start[i];
        mergedEnd = end[i];
      } else {
        mergedEnd = Math.max(end[i], mergedEnd);
      }
      positive = Math.max(mergedEnd - mergedStart, positive);
    }
    out.println(positive, zero);
  }
}
