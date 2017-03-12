package main;

import template.graph.tree.Tree;
import template.io.QuickScanner;
import template.io.QuickWriter;

import java.util.Arrays;

public class TaskC {
  int n;
  Tree tree;
  int[] color;
  int resIdx;
  int[] subColor, subSize;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    n = in.nextInt();
    tree = new Tree(n);
    for (int i = 1; i < n; ++i) {
      tree.add(in.nextInt() - 1, in.nextInt() - 1);
    }
    color = in.nextInt(n);
    for (int i = 0; i < n; ++i) {
      --color[i];
      add(color[i]);
    }
    resIdx = -1;
    subColor = new int[n];
    subSize = new int[n];
    Arrays.fill(subColor, -1);
    dfs(0, -1);
    if (resIdx < 0) {
      out.println("NO");
    } else {
      out.println("YES");
      out.println(resIdx + 1);
    }
  }

  void dfs(int u, int parent) {
    boolean valid = true;
    for (int edgeIdx = tree.lastOut(u); edgeIdx >= 0; edgeIdx = tree.nextOut(edgeIdx)) {
      int v = tree.toIdx(edgeIdx);
      if (v == parent) continue;
      dfs(v, u);
      subColor[u] = merge(subColor[u], subColor[v]);
      subSize[u] += subSize[v];
      valid &= subColor[v] >= 0;
    }
    if (valid) {
      sub(color[u]);
      for (int edgeIdx = tree.lastOut(u); edgeIdx >= 0; edgeIdx = tree.nextOut(edgeIdx)) {
        int v = tree.toIdx(edgeIdx);
        if (v == parent) continue;
        sub(subColor[v], subSize[v]);
      }
      if (nonZeroCnt <= 1) {
        resIdx = u;
      }
      add(color[u]);
      for (int edgeIdx = tree.lastOut(u); edgeIdx >= 0; edgeIdx = tree.nextOut(edgeIdx)) {
        int v = tree.toIdx(edgeIdx);
        if (v == parent) continue;
        add(subColor[v], subSize[v]);
      }
    }
    ++subSize[u];
    subColor[u] = merge(subColor[u], color[u]);
  }

  int merge(int l, int r) {
    if (l == -1) return r;
    if (l == -2) return -2;
    return l == r ? l : -2;
  }

  int nonZeroCnt;
  int[] cnt = new int[100000];

  void add(int c) {
    add(c, 1);
  }

  void add(int c, int size) {
    if (cnt[c] == 0) ++nonZeroCnt;
    cnt[c] += size;
  }

  void sub(int c) {
    sub(c, 1);
  }

  void sub(int c, int size) {
    if (cnt[c] == size) --nonZeroCnt;
    cnt[c] -= size;
  }
}
