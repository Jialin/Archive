package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

public class TaskB {
  char[][] board;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    board = new char[9][9];
    for (int i = 0; i < 9; ++i) for (int j = 0; j < 9; ++j) {
      board[i][j] = (char) in.nextNonSpaceChar();
    }
    int x = (in.nextInt() - 1) % 3 * 3;
    int y = (in.nextInt() - 1) % 3 * 3;
    if (hasEmpty(x, y)) {
      for (int i = x; i < x + 3; ++i) for (int j = y; j < y + 3; ++j) if (board[i][j] == '.') {
        board[i][j] = '!';
      }
    } else {
      for (int i = 0; i < 9; ++i) for (int j = 0; j < 9; ++j) if (board[i][j] == '.') {
        board[i][j] = '!';
      }
    }
    for (int i = 0; i < 9; ++i) {
      if (i > 0 && i % 3 == 0) out.println();
      for (int j = 0; j < 9; ++j) {
        if (j > 0 && j % 3 == 0) out.print(' ');
        out.print(board[i][j]);
      }
      out.println();
    }
  }

  boolean hasEmpty(int x, int y) {
    for (int i = x; i < x + 3; ++i) for (int j = y; j < y + 3; ++j) {
      if (board[i][j] == '.') return true;
    }
    return false;
  }
}
