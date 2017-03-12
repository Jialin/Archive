package main;

import template.collections.list.IntArrayList;
import template.graph.tree.Tree;
import template.io.QuickScanner;
import template.io.QuickWriter;

import java.util.PriorityQueue;

public class TaskF {
  int n, m, m2;
  Tree tree;
  boolean[] hasFriend;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    n = in.nextInt();
    m = in.nextInt();
    m2 = m << 1;
    tree = new Tree(n);
    for (int i = 1; i < n; ++i) {
      tree.add(in.nextInt() - 1, in.nextInt() - 1);
    }
    hasFriend = new boolean[n];
    for (int i = 0; i < m2; ++i) {
      hasFriend[in.nextInt() - 1] = true;
    }
    int root = calcRoot();
    out.println(1);
    out.println(root + 1);
    resX = new IntArrayList(m);
    resY = new IntArrayList(m);
    resD = new IntArrayList(m);
    PriorityQueue<IntArrayList> heap = new PriorityQueue<>(n, (l1, l2) -> l2.size - l1.size);
    for (int edgeIdx = tree.lastOut(root); edgeIdx >= 0; edgeIdx = tree.nextOut(edgeIdx)) {
      IntArrayList lst = new IntArrayList();
      dfs(tree.toIdx(edgeIdx), root, lst);
      if (!lst.isEmpty()) heap.add(lst);
    }
    while (heap.size() >= 2) {
      IntArrayList x = heap.poll();
      IntArrayList y = heap.poll();
      addResult(x.pollLast(), y.pollLast(), root);
      if (!x.isEmpty()) heap.add(x);
      if (!y.isEmpty()) heap.add(y);
    }
    if (hasFriend[root]) {
      if (heap.isEmpty()) throw new IllegalArgumentException();
      addResult(root, heap.peek().peekLast(), root);
    } else {
      if (!heap.isEmpty()) throw new IllegalArgumentException();
    }
    for (int i = 0; i < resX.size; ++i) {
      out.println(resX.get(i) + 1, resY.get(i) + 1, resD.get(i) + 1);
    }
  }

  int[] parent, size;

  int calcRoot() {
    parent = new int[n];
    size = new int[n];
    dfsRoot(0, -1);
    for (int u = 0; u < n; ++u) {
      int cnt = m2 - size[u];
      if (cnt > m) continue;
      for (int edgeIdx = tree.lastOut(u); edgeIdx >= 0; edgeIdx = tree.nextOut(edgeIdx)) {
        int v = tree.toIdx(edgeIdx);
        if (parent[u] == v) continue;
        cnt = Math.max(cnt, size[v]);
        if (cnt > m) break;
      }
      if (cnt <= m) return u;
    }
    throw new IllegalArgumentException();
  }

  void dfsRoot(int u, int parent) {
    this.parent[u] = parent;
    size[u] = hasFriend[u] ? 1 : 0;
    for (int edgeIdx = tree.lastOut(u); edgeIdx >= 0; edgeIdx = tree.nextOut(edgeIdx)) {
      int v = tree.toIdx(edgeIdx);
      if (v == parent) continue;
      dfsRoot(v, u);
      size[u] += size[v];
    }
  }

  void dfs(int u, int parent, IntArrayList lst) {
    if (hasFriend[u]) {
      lst.add(u);
    }
    for (int edgeIdx = tree.lastOut(u); edgeIdx >= 0; edgeIdx = tree.nextOut(edgeIdx)) {
      int v = tree.toIdx(edgeIdx);
      if (v == parent) continue;
      dfs(v, u, lst);
    }
  }

  IntArrayList resX, resY, resD;

  void addResult(int x, int y, int d) {
    resX.add(x);
    resY.add(y);
    resD.add(d);
  }
}
