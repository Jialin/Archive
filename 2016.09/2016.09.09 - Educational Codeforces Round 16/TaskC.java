package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

public class TaskC {
  int n, m;
  boolean[][] odd;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    n = in.nextInt();
    m = n >> 1;
    odd = new boolean[n][n];
    for (int i = 0; i < n; ++i) {
      odd[m][i] = odd[i][m] = true;
    }
    int rem = ((n * n + 1) >> 1) - (n << 1) + 1;
    if ((rem & 3) > 0) throw new RuntimeException();
    for ( ; rem > 0; rem -= 4) {
      fill();
    }
    int oddNumber = 1, evenNumber = 2;
    for (int i = 0; i < n; ++i) {
      for (int j = 0; j < n; ++j) {
        if (odd[i][j]) {
          out.printf("%d%c", oddNumber, j + 1 == n ? '\n' : ' ');
          oddNumber += 2;
        } else {
          out.printf("%d%c", evenNumber, j + 1 == n ? '\n' : ' ');
          evenNumber += 2;
        }
      }
    }
    if (oddNumber - 2 > n * n || evenNumber - 2 > n * n) throw new RuntimeException();
  }

  void fill() {
    for (int i = 0; i + 1 < n; ++i) {
      if (check(i, i)) {
        fill(i, i);
        return;
      }
      if (check(i, n - 1 - i - 1)) {
        fill(i, n - 1 - i - 1);
        return;
      }
    }
    for (int i = 0; i + 1 < n; ++i) {
      for (int j = 0; j + 1 < n; ++j) {
        if (check(i, j)) {
          fill(i, j);
          return;
        }
      }
    }
    throw new RuntimeException();
  }

  boolean check(int i, int j) {
    return !odd[i][j] && !odd[i + 1][j] && !odd[i][j + 1] && !odd[i + 1][j + 1];
  }

  void fill(int i, int j) {
    odd[i][j] = true;
    odd[i + 1][j] = true;
    odd[i][j + 1] = true;
    odd[i + 1][j + 1] = true;
  }
}
