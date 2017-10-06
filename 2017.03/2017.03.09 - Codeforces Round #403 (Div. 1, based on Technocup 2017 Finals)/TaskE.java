package main;

import template.collections.list.IntArrayList;
import template.io.QuickScanner;
import template.io.QuickWriter;
import template.numbertheory.number.IntModular;

import java.util.Arrays;

public class TaskE {

  static int INF = Integer.MAX_VALUE >> 1;
  static final IntModular MOD = new IntModular();

  int maxHeight, maxWidth, barrierCnt;

  Barrier[] barriers;
  int[] stackSize;
  IntArrayList[] stackHeight, stackCnt;
  IntervalTree iTree;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    maxHeight = in.nextInt();
    maxWidth = in.nextInt();
    barrierCnt = in.nextInt();
    barriers = new Barrier[barrierCnt];
    stackSize = new int[maxWidth];
    stackHeight = new IntArrayList[maxWidth];
    stackCnt = new IntArrayList[maxWidth];
    for (int i = 0; i < barrierCnt; ++i) {
      int u = in.nextInt();
      int l = in.nextInt() - 1;
      int r = in.nextInt();
      int s = in.nextInt();
      barriers[i] = new Barrier(u, l, r, s);
      if (l > 0) ++stackSize[l - 1];
      if (r < maxWidth) ++stackSize[r];
    }
    Arrays.sort(barriers);
    for (int i = 0; i < maxWidth; ++i) {
      stackHeight[i] = new IntArrayList(stackSize[i] + 1);
      stackCnt[i] = new IntArrayList(stackSize[i] + 1);
      stackHeight[i].add(maxHeight + 1);
      stackCnt[i].add(1);
    }
    iTree = new IntervalTree(maxWidth);
    out.println(calc());
  }

  int calc() {
    for (Barrier barrier : barriers) {
      int curRes = 0;
      while (true) {
        int idx = iTree.calc(barrier.left, barrier.right);
        if (stackHeight[idx].isEmpty() || stackHeight[idx].peekLast() > barrier.height + barrier.delta) break;
        do {
          curRes = MOD.add(curRes, stackCnt[idx].pollLast());
          stackHeight[idx].pollLast();
        } while (!stackHeight[idx].isEmpty() && stackHeight[idx].peekLast() <= barrier.height + barrier.delta);
        iTree.update(idx);
      }
      if (0 < barrier.left && barrier.right < maxWidth) {
        stackHeight[barrier.left - 1].add(barrier.height);
        stackCnt[barrier.left - 1].add(curRes);
        stackHeight[barrier.right].add(barrier.height);
        stackCnt[barrier.right].add(curRes);
        iTree.update(barrier.left - 1);
        iTree.update(barrier.right);
      } else {
        curRes = MOD.add(curRes, curRes);
        if (barrier.left == 0) {
          stackHeight[barrier.right].add(barrier.height);
          stackCnt[barrier.right].add(curRes);
          iTree.update(barrier.right);
        } else {
          stackHeight[barrier.left - 1].add(barrier.height);
          stackCnt[barrier.left - 1].add(curRes);
          iTree.update(barrier.left - 1);
        }
      }
    }
    int res = 0;
    for (IntArrayList cnt : stackCnt) {
      for (int i = 0; i < cnt.size; ++i) {
        res = MOD.add(res, cnt.get(i));
      }
    }
    return res;
  }

  int getMinHeight(int idx) {
    IntArrayList stack = stackHeight[idx];
    return stack.isEmpty() ? INF : stack.peekLast();
  }

  class IntervalTree extends AbstractSimpleIntervalTree {

    int[] minIdx;

    public IntervalTree(int leafCapacity) {
      super(leafCapacity);
    }

    @Override
    public void createSubclass(int nodeCapacity) {
      minIdx = new int[nodeCapacity];
    }

    @Override
    public void initLeaf(int nodeIdx, int idx) {
      minIdx[nodeIdx] = idx;
    }

    @Override
    public void merge(int nodeIdx, int leftNodeIdx, int rightNodeIdx) {
      int leftIdx = minIdx[leftNodeIdx];
      int rightIdx = minIdx[rightNodeIdx];
      minIdx[nodeIdx] = getMinHeight(leftIdx) <= getMinHeight(rightIdx) ? leftIdx : rightIdx;
    }

    @Override
    public void updateNode(int nodeIdx, int lower, int upper) {}

    @Override
    public void calcAppend(int nodeIdx, int lower, int upper) {
      resIdx = resIdx >= 0 && getMinHeight(resIdx) <= getMinHeight(minIdx[nodeIdx])
          ? resIdx
          : minIdx[nodeIdx];
    }

    @Override
    public String toString(int nodeIdx) {
      return "" + minIdx[nodeIdx];
    }

    void update(int x) {
      updateRange(x, x + 1);
    }

    int resIdx;

    int calc(int lower, int upper) {
      resIdx = -1;
      calcRange(lower, upper);
      return resIdx;
    }
  }

  class Barrier implements Comparable<Barrier> {
    final int height, left, right, delta;

    Barrier(int height, int left, int right, int delta) {
      this.height = height;
      this.left = left;
      this.right = right;
      this.delta = delta;
    }

    @Override
    public int compareTo(Barrier o) {
      return o.height - height;
    }
  }
}
