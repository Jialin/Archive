package main;

import template.array.LongArrayUtils;
import template.graph.BidirectionalGraph;
import template.io.QuickScanner;
import template.io.QuickWriter;
import template.numbertheory.hash.HashUtils;

import java.util.Arrays;

public class TaskD {
  int n;
  BidirectionalGraph graph;
  long[] hashs;
  long[][] childHashs;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    n = in.nextInt();
    graph = new BidirectionalGraph(n, n - 1);
    graph.init(n);
    for (int i = 1; i < n; ++i) {
      graph.add(in.nextInt() - 1, in.nextInt() - 1);
    }
    hashs = new long[n << 1];
    Arrays.fill(hashs, -1);
    childHashs = new long[n << 1][3];
    long[] res = new long[n];
    int m = 0, idx = (n << 1) - 1;
    for (int i = 0; i < n; ++i) if (graph.outDegree[i] < 4) {
      hashs[idx] = -1;
      res[m++] = calcHash(-1, i, idx);
    }
    Arrays.sort(res, 0, m);
    out.println(LongArrayUtils.unique(res, 0, m));
  }

  long calcHash(int u, int v, int idx) {
    if (hashs[idx] >= 0) {
      return hashs[idx];
    }
    int m = 0;
    for (int edgeIdx = graph.lastOut[v]; edgeIdx >= 0; edgeIdx = graph.nextOut[edgeIdx]) {
      int w = graph.toIdx[edgeIdx];
      if (w != u) {
        childHashs[idx][m++] = calcHash(v, w, edgeIdx);
      }
    }
    Arrays.sort(childHashs[idx], 0, m);
    hashs[idx] = HashUtils.calc(childHashs[idx], 0, m);
    return hashs[idx];
  }
}
