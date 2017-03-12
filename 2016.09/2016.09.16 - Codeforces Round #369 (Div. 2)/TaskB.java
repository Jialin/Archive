package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

public class TaskB {
  int n;
  long[][] mat;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    n = in.nextInt();
    mat = new long[n][0];
    for (int i = 0; i < n; ++i) {
      mat[i] = in.nextLong(n);
    }
    int[] xy = new int[2];
    find(xy);
    int x = xy[0], y = xy[1];
    long res;
    if (n == 1) {
      res = 1;
    } else {
      res = rowSum(x == 0 ? 1 : 0) - rowSum(x);
    }
    mat[x][y] = res;
    out.println(res > 0 && valid() ? res : -1);
  }

  public void find(int[] xy) {
    for (int i = 0; i < n; ++i) for (int j = 0; j < n; ++j) if (mat[i][j] == 0) {
      xy[0] = i;
      xy[1] = j;
      return ;
    }
  }

  public long rowSum(int i) {
    long res = 0;
    for (int j = 0; j < n; ++j) res += mat[i][j];
    return res;
  }

  public long columnSum(int j) {
    long res = 0;
    for (int i = 0; i < n; ++i) res += mat[i][j];
    return res;
  }

  public long diagSum1() {
    long res = 0;
    for (int i = 0; i < n; ++i) res += mat[i][i];
    return res;
  }

  public long diagSum2() {
    long res = 0;
    for (int i = 0; i < n; ++i) res += mat[i][n - 1 - i];
    return res;
  }

  boolean valid() {
    long sum = rowSum(0);
    for (int i = 0; i < n; ++i) if (sum != rowSum(i)) return false;
    for (int j = 0; j < n; ++j) if (sum != columnSum(j)) return false;
    return sum == diagSum1() && sum == diagSum2();
  }
}
