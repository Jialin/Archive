package main;

import template.collections.list.IntArrayList;
import template.io.QuickScanner;
import template.io.QuickWriter;

public class TaskD {
  int n, k;
  int[] t;
  IntArrayList gap;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    n = in.nextInt();
    k = in.nextInt();
    t = in.nextInt(n);
    gap = new IntArrayList(n);
    int lastNegative = -2;
    int change = 0;
    for (int i = 0; i < n; ++i) if (t[i] < 0) {
      if (lastNegative < i - 1) {
        ++change;
        if (lastNegative >= 0) {
          gap.add(i - lastNegative - 1);
        }
      }
      --k;
      lastNegative = i;
    }
    if (k < 0) {
      out.println(-1);
      return;
    }
    gap.sort();
    for (int i = 1; i < gap.size; ++i) {
      gap.values[i] += gap.values[i - 1];
    }
    int res = calc(change, k);
    if (lastNegative >= 0 && k >= n - 1 - lastNegative) {
      res = Math.min(res, calc(change, k - (n - 1 - lastNegative)) - 1);
    }
    out.println(res);
  }

  int calc(int change, int k) {
    return (change - gap.upperBound(k)) << 1;
  }
}
