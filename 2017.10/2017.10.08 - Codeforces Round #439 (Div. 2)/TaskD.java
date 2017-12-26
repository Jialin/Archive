package main;

import template.collections.list.IntArrayList;
import template.graph.basic.BidirectionalGraph;
import template.io.QuickScanner;
import template.io.QuickWriter;
import template.numbertheory.number.IntModular;

public class TaskD {
  static final IntModular MOD = new IntModular();

  int n, m;
  int[] end1, end2;
  int res;
  boolean[] visited;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    n = in.nextInt();
    m = in.nextInt();
    end1 = new int[m];
    end2 = new int[m];
    in.nextInts(m, end1, end2);
    initPoints();
    initGraph();
    res = 0;
    visited = new boolean[points.size];
    for (int i = 0; i < points.size; ++i) {
      res = MOD.add(res, MOD.mul(pointCounts.get(i), dfs(i)));
    }
    out.println(res);
  }

  int dfs(int u) {
    visited[u] = true;
    int res = pointCounts.get(u);
    for (int v : graph.outNodes(u)) if (!visited[v]) {
      res = MOD.add(res, dfs(v));
    }
    visited[u] = false;
    return res;
  }

  BidirectionalGraph graph;

  void initGraph() {
    graph = new BidirectionalGraph(points.size, points.size + m);
    points.forEach((point) -> {
      if (point > 1) {
        graph.add(points.lowerBound(point), points.lowerBound(point >> 1));
      }
    });
    for (int i = 0; i < m; ++i) {
      graph.add(points.lowerBound(end1[i]), points.lowerBound(end2[i]));
    }
  }

  IntArrayList points, pointCounts;

  void initPoints() {
    points = new IntArrayList();
    for (int i = 0; i < m; ++i) {
      addKeyPoints(end1[i]);
      addKeyPoints(end2[i]);
    }
    points.sortAndUnique();
    if (points.isEmpty()) {
      points.add(1);
    }
    pointCounts = new IntArrayList(points.size);
    for (int point : points) {
      int cnt = 1;
      if (!points.binarySearch(point << 1)) {
        cnt += calcSubtree(point << 1);
      }
      if (!points.binarySearch((point << 1) | 1)) {
        cnt += calcSubtree((point << 1) | 1);
      }
      pointCounts.add(cnt);
    }
  }

  int calcSubtree(int x) {
    int lower = x, upper = x;
    int res = 0;
    for ( ; lower <= n; lower <<= 1, upper = (upper << 1) | 1) {
      res += Math.min(n, upper) - lower + 1;
      if (n <= upper) break;
    }
    return res;
  }

  void addKeyPoints(int x) {
    for ( ; x > 0; x >>= 1) points.add(x);
  }
}
