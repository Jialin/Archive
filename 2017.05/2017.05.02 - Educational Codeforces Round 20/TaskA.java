package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

public class TaskA {
  int n, k;
  boolean[][] board;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    n = in.nextInt();
    k = in.nextInt();
    if (n * n < k) {
      out.println(-1);
      return;
    }
    board = new boolean[n][n];
    for (int i = 0; i < n; ++i) {
      for (int j = i; j < n; ++j) {
        if (k == 0) continue;
        if (i == j) {
          board[i][j] = true;
          --k;
        } else if (k > 1) {
          board[i][j] = board[j][i] = true;
          k -= 2;
        }
      }
    }
    if (k != 0) throw new IllegalArgumentException();
    for (int i = 0; i < n; ++i) {
      for (int j = 0; j < n; ++j) {
        out.print(board[i][j] ? 1 : 0);
        if (j + 1 < n) out.print(' ');
      }
      out.println();
    }
  }
}
