package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

public class TaskC {
  static final int INF = Integer.MAX_VALUE / 3;

  int n, m;
  int[] idx;
  char[] token;
  int[][] cost;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    n = in.nextInt();
    m = in.nextInt();
    idx = new int[m];
    token = new char[m];
    cost = new int[3][n];
    for (int i = 0; i < n; ++i) {
      in.next(token);
      cost[0][i] = cost[1][i] = cost[2][i] = INF;
      for (int j = 0; j < m; ++j) {
        int curCost = Math.min(j, m - j);
        if (Character.isDigit(token[j])) {
          cost[0][i] = Math.min(cost[0][i], curCost);
        } else if (Character.isLowerCase(token[j])) {
          cost[1][i] = Math.min(cost[1][i], curCost);
        } else {
          cost[2][i] = Math.min(cost[2][i], curCost);
        }
      }
    }
    int res = INF;
    for (int i = 0; i < n; ++i) for (int j = 0; j < n; ++j) if (i != j) {
      for (int k = 0; k < n; ++k) if (i != k && j != k) {
        res = Math.min(res, cost[0][i] + cost[1][j] + cost[2][k]);
      }
    }
    out.println(res);
  }
}
