package main;

import template.array.IntArrayUtils;
import template.graph.blockable.BlockableBidirectionalGraph;
import template.graph.connectivity.CutEdge;
import template.graph.order.BfsOrder;
import template.graph.weighted.IntEdgeWeightedDirectedGraph;
import template.io.QuickScanner;
import template.io.QuickWriter;

import java.util.Arrays;

public class TaskF {
  QuickWriter out;

  int n, m;
  BlockableBidirectionalGraph graph;
  int[] answer;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    this.out = out;
    n = in.nextInt();
    m = in.nextInt();
    graph = new BlockableBidirectionalGraph(n, m);
    for (int i = 0; i < m; ++i) {
      graph.add(in.nextInt() - 1, in.nextInt() - 1);
    }
    answer = new int[m];
    calcCutEdge();
    handleComponent();
    calcCED();
    for (int i = 0; i < m; ++i) {
      int idx = answer[i];
      out.println(graph.fromIdx[idx] + 1, graph.toIdx[idx] + 1);
    }
  }

  int[] dfn, low, ce;
  int ceCnt;

  void calcCutEdge() {
    dfn = new int[n];
    low = new int[n];
    ce = new int[m];
    ceCnt = CutEdge.calc(graph, ce, dfn, low);
    for (int i = 0; i < ceCnt; ++i) graph.block(ce[i]);
  }

  boolean[] visited, edgeD, dfsVisited;
  int[] bfs, marker, compSize;
  int compCnt;

  void handleComponent() {
    visited = new boolean[n];
    bfs = new int[n];
    marker = new int[n];
    compCnt = 0;
    compSize = new int[n];
    edgeD = new boolean[m << 1];
    dfsVisited = new boolean[n];
    for (int i = 0; i < n; ++i) if (!visited[i]) {
      int cnt = BfsOrder.calc(graph, i, bfs, visited);
      for (int j = 0; j < cnt; ++j) {
        marker[bfs[j]] = compCnt;
      }
      compSize[compCnt++] = cnt;
      dfsComponent(bfs[0]);
    }
  }

  void dfsComponent(int u) {
    dfsVisited[u] = true;
    for (int edgeIdx = graph.lastOut[u]; edgeIdx >= 0; edgeIdx = graph.nextOut[edgeIdx]) {
      if (!edgeD[edgeIdx] && !graph.blocked(edgeIdx)) {
        int v = graph.toIdx[edgeIdx];
        answer[edgeIdx >> 1] = edgeIdx;
        edgeD[edgeIdx] = edgeD[edgeIdx ^ 1] = true;
        if (!dfsVisited[v]) dfsComponent(v);
      }
    }
  }

  IntEdgeWeightedDirectedGraph subGraph;

  void calcCED() {
    subGraph = new IntEdgeWeightedDirectedGraph(compCnt, ceCnt << 1);
    for (int i = 0; i < ceCnt; ++i) {
      int idx = ce[i];
      int fromIdx = marker[graph.fromIdx[idx]];
      int toIdx = marker[graph.toIdx[idx]];
      subGraph.add(fromIdx, toIdx, idx);
      subGraph.add(toIdx, fromIdx, idx ^ 1);
    }
    int res = IntArrayUtils.max(compSize, 0, compCnt);
    out.println(res);
    int resIdx = IntArrayUtils.find(compSize, 0, compCnt, res);
    Arrays.fill(visited, 0, compCnt, false);
    dfsTree(resIdx);
  }

  void dfsTree(int u) {
    visited[u] = true;
    for (int edgeIdx = subGraph.lastOut[u]; edgeIdx >= 0; edgeIdx = subGraph.nextOut[edgeIdx]) {
      int v = subGraph.toIdx[edgeIdx];
      if (visited[v]) continue;
      dfsTree(v);
      int idx = subGraph.edgeWeights[edgeIdx ^ 1];
      answer[idx >> 1] = idx;
    }
  }
}
