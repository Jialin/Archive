package main;

import template.array.IntArrayUtils;
import template.direction.DirectionUtils;
import template.io.QuickScanner;
import template.io.QuickWriter;

public class TaskB {
  int n, m;
  int sx, sy;
  char[][] board;
  int opsCnt;
  char[] ops;

  int[] dirPerm;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    n = in.nextInt();
    m = in.nextInt();
    board = new char[n][m];
    for (int i = 0; i < n; ++i) in.next(board[i]);
    ops = new char[100];
    opsCnt = in.next(ops);
    dirPerm = new int[4];
    for (int i = 0; i < 4; ++i) dirPerm[i] = i;
    calcStartXY();
    int res = 0;
    do {
      if (isValid()) ++res;
    } while (IntArrayUtils.nextPermutation(dirPerm));
    out.println(res);
  }

  void calcStartXY() {
    for (int i = 0; i < n; ++i) for (int j = 0; j < m; ++j) if (board[i][j] == 'S') {
      sx = i;
      sy = j;
      return;
    }
  }

  boolean isValid() {
    int x = sx, y = sy;
    for (int i = 0; i < opsCnt; ++i) {
      int dir = dirPerm[ops[i] - '0'];
      x += DirectionUtils.DX[dir];
      y += DirectionUtils.DY[dir];
      if (x < 0 || x >= n || y < 0 || y >= m || board[x][y] == '#') return false;
      if (board[x][y] == 'E') return true;
    }
    return false;
  }
}
