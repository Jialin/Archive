package main;

import template.collections.list.IntArrayList;
import template.io.QuickScanner;
import template.io.QuickWriter;

public class TaskA {
  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    int n = in.nextInt();
    int x = in.nextInt();
    IntArrayList a = new IntArrayList(n);
    in.nextInt(n, a);
    int res = 0;
    for (int i = 0; i < x; ++i) if (a.find(i) < 0) {
      ++res;
    }
    if (a.find(x) >= 0) ++res;
    out.println(res);
  }
}
