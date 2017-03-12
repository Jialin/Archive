package main;

import template.collections.list.IntArrayList;
import template.io.QuickScanner;
import template.io.QuickWriter;

public class TaskA {
  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    int n = in.nextInt();
    IntArrayList res = new IntArrayList();
    for (int i = 1, i2 = 2; ; ++i, i2 += 2) {
      if (i2 < n) {
        res.add(i);
        n -= i;
      } else {
        res.add(n);
        break;
      }
    }
    out.println(res.size);
    out.println(res.values, 0, res.size);
  }
}
