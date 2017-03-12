package main;

import template.collections.disjointset.DisjointSet;
import template.collections.intervaltree.AbstractSimpleIntervalTree;
import template.collections.list.IntArrayList;
import template.io.QuickScanner;
import template.io.QuickWriter;

import java.util.Arrays;

public class TaskE {
  int n, k, k2, k4, m;
  IntArrayList[] friends;
  IntervalTree iTree;
  DisjointSet dset;
  int[] newIdx, newRight;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    n = in.nextInt();
    k = in.nextInt();
    k2 = k << 1;
    k4 = k << 2;
    m = in.nextInt();
    friends = new IntArrayList[n];
    for (int i = 0; i < n; ++i) {
      friends[i] = new IntArrayList();
    }
    for (int i = 0; i < m; ++i) {
      int u = in.nextInt() - 1;
      int v = in.nextInt() - 1;
      friends[Math.min(u, v)].add(Math.max(u, v));
    }
    dset = new DisjointSet(k4);
    newIdx = new int[k4];
    newRight = new int[k];
    iTree = new IntervalTree(n);
    for (int remQ = in.nextInt(); remQ > 0; --remQ) {
      int lower = in.nextInt() - 1;
      int upper = in.nextInt();
      out.println(iTree.calc(lower, upper));
    }
  }

  class IntervalTree extends AbstractSimpleIntervalTree {
    int cnt, pnt;
    int[] lower, size;
    int[] dsetCnt;
    int[][] left, right;

    public IntervalTree(int leafCapacity) {
      super(leafCapacity);
    }

    @Override
    public void createSubclass(int nodeCapacity) {
      lower = new int[nodeCapacity];
      size = new int[nodeCapacity];
      dsetCnt = new int[nodeCapacity];
      left = new int[k][nodeCapacity];
      right = new int[k][nodeCapacity];
    }

    @Override
    public void initLeaf(int nodeIdx, int idx) {
      lower[nodeIdx] = idx;
      size[nodeIdx] = dsetCnt[nodeIdx] = 1;
      for (int i = 0; i < k; ++i) {
        left[i][nodeIdx] = -1;
        right[i][nodeIdx] = -1;
      }
      left[0][nodeIdx] = right[k - 1][nodeIdx] = 0;
    }

    @Override
    public void merge(int nodeIdx, int leftNodeIdx, int rightNodeIdx) {
      copy(leftNodeIdx, nodeIdx);
      append(rightNodeIdx, nodeIdx);
    }

    @Override
    public void calcAppend(int nodeIdx, int lower, int upper) {
      append(nodeIdx, 0);
    }

    public int calc(int lower, int upper) {
      size[0] = 0;
      calcRange(lower, upper);
      return dsetCnt[0];
    }

    void append(int fromIdx, int toIdx) {
      if (size[toIdx] == 0) {
        copy(fromIdx, toIdx);
        return;
      }
      dsetCnt[toIdx] += dsetCnt[fromIdx];
      dset.init(k4);
      for (int i = k - 1, x = lower[toIdx] + size[toIdx] - 1; i >= 0 && x >= lower[toIdx]; --i, --x) if (right[i][toIdx] >= 0) {
        for (int friendIdx = friends[x].size - 1; friendIdx >= 0; --friendIdx) {
          int y = friends[x].get(friendIdx);
          if (y < lower[fromIdx] || lower[fromIdx] + size[fromIdx] <= y) continue;
          int j = y - lower[fromIdx];
          if (left[j][fromIdx] >= 0 && !dset.isFriend(right[i][toIdx], k2 + left[j][fromIdx])) {
            --dsetCnt[toIdx];
            dset.setFriend(right[i][toIdx], k2 + left[j][fromIdx]);
          }
        }
      }
      size[toIdx] += size[fromIdx];
      Arrays.fill(newIdx, -1);
      cnt = 0;
      // left
      pnt = 0;
      updateLeft(toIdx, toIdx, 0);
      updateLeft(fromIdx, toIdx, k2);
      // right
      pnt = k - 1;
      Arrays.fill(newRight, -1);
      updateRight(fromIdx, k2);
      updateRight(toIdx, 0);
      for (int i = 0; i < k; ++i) {
        right[i][toIdx] = newRight[i];
      }
    }

    void copy(int fromIdx, int toIdx) {
      lower[toIdx] = lower[fromIdx];
      size[toIdx] = size[fromIdx];
      dsetCnt[toIdx] = dsetCnt[fromIdx];
      for (int i = 0; i < k; ++i) {
        left[i][toIdx] = left[i][fromIdx];
        right[i][toIdx] = right[i][fromIdx];
      }
    }

    void updateLeft(int fromIdx, int toIdx, int delta) {
      for (int i = 0; i < k && pnt < k; ++i) if (left[i][fromIdx] >= 0) {
        int root = dset.calcRoot(delta + left[i][fromIdx]);
        if (newIdx[root] == -1) {
          newIdx[root] = cnt++;
        }
        left[pnt++][toIdx] = newIdx[root];
      }
    }

    void updateRight(int fromIdx, int delta) {
      for (int i = k - 1; i >= 0 && pnt >= 0; --i) if (right[i][fromIdx] >= 0) {
        int root = dset.calcRoot(delta + right[i][fromIdx]);
        if (newIdx[root] == -1) {
          newIdx[root] = cnt++;
        }
        newRight[pnt--] = newIdx[root];
      }
    }
  }
}
