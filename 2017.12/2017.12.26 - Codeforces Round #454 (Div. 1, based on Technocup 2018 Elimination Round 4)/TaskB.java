package main;

import template.array.IntArrayUtils;
import template.io.QuickScanner;
import template.io.QuickWriter;

public class TaskB {
  int[][] board;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    int n = in.nextInt();
    int m = in.nextInt();
    board = new int[n][m];
    if (calcBoard(Math.min(n, m), Math.max(n, m), n > m)) {
      out.println("YES");
      for (int i = 0; i < n; ++i) {
        for (int j = 0; j < m; ++j) {
          out.print(board[i][j] + 1);
          out.print(' ');
        }
        out.println();
      }
    } else {
      out.println("NO");
    }
  }

  boolean calcBoard(int n, int m, boolean reversed) {
    if (n * m <= 9) {
      return calcBruteForce(n, m, reversed);
    }
    for (int i = 0; i < n; ++i) {
      int j = i & 1;
      for (int k = 1; k < m; k += 2) {
        int v = calcGrid(i, k, n, m, reversed);
        if (reversed) {
          board[j][i] = v;
        } else {
          board[i][j] = v;
        }
        j = j + 1 == m ? 0 : j + 1;
      }
      for (int k = 0; k < m; k += 2) {
        int v = calcGrid(i, k, n, m, reversed);
        if (reversed) {
          board[j][i] = v;
        } else {
          board[i][j] = v;
        }
        j = j + 1 == m ? 0 : j + 1;
      }
    }
    return true;
  }

  boolean calcBruteForce(int n, int m, boolean reversed) {
    int nm = n * m;
    boolean[][] invalid = new boolean[nm][nm];
    for (int i = 0; i < n; ++i) for (int j = 0; j < m; ++j) {
      int u = calcGrid(i, j, n, m, reversed);
      if (i > 0) {
        int v = calcGrid(i - 1, j, n, m, reversed);
        invalid[u][v] = invalid[v][u] = true;
      }
      if (j > 0) {
        int v = calcGrid(i, j - 1, n, m, reversed);
        invalid[u][v] = invalid[v][u] = true;
      }
    }
    int[] perm = new int[nm];
    for (int i = 0; i < nm; ++i) perm[i] = i;
    boolean found;
    do {
      found = true;
      int idx = 0;
      for (int i = 0; i < n; ++i) {
        for (int j = 0; j < m; ++j) {
          if (i > 0 && invalid[perm[idx]][perm[idx - m]]) {
            found = false;
            break;
          }
          if (j > 0 && invalid[perm[idx]][perm[idx - 1]]) {
            found = false;
            break;
          }
          ++idx;
        }
        if (!found) break;
      }
    } while (!found && IntArrayUtils.nextPermutation(perm));
    int idx = 0;
    for (int i = 0; i < n; ++i) for (int j = 0; j < m; ++j) {
      if (reversed) {
        board[j][i] = perm[idx];
      } else {
        board[i][j] = perm[idx];
      }
      ++idx;
    }
    return found;
  }

  int calcGrid(int x, int y, int n, int m, boolean reversed) {
    if (reversed) {
      return y * n + x;
    } else {
      return x * m + y;
    }
  }
}
