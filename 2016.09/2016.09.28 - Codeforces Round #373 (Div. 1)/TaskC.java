package main;

import template.collections.intervaltree.AbstractIntervalTree;
import template.io.QuickScanner;
import template.io.QuickWriter;
import template.numbertheory.matrix.IntMatrixUtils;
import template.numbertheory.number.IntUtils;

public class TaskC {

  static final int BASE = 30;

  int[][][] bases;

  int n, m;
  int[] fibA, fibB;
  IT it;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    bases = new int[BASE][2][2];
    IntMatrixUtils.initFib(bases[0]);
    for (int i = 1; i < BASE; ++i) {
      IntMatrixUtils.mul(bases[i - 1], bases[i - 1], bases[i]);
    }
    n = in.nextInt();
    m = in.nextInt();
    fibA = new int[n];
    fibB = new int[n];
    int[][] fib = new int[2][2];
    for (int i = 0; i < n; ++i) {
      IntMatrixUtils.pow(bases, in.nextInt() - 1, fib);
      fibA[i] = fib[1][0];
      fibB[i] = fib[1][1];
    }
    it = new IT();
    it.init(n);
    for (int i = 0; i < m; ++i) {
      int op = in.nextInt();
      int l = in.nextInt() - 1;
      int r = in.nextInt();
      if (op == 1) {
        IntMatrixUtils.pow(bases, in.nextInt(), fib);
        it.update(l, r, fib);
      } else {
        out.println(it.calcSum(l, r));
      }
    }
  }

  class IT extends AbstractIntervalTree {

    int[] a, b;
    int[][][] label;
    int[][] update;
    int sum;

    public void update(int lower, int upper, int[][] update) {
      this.update = update;
      super.updateRange(lower, upper);
    }

    public int calcSum(int lower, int upper) {
      sum = 0;
      super.calcRange(lower, upper);
      return sum;
    }

    @Override
    public void initStorage(int n4) {
      a = new int[n4];
      b = new int[n4];
      label = new int[n4][2][2];
    }

    @Override
    public void initLeaf(int nodeIdx, int idx) {
      a[nodeIdx] = fibA[idx];
      b[nodeIdx] = fibB[idx];
      label[nodeIdx][0][0] = -1;
    }

    @Override
    public void merge(int nodeIdx, int leftNodeIdx, int rightNodeIdx) {
      a[nodeIdx] = IntUtils.add(a[leftNodeIdx], a[rightNodeIdx]);
      b[nodeIdx] = IntUtils.add(b[leftNodeIdx], b[rightNodeIdx]);
      label[nodeIdx][0][0] = -1;
    }


    @Override
    public void updateNode(int nodeIdx, int lower, int upper) {
      update(nodeIdx, update);
    }

    @Override
    public void calcAppend(int nodeIdx, int lower, int upper) {
      sum = IntUtils.add(sum, b[nodeIdx]);
    }

    @Override
    public void pushLazyPropagation(int fromNodeIdx, int toNodeIdx) {
      update(toNodeIdx, label[fromNodeIdx]);
    }

    @Override
    public void clearLazyPropagation(int nodeIdx) {
      label[nodeIdx][0][0] = -1;
    }

    void update(int nodeIdx, int[][] mul) {
      if (mul[0][0] >= 0) {
        int resA = IntUtils.add(
            IntUtils.mul(a[nodeIdx], mul[0][0]),
            IntUtils.mul(b[nodeIdx], mul[1][0]));
        int resB = IntUtils.add(
            IntUtils.mul(a[nodeIdx], mul[0][1]),
            IntUtils.mul(b[nodeIdx], mul[1][1]));
        a[nodeIdx] = resA;
        b[nodeIdx] = resB;
        if (label[nodeIdx][0][0] < 0) {
          label[nodeIdx][0][0] = mul[0][0];
          label[nodeIdx][0][1] = mul[0][1];
          label[nodeIdx][1][0] = mul[1][0];
          label[nodeIdx][1][1] = mul[1][1];
        } else {
          IntMatrixUtils.mul(label[nodeIdx], mul, label[nodeIdx]);
        }
      }
    }
  }
}
