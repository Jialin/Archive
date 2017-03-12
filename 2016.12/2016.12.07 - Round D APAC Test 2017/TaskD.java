package main;

import template.collections.intervaltree.AbstractIntervalTree;
import template.io.QuickScanner;
import template.io.QuickWriter;

public class TaskD {
  int n, totalCost, target;
  int[] a, b, cost;

  public void solve(int taskIdx, QuickScanner in, QuickWriter out) {
    System.err.printf("Handling #%d\n", taskIdx);
    n = in.nextInt();
    totalCost = in.nextInt();
    target = in.nextInt();
    a = new int[n];
    b = new int[n];
    cost = new int[n];
    for (int i = 0; i < n; ++i) {
      a[i] = in.nextInt();
      b[i] = in.nextInt();
      cost[i] = in.nextInt();
    }
    long res = calc();
    out.printf("Case #%d: ", taskIdx);
    out.println(res > totalCost ? "IMPOSSIBLE" : res);
  }

  long calcSlow() {
    long res = Long.MAX_VALUE;
    for (int mask = 1; mask < 1 << n; ++mask) {
      int sumA = 0, sumB = 0;
      long sumCost = 0;
      for (int i = 0; i < n; ++i) if (((mask >> i) & 1) > 0) {
        sumA += a[i];
        sumB += b[i];
        sumCost += cost[i];
      }
      if (sumA <= target && target <= sumB) {
        res = Math.min(res, sumCost);
      }
    }
    return res;
  }

  IT tree;
  long[] minCost;

  long calc() {
    tree = new IT(target + 1);
    tree.update(0, 1, 0);
    minCost = new long[target + 1];
    for (int i = 0; i < n; ++i) {
      for (int j = 0; j <= target; ++j) {
        minCost[j] = tree.calc(j, j + 1);
      }
      for (int j = 0, lower = a[i]; lower <= target; ++j, ++lower) if (minCost[j] != Long.MAX_VALUE) {
        int upper = Math.min(j + b[i], target);
        tree.update(lower, upper + 1, minCost[j] + cost[i]);
      }
    }
    return tree.calc(target, target + 1);
  }

  class IT extends AbstractIntervalTree {

    long[] minCost, lazyMinCost;

    public IT(int leafCapacity) {
      super(leafCapacity);
    }

    @Override
    public void createSubclass(int nodeCapacity) {
      minCost = new long[nodeCapacity];
      lazyMinCost = new long[nodeCapacity];
    }

    @Override
    public void initLeaf(int nodeIdx, int idx) {
      minCost[nodeIdx] = lazyMinCost[nodeIdx] = Long.MAX_VALUE;
    }

    @Override
    public void merge(int nodeIdx, int leftNodeIdx, int rightNodeIdx) {
      minCost[nodeIdx] = Math.min(minCost[nodeIdx], minCost[nodeIdx]);
      lazyMinCost[nodeIdx] = Long.MAX_VALUE;
    }

    @Override
    public void updateNode(int nodeIdx, int lower, int upper) {
      minCost[nodeIdx] = Math.min(minCost[nodeIdx], cost);
      lazyMinCost[nodeIdx] = Math.min(lazyMinCost[nodeIdx], cost);
    }

    @Override
    public void calcAppend(int nodeIdx, int lower, int upper) {
      resCost = Math.min(resCost, minCost[nodeIdx]);
    }

    @Override
    public void pushLazyPropagation(int fromNodeIdx, int toNodeIdx) {
      minCost[toNodeIdx] = Math.min(minCost[toNodeIdx], lazyMinCost[fromNodeIdx]);
      lazyMinCost[toNodeIdx] = Math.min(lazyMinCost[toNodeIdx], lazyMinCost[fromNodeIdx]);
    }

    @Override
    public void clearLazyPropagation(int nodeIdx) {
      lazyMinCost[nodeIdx] = Long.MAX_VALUE;
    }

    long cost;

    public void update(int lower, int upper, long cost) {
      this.cost = cost;
      updateRange(lower, upper);
    }

    long resCost;

    public long calc(int lower, int upper) {
      resCost = Long.MAX_VALUE;
      calcRange(lower, upper);
      return resCost;
    }
  }
}
