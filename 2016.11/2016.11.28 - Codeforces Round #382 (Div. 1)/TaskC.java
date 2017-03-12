package main;

import template.graph.tree.Tree;
import template.io.QuickScanner;
import template.io.QuickWriter;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static template.numbertheory.number.IntUtils.add;
import static template.numbertheory.number.IntUtils.mul;

public class TaskC {
  int n, k, k2;
  Tree tree;
  int[][] way;
  int[][] tmpWay;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    n = in.nextInt();
    k = in.nextInt();
    if (k == 0) {
      out.println(1);
      return;
    }
    tree = new Tree(n);
    for (int i = 1; i < n; ++i) {
      tree.add(in.nextInt() - 1, in.nextInt() - 1);
    }
    k2 = (k + 1) << 1;
    way = new int[n][k2];
    tmpWay = new int[2][k2];
    dfs(0, -1);
    int res = 0;
    for (int i = 0; i <= k + 1; ++i) {
      res = add(res, way[0][i]);
    }
    out.println(res);
  }

  void dfs(int u, int parent) {
    for (int edgeIdx = tree.lastOut(u); edgeIdx >= 0; edgeIdx = tree.nextOut(edgeIdx)) {
      int v = tree.toIdx(edgeIdx);
      if (v == parent) continue;
      dfs(v, u);
    }
    int t = 0;
    clear(tmpWay[t]);
    tmpWay[t][0] = tmpWay[t][k + 2] = 1;
    for (int edgeIdx = tree.lastOut(u); edgeIdx >= 0; edgeIdx = tree.nextOut(edgeIdx)) {
      int v = tree.toIdx(edgeIdx);
      if (v == parent) continue;
      clear(tmpWay[t ^ 1]);
      append(tmpWay[t], way[v], tmpWay[t ^ 1]);
      t ^= 1;
    }
    for (int i = 0; i < k2; ++i) {
      way[u][i] = tmpWay[t][i];
    }
  }

  void clear(int[] way) {
    for (int i = 0; i < k2; ++i) {
      way[i] = 0;
    }
  }

  void append(int[] way, int[] childWay, int[] resWay) {
    for (int i = 0; i < k2; ++i) if (way[i] > 0) {
      int black = i < k + 1 ? i + 1 : 0;
      int white = i > k + 1 ? i - k - 1 : 0;
      for (int j = 0; j < k2; ++j) if (childWay[j] > 0) {
        int value = mul(way[i], childWay[j]);
        int childBlack = j < k + 1 ? j + 1 : 0;
        int childWhite = j > k + 1 ? j - k - 1 : 0;
        int resBlack;
        if (black > 0 && childBlack > 0) {
          resBlack = min(black, childBlack + 1);
        } else if (black > 0) {
          resBlack = black;
        } else if (childBlack > 0) {
          resBlack = childBlack > k ? 0 : childBlack + 1;
        } else {
          resBlack = 0;
        }
        int resWhite;
        if (white > 0 && childWhite > 0) {
          resWhite = max(white, childWhite + 1);
        } else if (white > 0) {
          resWhite = white;
        } else if (childWhite > 0) {
          resWhite = childWhite + 1;
        } else {
          resWhite = 0;
        }
        if (resBlack > 0 && resWhite <= k + 2 - resBlack) {
          resWhite = 0;
        }
        if (resWhite > 0) {
          if (resWhite <= k) {
            resWay[resWhite + k + 1] = add(resWay[resWhite + k + 1], value);
          }
        } else if (resBlack > 0) {
          resWay[resBlack - 1] = add(resWay[resBlack - 1], value);
        } else {
          resWay[k + 1] = add(resWay[k + 1], value);
        }
      }
    }
  }
}
