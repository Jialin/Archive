package main;

import template.array.IntArrayUtils;
import template.collections.binaryindexedtree.IntAddBinaryIndexedTree2D;
import template.io.QuickScanner;
import template.io.QuickWriter;

public class TaskE {
  int n, m, sqrtN;
  int[] perm, blockIdx;
  IntAddBinaryIndexedTree2D biTree;
  long res;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    n = in.nextInt();
    for (sqrtN = 1; sqrtN < n / sqrtN; ++sqrtN) {}
    perm = new int[n];
    blockIdx = new int[n];
    m = 0;
    for (int i = 0; i < n; i += sqrtN, ++m) {
      for (int j = i, k = 0; j < n && k < sqrtN; ++j, ++k) {
        perm[j] = j;
        blockIdx[j] = m;
      }
    }
    biTree = new IntAddBinaryIndexedTree2D(m, n);
    for (int i = 0; i < n; ++i) biTree.update(blockIdx[i], i, 1);
    res = 0;
    for (int q = in.nextInt(); q > 0; --q) {
      int x = in.nextInt() - 1;
      int y = in.nextInt() - 1;
      out.println(calc(x, y));
    }
  }

  long calc(int x, int y) {
    if (x > y) return calc(y, x);
    if (x == y) return res;
    res += perm[x] > perm[y] ? -1 : 1;
    res -= subCalc(x, y);
    biTree.update(blockIdx[x], perm[x], -1);
    biTree.update(blockIdx[y], perm[y], -1);
    IntArrayUtils.swap(perm, x, y);
    biTree.update(blockIdx[x], perm[x], 1);
    biTree.update(blockIdx[y], perm[y], 1);
    res += subCalc(x, y);
    return res;
  }

  int subCalc(int x, int y) {
    int idxX = blockIdx[x], idxY = blockIdx[y];
    int valueX = perm[x], valueY = perm[y];
    int res = 0;
    if (idxX + 1 < idxY) {
      res += biTree.calcRange(idxX + 1, 0, idxY, valueX);
      res += biTree.calcRange(idxX + 1, valueY + 1, idxY, n);
    }
    if (idxX == idxY) {
      for (int i = x + 1; i < y; ++i) {
        if (valueX > perm[i]) ++res;
        if (perm[i] > valueY) ++res;
      }
    } else {
      for (int i = Math.min((idxX + 1) * sqrtN, n) - 1; i > x; --i) {
        if (valueX > perm[i]) ++res;
        if (perm[i] > valueY) ++res;
      }
      for (int i = sqrtN * idxY; i < y; ++i) {
        if (valueX > perm[i]) ++res;
        if (perm[i] > valueY) ++res;
      }
    }
    return res;
  }
}
