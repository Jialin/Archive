package main;

import template.collections.binaryindexedtree.LongXorBinaryIndexedTree2D;
import template.io.QuickScanner;
import template.io.QuickWriter;
import template.numbertheory.hash.LongHasher;

public class TaskE {
  static LongHasher hasher = new LongHasher();

  int n, m;
  LongXorBinaryIndexedTree2D xorTree;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    n = in.nextInt();
    m = in.nextInt();
    xorTree = new LongXorBinaryIndexedTree2D(n, m);
    for (int queryRem = in.nextInt(); queryRem > 0; --queryRem) {
      int op = in.nextInt();
      int x1 = in.nextInt() - 1;
      int y1 = in.nextInt() - 1;
      int x2 = in.nextInt();
      int y2 = in.nextInt();
      if (op == 3) {
        --x2;
        --y2;
        out.println(xorTree.calc(x1, y1) == xorTree.calc(x2, y2) ? "Yes" : "No");
        continue;
      }
      long hash = hasher.calc2(x1, y1);
      xorTree.update(x1, y1, hash);
      xorTree.update(x1, y2, hash);
      xorTree.update(x2, y1, hash);
      xorTree.update(x2, y2, hash);
    }
  }
}
