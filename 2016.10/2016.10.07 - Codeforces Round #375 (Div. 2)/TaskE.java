package main;

import template.graph.BlockableBidirectionalGraph;
import template.graph.euler.EulerPath;
import template.graph.order.BfsOrder;
import template.io.QuickScanner;
import template.io.QuickWriter;

import java.util.Arrays;

public class TaskE {
  static final int MAXN = 200;
  static final int MAXM = MAXN * (MAXN - 1) / 2 + MAXN;

  int n, m, m2;
  BlockableBidirectionalGraph graph;
  int[] bfs, path, lastOut;
  boolean[] visited;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    graph = new BlockableBidirectionalGraph(MAXN, MAXM);
    bfs = new int[MAXN];
    lastOut = new int[MAXN];
    visited = new boolean[MAXN];
    path = new int[MAXM];
    for (int remTask = in.nextInt(); remTask > 0; --remTask) {
      n = in.nextInt();
      m = in.nextInt();
      m2 = m << 1;
      graph.init(n);
      for (int i = 0; i < m; ++i) {
        graph.add(in.nextInt() - 1, in.nextInt() - 1);
      }
      int res = 0;
      for (int i = 0; i < n; ++i) {
        res += 1 - (graph.outDegree[i] & 1);
      }
      out.println(res);
      calc(out);
    }
  }

  void calc(QuickWriter out) {
    Arrays.fill(visited, 0, n, false);
    for (int i = 0; i < n; ++i) lastOut[i] = graph.lastOut[i];
    for (int i = 0; i < n; ++i) if (!visited[i]) {
      int bfsCnt = BfsOrder.calc(graph, i, bfs, visited);
      int lastIdx = -1;
      for (int j = 0; j < bfsCnt; ++j) {
        int idx = bfs[j];
        if ((graph.outDegree[idx] & 1) == 0) continue;
        if (lastIdx < 0) {
          lastIdx = idx;
        } else {
          graph.add(lastIdx, idx);
          lastOut[lastIdx] = graph.lastOut[lastIdx];
          lastOut[idx] = graph.lastOut[idx];
          lastIdx = -1;
        }
      }
      int pathCnt = EulerPath.calc(i, graph, lastOut, path);
      for (int j = 0; j < pathCnt; ++j) {
        int edgeIdx = path[j];
        if (edgeIdx >= m2) continue;
        out.println(graph.fromIdx[edgeIdx] + 1, graph.toIdx[edgeIdx] + 1);
      }
    }
  }
}
