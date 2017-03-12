package main;

import template.collections.binaryindexedtree.IntAddBinaryIndexedTree;
import template.io.QuickScanner;
import template.io.QuickWriter;

public class TaskD {
  int n, m;
  IntAddBinaryIndexedTree biTree;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    n = in.nextInt();
    m = in.nextInt();
    m = Math.min(m, n - m);
    biTree = new IntAddBinaryIndexedTree(n);
    long res = 1;
    for (int loop = 0, lower = 0; loop < n; ++loop) {
      int upper = lower + m >= n ? lower + m - n : lower + m;
      res += calcRange(lower + 1 == n ? 0 : lower + 1, upper) + 1;
      if (loop > 0) out.print(' ');
      out.print(res);
      biTree.update(lower, 1);
      biTree.update(upper, 1);
      lower = upper;
    }
    out.println();
  }

  int calcRange(int lower, int upper) {
    if (lower <= upper) {
      return biTree.calcRange(lower, upper);
    } else {
      return calcRange(lower, n) + calcRange(0, upper);
    }
  }
}
