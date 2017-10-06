package main;

import template.graph.lca.TreeLCA;
import template.graph.weighted.BooleanEdgeWeightedTree;
import template.io.QuickScanner;
import template.io.QuickWriter;

public class TaskD {
  int vertexCnt;
  BooleanEdgeWeightedTree tree;
  TreeLCA lca;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    vertexCnt = in.nextInt();
    tree = new BooleanEdgeWeightedTree(vertexCnt + 1);
    for (int i = 0; i < vertexCnt; ++i) {
      int parent = in.nextInt();
      int type = in.nextInt();
      tree.add(
          i,
          parent < 0 ? vertexCnt : parent - 1,
          type > 0);
    }
    lca = new TreeLCA();
    lca.init(tree, vertexCnt);
    preCalc();
    for (int remQ = in.nextInt(); remQ > 0; --remQ) {
      out.println(calc(in.nextInt(), in.nextInt() - 1, in.nextInt() - 1) ? "YES" : "NO");
    }
  }

  int[] specialCnt, partCnt;

  void preCalc() {
    specialCnt = new int[vertexCnt];
    partCnt = new int[vertexCnt];
    tree.forEachOutNodes(vertexCnt, u -> dfs(u, vertexCnt, 0, 0));
  }

  void dfs(int u, int parent, int specialCnt, int partCnt) {
    this.specialCnt[u] = specialCnt;
    this.partCnt[u] = partCnt;
    tree.forEachOutEdges(u, edgeIdx -> {
      int v = tree.toIdx(edgeIdx);
      if (v == parent) return;
      boolean isPart = tree.edgeWeight(edgeIdx);
      dfs(v, u, specialCnt + (isPart ? 0 : 1), partCnt + (isPart ? 1 : 0));
    });
  }

  boolean calc(int type, int u, int v) {
    if (u == v) return false;
    int lca = this.lca.calc(u, v);
    if (lca == vertexCnt) return false;
    if (type == 1) {
      return lca == u && isAllSpecial(v, u);
    } else {
      return lca != v && isAllPart(v, lca) && isAllSpecial(u, lca);
    }
  }

  boolean isAllSpecial(int u, int ancestor) {
    return specialCnt[u] - specialCnt[ancestor] == lca.depth[u] - lca.depth[ancestor];
  }

  boolean isAllPart(int u, int ancestor) {
    return partCnt[u] - partCnt[ancestor] == lca.depth[u] - lca.depth[ancestor];
  }
}
