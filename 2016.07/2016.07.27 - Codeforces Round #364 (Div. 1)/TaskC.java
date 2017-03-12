package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

import java.util.Arrays;

import static java.lang.Math.min;

public class TaskC {
  int n, m;
  int s, t;
  int[] weight;
  boolean[] blocked;
  boolean[] parentOfT;
  BidirectionalGraph graph;
  int[] depth;
  int[] lowpoint;
  int minCut;
  int[] cut;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    n = in.nextInt();
    m = in.nextInt();
    s = in.nextInt() - 1;
    t = in.nextInt() - 1;
    weight = new int[m];
    blocked = new boolean[m];
    graph = new BidirectionalGraph(n, m);
    for (int i = 0; i < m; ++i) {
      int x = in.nextInt() - 1;
      int y = in.nextInt() - 1;
      weight[i] = in.nextInt();
      graph.add(x, y);
    }
    parentOfT = new boolean[n];
    depth = new int[n];
    lowpoint = new int[n];
    minCut = Integer.MAX_VALUE;
    calc();
    if (minCut == Integer.MAX_VALUE) {
      out.println("-1");
    } else {
      out.println(minCut);
      out.println(cut.length);
      out.println(cut);
    }
  }

  int[] previousEdge;

  void calc() {
    int res = calcGraph();
    if (!parentOfT[s]) {
      minCut = 0;
      cut = new int[0];
      return;
    }
    if (res >= 0) {
      minCut = weight[res];
      cut = new int[] {res + 1};
    }
    previousEdge = new int[n];
    Arrays.fill(previousEdge, -1);
    dfs(s, -1, -1);
    for (int u = t; u != s; ) {
      int edgeIndex = previousEdge[u] >> 1;
      int v = graph.fromIndex[previousEdge[u]];
      blocked[edgeIndex] = true;
      res = calcGraph();
      if (res >= 0 && minCut > weight[edgeIndex] + weight[res]) {
        minCut = weight[edgeIndex] + weight[res];
        cut = new int[] {edgeIndex + 1, res + 1};
      }
      blocked[edgeIndex] = false;
      u = v;
    }
  }

  int calcGraph() {
    Arrays.fill(parentOfT, false);
    Arrays.fill(depth, -1);
    Arrays.fill(lowpoint, Integer.MAX_VALUE);
    return tarjan(s, -1, 0);
  }

  void dfs(int u, int parent, int parentEdge) {
    previousEdge[u] = parentEdge;
    for (int edgeIndex : graph.outgoingEdgeIndex(u)) {
      int v = graph.toIndex[edgeIndex];
      if (v == parent) continue;
      if (v == s || previousEdge[v] >= 0) continue;
      dfs(v, u, edgeIndex);
    }
  }

  int tarjan(int u, int parentEdge, int currentDepth) {
    int res = -1;
    depth[u] = currentDepth;
    lowpoint[u] = currentDepth;
    parentOfT[u] = u == t;
    for (int edgeIndex : graph.outgoingEdgeIndex(u)) {
      if (blocked[edgeIndex >> 1]) continue;
      int v = graph.toIndex[edgeIndex];
      if (depth[v] < 0) {
        int subRes = tarjan(v, edgeIndex, currentDepth + 1);
        if (subRes >= 0 && (res < 0 || weight[res] > weight[subRes])) {
          res = subRes;
        }
        parentOfT[u] |= parentOfT[v];
        lowpoint[u] = min(lowpoint[u], lowpoint[v]);
        if (parentOfT[v] && lowpoint[v] > depth[u]) {
          if (res < 0 || weight[res] > weight[edgeIndex >> 1]) {
            res = edgeIndex >> 1;
          }
        }
      } else if (parentEdge < 0 || (parentEdge ^ 1) != edgeIndex) {
        lowpoint[u] = min(lowpoint[u], depth[v]);
      }
    }
    return res;
  }
}
