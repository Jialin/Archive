package main;

import template.Constants;
import template.io.QuickScanner;
import template.io.QuickWriter;

import java.util.Arrays;

public class UVA227 {

  char[][] board;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    board = new char[5][5];
    int taskIdx = 0;
    initBoard();
    while (in.nextLine(board[0], true) > 1) {
      for (int i = 1; i < 5; ++i) {
        in.nextLine(board[i], true);
      }
      int x = -1;
      int y = -1;
      for (int i = 0; i < 5; ++i) {
        for (int j = 0; j < 5; ++j) {
          if (board[i][j] == ' ') {
            x = i;
            y = j;
          }
        }
      }
      boolean isValid = true;
      while (true) {
        char op = (char) in.nextNonSpaceChar();
        if (op == '0') {
          break;
        }
        if (!isValid) {
          continue;
        }
        int dir = -1;
        switch (op) {
          case 'A':
            dir = 0;
            break;
          case 'R':
            dir = 1;
            break;
          case 'B':
            dir = 2;
            break;
          case 'L':
            dir = 3;
            break;
        }
        if (dir < 0) {
          isValid = false;
          continue;
        }
        int newX = x + Constants.DX4[dir];
        int newY = y + Constants.DY4[dir];
        if (0 <= newX && newX < 5 && 0 <= newY && newY < 5) {
          board[x][y] ^= board[newX][newY];
          board[newX][newY] ^= board[x][y];
          board[x][y] ^= board[newX][newY];
          x = newX;
          y = newY;
        } else {
          isValid = false;
        }
      }
      if (taskIdx > 0) {
        out.println();
      }
      out.printf("Puzzle #%d:\n", ++taskIdx);
      if (!isValid) {
        out.println("This puzzle has no final configuration.");
      } else {
        for (int i = 0; i < 5; ++i) {
          for (int j = 0; j < 5; ++j) {
            out.printf("%c%c", board[i][j], j == 4 ? '\n' : ' ');
          }
        }
      }
      initBoard();
    }
  }

  void initBoard() {
    for (int i = 0; i < 5; ++i) {
      Arrays.fill(board[i], ' ');
    }
  }
}
