package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

public class TaskA {
  static int MAXN = 20;
  static int[] DX = new int[]{-1, 1, 0, 0};
  static int[] DY = new int[]{0, 0, -1, 1};

  int n, m, x, y, S;
  double p, q, res;
  double[][] prob = new double[MAXN][MAXN];
  int[][] cnt = new int[MAXN][MAXN];

  public void solve(int taskIdx, QuickScanner in, QuickWriter out) {
    System.err.printf("Handling #%d\n", taskIdx);
    n = in.nextInt();
    m = in.nextInt();
    x = in.nextInt();
    y = in.nextInt();
    S = in.nextInt();
    p = in.nextDouble();
    q = in.nextDouble();
    for (int i = 0; i < n; ++i) for (int j = 0; j < m; ++j) {
      prob[i][j] = in.nextNonSpaceChar() == 'A' ? p : q;
      cnt[i][j] = 0;
    }
    res = 0;
    dfs(x, y, S, 0);
    out.printf("Case #%d: %.7f\n", taskIdx, res);
  }

  void dfs(int x, int y, int remStep, double curRes) {
//System.out.printf("x:%d y:%d remStep:%d curRes:%f\n", x, y, remStep, curRes);
    if (remStep == 0) {
      res = Math.max(res, curRes);
      return;
    }
    for (int dir = 0; dir < 4; ++dir) {
      int xx = x + DX[dir], yy = y + DY[dir];
      if (!isValid(xx, yy)) continue;
      ++cnt[xx][yy];
      dfs(xx, yy, remStep - 1, curRes + calcProb(xx, yy, cnt[xx][yy]));
      --cnt[xx][yy];
    }
  }

  boolean isValid(int x, int y) {
    return 0 <= x && x < n && 0 <= y && y < m;
  }

  double calcProb(int x, int y, int cnt) {
    double res = 1;
    for (int i = 1; i < cnt; ++i) {
      res *= 1 - prob[x][y];
    }
    return res * prob[x][y];
  }
}
