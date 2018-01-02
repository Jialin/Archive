package main;

import template.collections.eertree.OddEvenFactorizationEertree;
import template.io.QuickScanner;
import template.io.QuickWriter;

public class Task2058 {
  static int MAXL = 300000;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    int[] values = new int[MAXL];
    OddEvenFactorizationEertree eertree = new OddEvenFactorizationEertree(26, 300000, true);
    int n;
    for (n = 0; ; ++n) {
      int value = in.nextChar() - 'a';
      if (value < 0) break;
      values[n] = value;
    }
    eertree.calc(n, values);
    for (int i = 1; i <= n; ++i) {
      out.print(eertree.odd[i] <= n ? eertree.odd[i] : -1);
      out.print(' ');
      out.println(eertree.even[i] <= n ? eertree.even[i] : -2);
    }
  }
}
