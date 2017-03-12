package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

public class TaskB {
  static int[] DX = new int[]{ 1, 1, 1, 0};
  static int[] DY = new int[]{-1, 0, 1, 1};

  static int n = 4;
  char[][] board = new char[n][n];

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    for (int i = 0; i < n; ++i) {
      in.next(board[i]);
    }
    out.println(found() ? "YES" : "NO");
  }

  boolean found() {
    for (int i = 0; i < n; ++i) for (int j = 0; j < n; ++j) if (board[i][j] == '.') {
      board[i][j] = 'x';
      if (check()) return true;
      board[i][j] = '.';
    }
    return false;
  }

  boolean check() {
    for (int i = 0; i < n; ++i) for (int j = 0; j < n; ++j) if (board[i][j] == 'x') {
      for (int dir = 0; dir < 4; ++dir) {
        boolean valid = true;
        int x = i, y = j;
        for (int step = 1; step < 3; ++step) {
          x += DX[dir];
          y += DY[dir];
          if (x < 0 || x >= n || y < 0 || y >= n || board[x][y] != 'x') {
            valid = false;
            break;
          }
        }
        if (valid) return true;
      }
    }
    return false;
  }
}
