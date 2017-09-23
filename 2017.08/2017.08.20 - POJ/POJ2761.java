package main;

import template.collections.partitiontree.IntPartitionTree;
import template.io.QuickScanner;
import template.io.QuickWriter;

public class POJ2761 {
  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    IntPartitionTree ptree = new IntPartitionTree();
    int n = in.nextInt();
    int m = in.nextInt();
    ptree.init(in.nextInt(n));
    for (int i = 0; i < m; ++i) {
      int x = in.nextInt() - 1;
      int y = in.nextInt();
      int kth = in.nextInt() - 1;
      out.println(ptree.calcKth(x, y, kth));
    }
  }
}
