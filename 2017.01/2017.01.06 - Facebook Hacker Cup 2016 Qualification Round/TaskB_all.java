package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

public class TaskB_all {
  static int MAXN = 1000;

  int n;
  char[] s = new char[MAXN];
  boolean[][] covered = new boolean[2][MAXN];

  public void solve(int unusedTaskIdx, QuickScanner in, QuickWriter out) {
    int taskCnt = in.nextInt();
    for (int taskIdx = 1; taskIdx <= taskCnt; ++taskIdx) {
      System.err.printf("Handling #%d\n", taskIdx);
      n = in.nextInt();
      for (int i = 0; i < 2; ++i) {
        in.next(s);
        for (int j = 0; j < n; ++j) {
          covered[i][j] = s[j] == 'X';
        }
      }
      out.printf("Case #%d: %d\n", taskIdx, calc());
    }
  }

  int calc() {
    int res = 0;
    for (int i = 0; i < 2; ++i) {
      for (int j = 0; j < n; ++j) if (!covered[i][j] && !covered[i ^ 1][j] && (j == 0 || covered[i][j - 1]) && (j == n - 1 || covered[i][j + 1])) {
        ++res;
        cover(covered, i ^ 1, j);
      }
    }
    for (int i = 0; i < 2; ++i) {
      for (int j = 0; j < n; ++j) if (!covered[i][j] && (j == 0 || covered[i][j - 1])) {
        ++res;
      }
    }
    return res;
  }

  void cover(boolean[][] covered, int x, int y) {
    covered[x][y] = true;
    covered[x ^ 1][y] = true;
    for (int i = y - 1; i >= 0 && !this.covered[x][i]; --i) {
      covered[x][i] = true;
    }
    for (int i = y + 1; i < n && !this.covered[x][i]; ++i) {
      covered[x][i] = true;
    }
  }
}
