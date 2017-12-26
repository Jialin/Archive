package main;

import template.collections.list.IntArrayList;
import template.io.QuickScanner;
import template.io.QuickWriter;
import template.numbertheory.number.IntUtils;

public class TaskA {
  int n;
  int[] x, y;
  IntArrayList xy;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    n = in.nextInt();
    x = in.nextInt(n);
    y = in.nextInt(n);
    xy = new IntArrayList(n << 1);
    xy.addAll(x);
    xy.addAll(y);
    xy.sortAndUnique();
    int res = 0;
    for (int i = 0; i < n; ++i) for (int j = 0; j < n; ++j) if (xy.binarySearch(x[i] ^ y[j])) {
      ++res;
    }
    out.println(IntUtils.isEven(res) ? "Karen" : "Koyomi");
  }
}
