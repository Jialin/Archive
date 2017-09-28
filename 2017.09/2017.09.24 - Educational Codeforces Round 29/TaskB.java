package main;

import template.array.IntArrayUtils;
import template.collections.list.IntArrayList;
import template.io.QuickScanner;
import template.io.QuickWriter;

public class TaskB {
  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    int n = in.nextInt() << 1;
    int[] a = in.nextInt(n);
    IntArrayUtils.sort(a);
    int res = Integer.MAX_VALUE;
    IntArrayList b = new IntArrayList(n - 2);
    for (int i = 0; i < n; ++i) for (int j = i + 1; j < n; ++j) {
      b.clear();
      b.addAll(a, 0, i);
      b.addAll(a, i + 1, j);
      b.addAll(a, j + 1, n);
      int diff = 0;
      for (int k = 0; k < b.size; k += 2) {
        diff += b.get(k + 1) - b.get(k);
      }
      res = Math.min(res, diff);
    }
    out.println(res);
  }
}
