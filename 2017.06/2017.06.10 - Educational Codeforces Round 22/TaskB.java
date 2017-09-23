package main;

import template.collections.list.LongArrayList;
import template.io.QuickScanner;
import template.io.QuickWriter;

public class TaskB {
  long l, r;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    long x = in.nextLong();
    long y = in.nextLong();
    l = in.nextLong();
    r = in.nextLong();
    LongArrayList xLst = gen(x);
    LongArrayList yLst = gen(y);
    LongArrayList lst = new LongArrayList();
    lst.add(l - 1);
    lst.add(r + 1);
    for (long xValue : xLst) for (long yValue : yLst) {
      long value = xValue + yValue;
      if (l <= value && value <= r) {
        lst.add(value);
      }
    }
    lst.sortAndUnique();
    long res = 0;
    for (int i = 1; i < lst.size; ++i) {
      res = Math.max(res, lst.get(i) - lst.get(i - 1) - 1);
    }
    out.println(res);
  }

  LongArrayList gen(long x) {
    LongArrayList res = new LongArrayList();
    for (res.add(1); res.peekLast() <= r / x; res.add(res.peekLast() * x)) {}
    return res;
  }
}
