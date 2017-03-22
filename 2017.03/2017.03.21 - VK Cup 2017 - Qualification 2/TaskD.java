package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

public class TaskD {
  int n;
  char[] s;
  char[][] board;
  int maxSize;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    n = in.nextInt();
    s = new char[n];
    in.next(s);
    maxSize = calcSize();
    board = new char[maxSize * 2 + 3][1024];
    int m = 1;
    for (int i = 0, size = maxSize; i < n; ++i) {
      if (s[i] == '[') {
        putFront(m, size);
      } else {
        putBack(m, size);
      }
      if (i + 1 == n) continue;
      if (s[i] == '[') {
        if (s[i + 1] == '[') {
          --size;
          ++m;
        } else {
          m += 3;
        }
      } else {
        if (s[i + 1] == '[') {
          m += 2;
        } else {
          ++size;
          ++m;
        }
      }
    }
    for (char[] row : board) {
      for (int i = 0; i <= m; ++i) {
        out.print(row[i] > 0 ? row[i] : ' ');
      }
      out.println();
    }
  }

  int calcSize() {
    int res = 0, depth = 0;
    for (int i = 0; i < n; ++i) {
      depth += s[i] == '[' ? 1 : -1;
      res = Math.max(res, depth);
    }
    return res - 1;
  }

  void putFront(int idx, int size) {
    putColumn(idx - 1, size);
    board[maxSize + 1 - size - 1][idx] = '-';
    board[maxSize + 1 + size + 1][idx] = '-';
  }

  void putBack(int idx, int size) {
    putColumn(idx, size);
    board[maxSize + 1 - size - 1][idx - 1] = '-';
    board[maxSize + 1 + size + 1][idx - 1] = '-';
  }

  void putColumn(int idx, int size) {
    board[maxSize + 1 - size - 1][idx] = '+';
    board[maxSize + 1][idx] = '|';
    board[maxSize + 1 + size + 1][idx] = '+';
    for (int i = 1; i <= size; ++i) {
      board[maxSize + 1 - i][idx] = '|';
      board[maxSize + 1 + i][idx] = '|';
    }
  }
}
