package main;

import template.collections.intervaltree.impl.IntMaxAndSumIntervalTreeSupportMinTrim;
import template.io.QuickScanner;
import template.io.QuickWriter;

public class HDU5306 {
  static int MAXN = 1000000;

  int n, m;
  int[] a;
  IntMaxAndSumIntervalTreeSupportMinTrim itree;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    itree = new IntMaxAndSumIntervalTreeSupportMinTrim(MAXN, false);
    a = new int[MAXN];
    for (int remTask = in.nextInt(); remTask > 0; --remTask) {
      n = in.nextInt();
      m = in.nextInt();
      in.nextInt(n, a);
      itree.init(n, a);
      for (int remQuery = m; remQuery > 0; --remQuery) {
        int op = in.nextInt();
        int lower = in.nextInt() - 1;
        int upper = in.nextInt();
        if (op == 0) {
          itree.update(lower, upper, in.nextInt());
        } else if (op == 1) {
          out.println(itree.calcMaxValue(lower, upper));
        } else {
          out.println(itree.calcSum(lower, upper));
        }
      }
    }
  }
}
