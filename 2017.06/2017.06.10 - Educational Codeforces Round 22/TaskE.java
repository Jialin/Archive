package main;

import template.array.IntArrayUtils;
import template.collections.partitiontree.IntPartitionTree;
import template.io.QuickScanner;
import template.io.QuickWriter;

public class TaskE {
  int n, m, k;
  int[] a;
  int[] lastA;
  int[] jumpPow2, jump;
  IntPartitionTree ptree;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    ptree = new IntPartitionTree();
    n = in.nextInt();
    k = in.nextInt();
    a = in.nextInt(n);
    m = IntArrayUtils.max(a);
    IntArrayUtils.update(a, -1);
    initLast();
    ptree.init(jump);
    int last = 0;
    for (int remQ = in.nextInt(); remQ > 0; --remQ) {
      int x = (in.nextInt() + last) % n;
      int y = (in.nextInt() + last) % n;
      last = calc(x, y);
      out.println(last);
    }
  }

  int calc(int x, int y) {
    return x <= y
        ? ptree.calcLess(x, y + 1, x)
        : ptree.calcLess(y, x + 1, y);
  }

  void initLast() {
    jump = new int[n];
    jumpPow2 = new int[n];
    lastA = new int[m];
    for (int i = 0; i < m; ++i) lastA[i] = -1;
    for (int i = 0; i < n; ++i) {
      jump[i] = i;
      jumpPow2[i] = lastA[a[i]];
      lastA[a[i]] = i;
    }
    while (k > 0) {
      if ((k & 1) > 0) {
        for (int i = n - 1; i >= 0; --i) {
          if (jump[i] != -1) jump[i] = jumpPow2[jump[i]];
        }
      }
      k >>= 1;
      if (k == 0) break;
      for (int i = n - 1; i >= 0; --i) {
        if (jumpPow2[i] != -1) jumpPow2[i] = jumpPow2[jumpPow2[i]];
      }
    }
  }
}
