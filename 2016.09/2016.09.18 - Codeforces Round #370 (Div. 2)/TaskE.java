package main;



import template.collections.intervaltree.AbstractIntervalTree;
import template.io.QuickScanner;
import template.io.QuickWriter;

public class TaskE {
  int n;
  double[] p;
  IT it;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    n = in.nextInt();
    p = new double[n];
    int queryCnt = in.nextInt();
    for (int i = 0; i < n; ++i) {
      double a = in.nextInt();
      p[i] = a / in.nextInt();
    }
    it = new IT(n);
    it.init(n);
    for (int queryIdx = 0; queryIdx < queryCnt; ++queryIdx) {
      if (in.nextInt() == 1) {
        int idx = in.nextInt() - 1;
        double a = in.nextInt();
        p[idx] = a / in.nextInt();
        it.updateRange(idx, idx + 1);
      } else {
        int lower = in.nextInt() - 1;
        int upper = in.nextInt();
        out.printf("%.10f\n", it.calcWin(lower, upper));
      }
    }
  }

  class IT extends AbstractIntervalTree {

    double[] l, r;
    double resL, resR;

    IT(int n) {
      l = new double[n << 2];
      r = new double[n << 2];
    }

    public double calcWin(int lower, int upper) {
      resL = 1;
      resR = 0;
      calc(lower, upper);
      return resL;
    }

    @Override
    public void initLeaf(int nodeIdx, int idx) {
      l[nodeIdx] = r[nodeIdx] = p[idx];
    }

    @Override
    public void merge(int nodeIdx, int leftNodeIdx, int rightNodeIdx) {
      l[nodeIdx] = mergeL(l[leftNodeIdx], r[leftNodeIdx], l[rightNodeIdx], r[rightNodeIdx]);
      r[nodeIdx] = mergeR(l[leftNodeIdx], r[leftNodeIdx], l[rightNodeIdx], r[rightNodeIdx]);
    }

    @Override
    public void updateNode(int nodeIdx, int lower, int upper) {
      initLeaf(nodeIdx, lower);
    }

    @Override
    public void calcAppend(int nodeIdx, int lower, int upper) {
      double tmpL = mergeL(resL, resR, l[nodeIdx], r[nodeIdx]);
      resR = mergeR(resL, resR, l[nodeIdx], r[nodeIdx]);
      resL = tmpL;
    }

    @Override
    public void pushLazyPropagation(int fromNodeIdx, int toNodeIdx) {}

    @Override
    public void clearLazyPropagation(int nodeIdx) {}

    double mergeL(double l1, double r1, double l2, double r2) {
      return l1 * l2 / (1 - r1 + l2 * r1);
    }

    double mergeR(double l1, double r1, double l2, double r2) {
      return r2 + (1 - r2) * r1 * l2 / (1 - r1 + l2 * r1);
    }
  }
}
