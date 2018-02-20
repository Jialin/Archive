package main;

import template.collections.disjointset.DisjointSet;
import template.io.QuickScanner;
import template.io.QuickWriter;
import template.numbertheory.number.IntUtils;

import java.util.Arrays;

public class TaskH {
  int n;
  char[][] board;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    n = in.nextInt();
    board = new char[n][n];
    for (int i = 0; i < n; ++i) in.next(board[i]);
    out.println(calc());
  }

  int m;
  boolean[][] connected;

  int calc() {
    DisjointSet dset = new DisjointSet(n);
    for (int i = 0; i < n; ++i) for (int j = i + 1; j < n; ++j) if (board[i][j] == 'A') {
      dset.setFriend(i, j);
    }
    for (int i = 0; i < n; ++i) for (int j = i + 1; j < n; ++j) if (board[i][j] == 'X' && dset.isFriend(i, j)) {
      return -1;
    }
    m = 0;
    int[] setIdx = new int[n];
    for (int i = 0; i < n; ++i) if (dset.parent[i] < -1) {
      setIdx[i] = m++;
    }
    connected = new boolean[m][m];
    for (int i = 0; i < n; ++i) {
      int rootI = dset.calcRoot(i);
      if (dset.parent[rootI] >= -1) continue;
      for (int j = i + 1; j < n; ++j) if (board[i][j] == 'X') {
        int rootJ = dset.calcRoot(j);
        if (rootI == rootJ || dset.parent[rootJ] >= -1) continue;
        connected[setIdx[rootI]][setIdx[rootJ]] = connected[setIdx[rootJ]][setIdx[rootI]] = true;
      }
    }
    return n - 1 + calcMinColor();
  }

  int calcMinColor() {
    if (m == 0) return 0;
    int[] connectedMask = new int[m];
    for (int i = 0; i < m; ++i) for (int j = 0; j < m; ++j) if (connected[i][j]) {
      connectedMask[i] |= 1 << j;
    }
    int[] cnt = new int[1 << m];
    boolean[] positive = new boolean[1 << m];
    positive[0] = IntUtils.isEven(n);
    for (int mask = 1; mask < 1 << m; ++mask) {
      int idx = Integer.numberOfTrailingZeros(mask);
      int subMask = mask ^ (1 << idx);
      cnt[mask] = 1 + cnt[subMask] + cnt[subMask & ~connectedMask[idx]];
      positive[mask] = !positive[subMask];
    }
    int[] cntK = new int[1 << m];
    Arrays.fill(cntK, 1);
    for (int k = 1; ; ++k) {
      for (int mask = (1 << m) - 1; mask >= 0; --mask) cntK[mask] *= cnt[mask];
      int res = 0;
      for (int mask = (1 << m) - 1; mask >= 0; --mask) {
        if (positive[mask]) {
          res += cntK[mask];
        } else {
          res -= cntK[mask];
        }
      }
      if (res != 0) return k;
    }
  }
}
