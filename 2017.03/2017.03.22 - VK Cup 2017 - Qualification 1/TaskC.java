package main;

import template.collections.list.CharArrayList;
import template.collections.queue.IntArrayQueue;
import template.io.QuickScanner;
import template.io.QuickWriter;

import java.util.Arrays;

public class TaskC {
  static int INF = Integer.MAX_VALUE >> 1;
  static char[] D = new char[]{'D', 'L', 'R', 'U'};
  static int[] DX = new int[]{1, 0, 0, -1};
  static int[] DY = new int[]{0, -1, 1, 0};

  int n, m, nm, length;
  int[][] dist;
  char[][] board;
  IntArrayQueue queueX, queueY;

  CharArrayList res;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    n = in.nextInt();
    m = in.nextInt();
    nm = n * m;
    length = in.nextInt();
    board = new char[n][m];
    dist = new int[n][m];
    queueX = new IntArrayQueue(nm);
    queueY = new IntArrayQueue(nm);
    int sX = -1, sY = -1;
    for (int i = 0; i < n; ++i) {
      Arrays.fill(dist[i], INF);
      in.next(board[i]);
      for (int j = 0; j < m; ++j) if (board[i][j] == 'X') {
        queueX.add(i);
        queueY.add(j);
        sX = i;
        sY = j;
        dist[i][j] = 0;
        break;
      }
    }
    res = new CharArrayList(length);
    bfs();
    calc(sX, sY);
    if (res.size != length) {
      out.println("IMPOSSIBLE");
    } else {
      out.println(res);
    }
  }

  void bfs() {
    while (!queueX.isEmpty()) {
      int x = queueX.poll(), y = queueY.poll(), value = dist[x][y];
      for (int dir = 0; dir < 4; ++dir) {
        int newX = x + DX[dir], newY = y + DY[dir];
        if (isValid(newX, newY) && dist[newX][newY] == INF) {
          queueX.add(newX);
          queueY.add(newY);
          dist[newX][newY] = value + 1;
        }
      }
    }
  }

  boolean isValid(int x, int y) {
    return 0 <= x && x < n && 0 <= y && y < m && board[x][y] != '*';
  }

  void calc(int x, int y) {
    for (int remLength = length - 1; remLength >= 0; --remLength) {
      for (int dir = 0; dir < 4; ++dir) {
        int newX = x + DX[dir], newY = y + DY[dir];
        if (isValid(newX, newY) && dist[newX][newY] <= remLength) {
          x = newX;
          y = newY;
          res.add(D[dir]);
          break;
        }
      }
    }
  }
}
