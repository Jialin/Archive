package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

public class UVA232 {
  final int MAXN = 10;

  int n, m;
  char[][] board;
  int[][] idx;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    board = new char[MAXN][MAXN];
    idx = new int[MAXN][MAXN];
    int taskIdx = 0;
    while (true) {
      n = in.nextInt();
      if (n == 0) {
        break;
      }
      m = in.nextInt();
      for (int i = 0; i < n; ++i) {
        in.next(board[i]);
      }
      int idxCnt = 0;
      for (int i = 0; i < n; ++i) for (int j = 0; j < m; ++j) {
        if (board[i][j] != '*' && (i == 0 || board[i - 1][j] == '*' || j == 0 || board[i][j - 1] == '*')) {
          idx[i][j] = ++idxCnt;
        }
      }
      if (taskIdx > 0) {
        out.println();
      }
      out.printf("puzzle #%d:\n", ++taskIdx);
      out.println("Across");
      for (int i = 0; i < n; ++i) for (int j = 0; j < m; ++j) {
        if (board[i][j] != '*' && (j == 0 || board[i][j - 1] == '*')) {
          out.printf("%3d.", idx[i][j]);
          outWord(out, i, j, 0, 1);
        }
      }
      out.println("Down");
      for (int i = 0; i < n; ++i) for (int j = 0; j < m; ++j) {
        if (board[i][j] != '*' && (i == 0 || board[i - 1][j] == '*')) {
          out.printf("%3d.", idx[i][j]);
          outWord(out, i, j, 1, 0);
        }
      }
    }
  }

  void outWord(QuickWriter out, int x, int y, int dx, int dy) {
    out.print(board[x][y]);
    for (x += dx, y += dy; x < n && y < m && board[x][y] != '*'; x += dx, y += dy) {
      out.print(board[x][y]);
    }
    out.println();
  }
}
