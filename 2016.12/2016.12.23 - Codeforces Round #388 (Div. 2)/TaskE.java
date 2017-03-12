package main;

import template.array.IntArrayUtils;
import template.collections.binaryindexedtree.IntAddBinaryIndexedTree;
import template.collections.binaryindexedtree.LongAddBinaryIndexedTree;
import template.io.QuickScanner;
import template.io.QuickWriter;

public class TaskE {
  int n;
  int[] perm;
  IntAddBinaryIndexedTree markBit;
  LongAddBinaryIndexedTree cntBit;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    n = in.nextInt();
    perm = in.nextInt(n);
    IntArrayUtils.update(perm, -1);
    markBit = new IntAddBinaryIndexedTree(n);
    cntBit = new LongAddBinaryIndexedTree(n);
    long invCnt = 0;
    double allInvCnt = 0;
    for (int i = n - 1; i >= 0; --i) {
      int p = perm[i];
      invCnt += markBit.calc(p);
      allInvCnt += cntBit.calc(p) * (i + 1);
      markBit.update(p, 1);
      cntBit.update(p, n - i);
    }
    double rangeCnt = n / 2. * (n + 1);
    double incInv = (n - 1.) * (n + 2) / 24;
    out.printf("%.30f\n", invCnt - allInvCnt / rangeCnt + incInv);
  }
}
