package main;

import template.collections.intervaltree.AbstractSimpleIntervalTree;
import template.io.QuickScanner;
import template.io.QuickWriter;

import java.util.Arrays;

public class TaskE {
  static int INF = 1000000000;
  static int MAX_STATE = 4;
  static int[][][] TRANSFORM = new int[][][]{
      //0                           1                            2                            3
      {{0, 1, 3, 4, 5, 6, 7, 8, 9}, {2},                         {},                          {}},                          // 0
      {{},                          {1, 2, 3, 4, 5, 6, 7, 8, 9}, {0},                         {}},                          // 1
      {{},                          {},                          {0, 2, 3, 4, 5, 6, 7, 8, 9}, {1}},                         // 2
      {{},                          {},                          {},                          {0, 1, 2, 3, 4, 5, 7, 8, 9}}, // 3
  };

  int n, q;
  int[] last7, cnt6;
  int[][] transform;
  char[] s;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    transform = new int[MAX_STATE][MAX_STATE];
    for (int i = 0; i < MAX_STATE; ++i) for (int j = i; j < MAX_STATE; ++j) {
      int mask = 0;
      for (int digit : TRANSFORM[i][j]) {
        mask |= 1 << digit;
      }
      transform[i][j] = mask;
    }
    n = in.nextInt();
    q = in.nextInt();
    s = new char[n];
    in.next(s);
    init();
    SimpleITree itree = new SimpleITree(n);
    for (int i = 0; i < q; ++i) {
      int lower = in.nextInt() - 1;
      int upper = in.nextInt() - 1;
      if (lower < last7[upper]) {
        int res = itree.calc(lower, last7[upper]);
        res += cnt6[upper] - cnt6[last7[upper] - 1];
        out.println(res >= INF ? -1 : res);
      } else {
        out.println(-1);
      }
    }
  }

  void init() {
    last7 = new int[n];
    cnt6 = new int[n];
    int last7Pos = -1;
    for (int i = 0; i < n; ++i) {
      if (s[i] == '7') {
        last7Pos = i;
      } else if (s[i] == '6') {
        cnt6[i] = 1;
      }
      if (i > 0) cnt6[i] += cnt6[i - 1];
      last7[i] = last7Pos;
    }
  }

  class SimpleITree extends AbstractSimpleIntervalTree {

    public SimpleITree(int leafCapacity) {
      super(leafCapacity);
    }

    int[][][] minCost;

    @Override
    public void createSubclass(int nodeCapacity) {
      minCost = new int[MAX_STATE][MAX_STATE][nodeCapacity];
      for (int i = 0; i < MAX_STATE; ++i) for (int j = i; j < MAX_STATE; ++j) {
        Arrays.fill(minCost[i][j], INF);
      }
    }

    @Override
    public void initLeaf(int nodeIdx, int idx) {
      for (int i = 0; i < MAX_STATE; ++i) {
        minCost[i][i][nodeIdx] = 1;
        for (int j = i; j < MAX_STATE; ++j) if ((transform[i][j] & (1 << (s[idx] - '0'))) > 0) {
          minCost[i][j][nodeIdx] = 0;
        }
      }
    }

    @Override
    public void merge(int nodeIdx, int leftNodeIdx, int rightNodeIdx) {
      for (int i = 0; i < MAX_STATE; ++i) for (int j = i; j < MAX_STATE; ++j) if (minCost[i][j][leftNodeIdx] != INF) {
        for (int k = j; k < MAX_STATE; ++k) {
          minCost[i][k][nodeIdx] = Math.min(
              minCost[i][k][nodeIdx], minCost[i][j][leftNodeIdx] + minCost[j][k][rightNodeIdx]);
        }
      }
    }

    int[] resCost = new int[MAX_STATE];
    int[] tmpCost = new int[MAX_STATE];

    int calc(int lower, int upper) {
      Arrays.fill(resCost, INF);
      resCost[0] = 0;
      calcRange(lower, upper);
      return resCost[3];
    }

    @Override
    public void calcAppend(int nodeIdx, int lower, int upper) {
      Arrays.fill(tmpCost, INF);
      for (int i = 0; i < MAX_STATE; ++i) for (int j = i; j < MAX_STATE; ++j) {
        tmpCost[j] = Math.min(tmpCost[j], resCost[i] + minCost[i][j][nodeIdx]);
      }
      for (int i = 0; i < MAX_STATE; ++i) resCost[i] = tmpCost[i];
    }
  }
}
