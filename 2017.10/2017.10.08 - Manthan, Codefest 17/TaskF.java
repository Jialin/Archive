package main;

import template.collections.disjointset.DisjointSet;
import template.collections.intervaltree.AbstractIntervalTreeWithLazyPropagation;
import template.io.QuickScanner;
import template.io.QuickWriter;

public class TaskF {
  static int n = 100000;

  boolean[] upperCovered, lowerCovered;
  IntHistoricalMinIntervalTree upperValue, lowerValue;
  DisjointSet upperDset, lowerDset;
  IntMaxAndSumIntervalTreeSupportMinTrim upperTree, lowerTree;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    upperCovered = new boolean[n];
    upperValue = new IntHistoricalMinIntervalTree(n);
    upperDset = new DisjointSet(n);
    upperTree = new IntMaxAndSumIntervalTreeSupportMinTrim(n);

    lowerCovered = new boolean[n];
    lowerValue = new IntHistoricalMinIntervalTree(n);
    lowerDset = new DisjointSet(n);
    lowerTree = new IntMaxAndSumIntervalTreeSupportMinTrim(n);

    for (int remQuery = in.nextInt(); remQuery > 0; --remQuery) {
      int op = in.nextInt();
      int lower = in.nextInt() - 1;
      int upper = in.nextInt() - 1;
      if (op == 1) {
        update(lower, upper, in.nextInt());
      } else {
        out.println(upperTree.calc(lower, upper) + lowerTree.calc(lower, upper));
      }
    }
  }

  void update(int lower, int upper, int value) {
    if (value > 0) {
      cover(lower, upper, upperCovered, upperDset, upperValue, value);
      upperTree.update(lower, upper, value, false);
    } else {
      cover(lower, upper, lowerCovered, lowerDset, lowerValue, -value);
      lowerTree.update(lower, upper, -value, false);
    }
  }

  void cover(
      int lower, int upper, boolean[] covered, DisjointSet dset, IntHistoricalMinIntervalTree valueTree, int value) {

    valueTree.update(lower, upper, value);
    for (int i = lower; i < upper; i = dset.calcRoot(i + 1)) {
      if (i + 1 < upper) dset.setFriend(i + 1, i);
      if (covered[i]) continue;
      covered[i] = true;
      if (upperCovered[i] && lowerCovered[i]) {
        upperTree.update(i, i + 1, upperValue.calc(i), true);
        lowerTree.update(i, i + 1, lowerValue.calc(i), true);
      }
    }
  }

  public class IntHistoricalMinIntervalTree extends AbstractIntervalTreeWithLazyPropagation {

    int[] minValue;
    int[] setValue;

    public IntHistoricalMinIntervalTree(int leafCapacity) {
      super(leafCapacity);
    }

    @Override
    public void createStorage(int nodeCapacity) {
      minValue = new int[nodeCapacity];
      setValue = new int[nodeCapacity];
    }

    @Override
    public void initLeaf(int idxInTree, int idx) {
      minValue[idxInTree] = Integer.MAX_VALUE;
    }

    int updateValue;

    @Override
    public void assignFakeLazyPropagation() {
      setValue[0] = updateValue;
    }

    @Override
    public void pushLazyPropagation(int fromIdxInTree, int toIdxInTree) {
      setValue[toIdxInTree] = Math.min(setValue[toIdxInTree], setValue[fromIdxInTree]);
      minValue[toIdxInTree] = Math.min(minValue[toIdxInTree], setValue[toIdxInTree]);
    }

    @Override
    public void clearLazyPropagation(int idxInTree) {
      setValue[idxInTree] = Integer.MAX_VALUE;
    }

    @Override
    public void merge(int leftIdxInTree, int rightIdxInTree, int idxInTree) {
      minValue[idxInTree] = Math.min(minValue[leftIdxInTree], minValue[rightIdxInTree]);
    }

    @Override
    public void copyForCalc(int fromIdxInTree, int toIdxInTree) {
      minValue[toIdxInTree] = minValue[fromIdxInTree];
    }

    @Override
    public void clearNodeForCalc(int idxInTree) {
      minValue[idxInTree] = Integer.MAX_VALUE;
    }

    @Override
    public String toDisplay(int idxInTree) {
      return null;
    }

    void update(int lower, int upper, int value) {
      updateValue = value;
      updateRange(lower, upper);
    }

    int calc(int idx) {
      calcRange(idx, idx + 1);
      return minValue[0];
    }
  }

  public class IntMaxAndSumIntervalTreeSupportMinTrim extends AbstractIntervalTreeWithLazyPropagation {

    private int[] maxValue;
    private int[] maxValueCnt;
    private int[] secondMaxValue;
    private long[] sum;

    public IntMaxAndSumIntervalTreeSupportMinTrim(int leafCapacity) {
      super(leafCapacity);
    }

    @Override
    public void createStorage(int nodeCapacity) {
      maxValue = new int[nodeCapacity];
      maxValueCnt = new int[nodeCapacity];
      secondMaxValue = new int[nodeCapacity];
      sum = new long[nodeCapacity];
    }

    @Override
    public void initLeaf(int idxInTree, int idx) {
      maxValue[idxInTree] = Integer.MIN_VALUE;
      maxValueCnt[idxInTree] = 0;
      secondMaxValue[idxInTree] = Integer.MIN_VALUE;
      sum[idxInTree] = 0;
    }

    private boolean cover;
    private int updateValue;

    @Override
    public void assignFakeLazyPropagation() {
      maxValue[0] = updateValue;
    }

    @Override
    public void pushLazyPropagation(int fromIdxInTree, int toIdxInTree) {
      if (cover && fromIdxInTree == 0 && rangeLength(toIdxInTree) == 1) {
        maxValue[toIdxInTree] = maxValue[0];
        maxValueCnt[toIdxInTree] = 1;
        secondMaxValue[toIdxInTree] = Integer.MIN_VALUE;
        sum[toIdxInTree] = maxValue[0];
        return;
      }
      if (maxValue[toIdxInTree] <= maxValue[fromIdxInTree]) return;
      if (secondMaxValue[toIdxInTree] < maxValue[fromIdxInTree]) {
        sum[toIdxInTree] -= (long) (maxValue[toIdxInTree] - maxValue[fromIdxInTree]) * maxValueCnt[toIdxInTree];
        maxValue[toIdxInTree] = maxValue[fromIdxInTree];
        return;
      }
      int leftIdxInTree = toIdxInTree << 1, rightIdxInTree = leftIdxInTree | 1;
      pushLazyPropagation(fromIdxInTree, leftIdxInTree);
      pushLazyPropagation(fromIdxInTree, rightIdxInTree);
      merge(leftIdxInTree, rightIdxInTree, toIdxInTree);
    }

    @Override
    public void clearLazyPropagation(int idxInTree) {}

    @Override
    public void merge(int leftIdxInTree, int rightIdxInTree, int idxInTree) {
      maxValue[idxInTree] = Math.max(maxValue[leftIdxInTree], maxValue[rightIdxInTree]);
      maxValueCnt[idxInTree] = (maxValue[idxInTree] == maxValue[leftIdxInTree] ? maxValueCnt[leftIdxInTree] : 0)
          + (maxValue[idxInTree] == maxValue[rightIdxInTree] ? maxValueCnt[rightIdxInTree] : 0);
      if (maxValue[leftIdxInTree] == maxValue[rightIdxInTree]) {
        secondMaxValue[idxInTree] = Math.max(secondMaxValue[leftIdxInTree], secondMaxValue[rightIdxInTree]);
      } else if (maxValue[leftIdxInTree] > maxValue[rightIdxInTree]) {
        secondMaxValue[idxInTree] = Math.max(secondMaxValue[leftIdxInTree], maxValue[rightIdxInTree]);
      } else {
        secondMaxValue[idxInTree] = Math.max(maxValue[leftIdxInTree], secondMaxValue[rightIdxInTree]);
      }
      sum[idxInTree] = sum[leftIdxInTree] + sum[rightIdxInTree];
    }

    @Override
    public void copyForCalc(int fromIdxInTree, int toIdxInTree) {
      maxValue[toIdxInTree] = maxValue[fromIdxInTree];
      maxValueCnt[toIdxInTree] = maxValueCnt[fromIdxInTree];
      secondMaxValue[toIdxInTree] = secondMaxValue[fromIdxInTree];
      sum[toIdxInTree] = sum[fromIdxInTree];
    }

    @Override
    public void clearNodeForCalc(int idxInTree) {
      maxValue[idxInTree] = secondMaxValue[idxInTree] = Integer.MIN_VALUE;
      maxValueCnt[idxInTree] = 0;
      sum[idxInTree] = 0;
    }

    @Override
    public String toDisplay(int idxInTree) {
      return String.format(
          "maxValue:%d maxValueCnt:%d secondMaxValue:%d sum:%d",
          maxValue[idxInTree], maxValueCnt[idxInTree], secondMaxValue[idxInTree], sum[idxInTree]);
    }

    public void update(int lower, int upper, int updateValue, boolean cover) {
      this.updateValue = updateValue;
      this.cover = cover;
      updateRange(lower, upper);
    }

    public long calc(int lower, int upper) {
      calcRange(lower, upper);
      return sum[0];
    }
  }
}
