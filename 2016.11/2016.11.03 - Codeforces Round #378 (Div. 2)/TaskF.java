package main;

import template.collections.disjointset.DisjointSet;
import template.graph.treepathquery.AbstractEdgeWeightedTreePathQuery;
import template.graph.weighted.IntEdgeWeightedTree;
import template.io.QuickScanner;
import template.io.QuickWriter;

import java.util.Arrays;

public class TaskF {
  int n, m, s;
  Edge[] edges;
  DisjointSet dset;
  IntEdgeWeightedTree tree;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    n = in.nextInt();
    m = in.nextInt();
    edges = new Edge[m];
    for (int i = 0; i < m; ++i) {
      edges[i] = new Edge(i);
    }
    for (int i = 0; i < m; ++i) {
      edges[i].w = in.nextInt();
    }
    for (int i = 0; i < m; ++i) {
      edges[i].c = in.nextInt();
    }
    for (int i = 0; i < m; ++i) {
      edges[i].fromIdx = in.nextInt() - 1;
      edges[i].toIdx = in.nextInt() - 1;
    }
    s = in.nextInt();
    Arrays.sort(edges);
    dset = new DisjointSet(n);
    int minC = Integer.MAX_VALUE;
    int minCIdx = -1;
    long sumW = 0;
    tree = new IntEdgeWeightedTree(n);
    for (int i = 0; i < m; ++i) {
      int fromIdx = edges[i].fromIdx;
      int toIdx = edges[i].toIdx;
      if (dset.isFriend(fromIdx, toIdx)) continue;
      dset.setFriend(fromIdx, toIdx);
      tree.add(fromIdx, toIdx, i);
      edges[i].inMst = true;
      sumW += edges[i].w;
      if (minC > edges[i].c) {
        minC = edges[i].c;
        minCIdx = i;
      }
    }
    int findIdx = minCIdx, replaceIdx = minCIdx;
    long res = sumW - s / minC;
    TreePathQuery treePathQuery = new TreePathQuery(n);
    treePathQuery.init(tree, 0);
    for (int i = 0; i < m; ++i) if (!edges[i].inMst && edges[i].c < minC) {
      treePathQuery.calc(edges[i].fromIdx, edges[i].toIdx);
      int currentIdx = treePathQuery.resIdx;
      long currentRes = sumW - edges[currentIdx].w + edges[i].w - s / edges[i].c;
      if (res <= currentRes) continue;
      res = currentRes;
      findIdx = currentIdx;
      replaceIdx = i;
    }
    out.println(res);
    for (int i = 0; i < m; ++i) if (edges[i].inMst) {
      if (i == findIdx) {
        out.println(edges[replaceIdx].idx + 1, edges[replaceIdx].w - s / edges[replaceIdx].c);
      } else {
        out.println(edges[i].idx + 1, edges[i].w);
      }
    }
  }

  class Edge implements Comparable<Edge> {
    final int idx;
    int w, c, fromIdx, toIdx;
    boolean inMst;

    Edge(int idx) {
      this.idx = idx;
      inMst = false;
    }

    @Override
    public int compareTo(Edge o) {
      return w - o.w;
    }
  }

  class TreePathQuery extends AbstractEdgeWeightedTreePathQuery {

    public TreePathQuery(int capacity) {
      super(capacity);
    }

    int[][] maxWIdx;

    @Override
    public void createSubclass(int capacityHighBit, int capacity) {
      maxWIdx = new int[capacityHighBit][capacity];
    }

    @Override
    public void initSubclass(int u, int toParentEdgeIdx) {
      maxWIdx[0][u] = tree.edgeWeight(toParentEdgeIdx);
    }

    @Override
    public void initMerge(int targetBit, int targetIdx, int sourceBit, int sourceIdx1, int sourceIdx2) {
      int idx1 = maxWIdx[sourceBit][sourceIdx1];
      int idx2 = maxWIdx[sourceBit][sourceIdx2];
      maxWIdx[targetBit][targetIdx] = edges[idx1].w > edges[idx2].w ? idx1 : idx2;
    }

    @Override
    public void calcAppend(int bit, int idx) {
      if (resIdx == -1 || edges[resIdx].w < edges[maxWIdx[bit][idx]].w) {
        resIdx = maxWIdx[bit][idx];
      }
    }

    int resIdx;

    public void calc(int fromIdx, int toIdx) {
      resIdx = -1;
      super.calc(fromIdx, toIdx);
    }
  }
}
