package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

import java.util.Arrays;

public class manic_per {
  static int INF = 1000000000;

  int n, m;
  int[] start, end;
  int[][] dist;
  int[][] dpStart;
  int[][] dpEnd;

  public void solve(int taskIdx, QuickScanner in, QuickWriter out) {
    System.err.printf("Handling #%d\n", taskIdx);
    n = in.nextInt();
    dist = new int[n][n];
    for (int i = 0; i < n; ++i) {
      Arrays.fill(dist[i], INF);
      dist[i][i] = 0;
    }
    int edgeCnt = in.nextInt();
    m = in.nextInt();
    for (int i = 0; i < edgeCnt; ++i) {
      int a = in.nextInt() - 1;
      int b = in.nextInt() - 1;
      int g = in.nextInt();
      dist[a][b] = Math.min(dist[a][b], g);
      dist[b][a] = Math.min(dist[b][a], g);
    }
    for (int k = 0; k < n; ++k) for (int i = 0; i < n; ++i) for (int j = 0; j < n; ++j) {
      dist[i][j] = Math.min(dist[i][j], dist[i][k] + dist[k][j]);
    }
    start = new int[m];
    end = new int[m];
    for (int i = 0; i < m; ++i) {
      start[i] = in.nextInt() - 1;
      end[i] = in.nextInt() - 1;
    }
    out.printf("Case #%d: %d\n", taskIdx, calc());
  }

  int calc() {
    dpStart = new int[2][m];
    dpEnd = new int[2][m];
    for (int i = 0; i < 2; ++i) {
      Arrays.fill(dpStart[i], -1);
      Arrays.fill(dpEnd[i], -1);
    }
    for (int i = 0; i < m; ++i) {
      if (dist[i > 0 ? end[i - 1] : 0][start[i]] == INF
          || dist[start[i]][end[i]] == INF) return -1;
    }
    dpEnd[0][m - 1] = 0;
    return dist[0][start[0]] + calcStart(0, 0);
  }

  int calcStart(int carryPrev, int family) {
    if (dpStart[carryPrev][family] >= 0) return dpStart[carryPrev][family];
    int location = start[family];
    int res;
    if (carryPrev > 0) {
      // carry family-1 and family, go to end of family-1
      res = dist[location][end[family - 1]] + calcEnd(1, family - 1);
    } else {
      // carry family, go to end of family
      res = dist[location][end[family]] + calcEnd(0, family);
      // carry family, go to start of family+1
      if (family + 1 < m) {
        res = Math.min(
            res,
            dist[location][start[family + 1]] + calcStart(1, family + 1));
      }
    }
    dpStart[carryPrev][family] = res;
    return res;
  }

  int calcEnd(int carryNext, int family) {
    if (dpEnd[carryNext][family] >= 0) return dpEnd[carryNext][family];
    int location = end[family];
    int res;
    if (carryNext > 0) {
      // carry family+1, go to end of family+1
      res = dist[location][end[family + 1]] + calcEnd(0, family + 1);
      if (family + 2 < m) {
        // carry family+1, go to start of family+2
        res = Math.min(
            res,
            dist[location][start[family + 2]] + calcStart(1, family + 2));
      }
    } else {
      // empty, go to start of family+1
      res = dist[location][start[family + 1]] + calcStart(0, family + 1);
    }
    dpEnd[carryNext][family] = res;
    return res;
  }
}
