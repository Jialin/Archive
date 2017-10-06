package main;

import template.graph.lca.TreeLCA;
import template.graph.tree.Tree;
import template.io.QuickScanner;
import template.io.QuickWriter;

public class Task2109 {
  int n;
  Tree tree;
  TreeLCA lca;
  IntervalTree iTree;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    n = in.nextInt();
    tree = new Tree(n);
    lca = new TreeLCA(n);
    for (int i = 1; i < n; ++i) {
      tree.add(in.nextInt() - 1, in.nextInt() - 1);
    }
    lca.init(tree, 0);
    iTree = new IntervalTree(n);
    for (int i = in.nextInt(); i > 0; --i) {
      int l = in.nextInt() - 1;
      int r = in.nextInt();
      out.println(iTree.calc(l, r) + 1);
    }
  }

  class IntervalTree extends AbstractSimpleIntervalTree {

    int[] idx;

    public IntervalTree(int leafCapacity) {
      super(leafCapacity);
    }

    @Override
    public void createSubclass(int nodeCapacity) {
      idx = new int[nodeCapacity];
    }

    @Override
    public void initLeaf(int nodeIdx, int idx) {
      this.idx[nodeIdx] = idx;
    }

    @Override
    public void merge(int nodeIdx, int leftNodeIdx, int rightNodeIdx) {
      idx[nodeIdx] = lca.calc(idx[leftNodeIdx], idx[rightNodeIdx]);
    }

    @Override
    public void updateNode(int nodeIdx, int lower, int upper) {}

    @Override
    public void calcAppend(int nodeIdx, int lower, int upper) {
      if (resIdx == -1) {
        resIdx = idx[nodeIdx];
      } else {
        resIdx = lca.calc(resIdx, idx[nodeIdx]);
      }
    }

    int resIdx;

    int calc(int lower, int upper) {
      resIdx = -1;
      calcRange(lower, upper);
      return resIdx;
    }

    @Override
    public String toString(int nodeIdx) {
      return null;
    }
  }
}
