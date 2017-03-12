package main;

import template.io.QuickScanner;
import template.io.QuickWriter;
import template.numbertheory.matrix.Matrix01;

public class TaskF {
  static int MAXBIT = 61;
  static long INF = 1000000000000000000L;

  int n, m;
  Matrix01[][] transform;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    n = in.nextInt();
    m = in.nextInt();
    transform = new Matrix01[2][MAXBIT];
    transform[0][0] = new Matrix01(n);
    transform[1][0] = new Matrix01(n);
    for (int i = 0; i < m; ++i) {
      int x = in.nextInt() - 1;
      int y = in.nextInt() - 1;
      int flip = in.nextInt();
      transform[flip][0].set(x, y);
    }
    for (int bit = 1; bit < MAXBIT; ++bit) {
      for (int flip = 0; flip < 2; ++flip) {
        transform[flip][bit] = Matrix01.mul(transform[flip][bit - 1], transform[flip ^ 1][bit - 1]);
      }
    }
    out.println(calc());
  }

  long calc() {
    long res = 0;
    int flip = 1;
    Matrix01[] matrix = new Matrix01[]{new Matrix01(n), new Matrix01(n)};
    matrix[flip].initEye(n);
    for (int bit = MAXBIT - 1; bit >= 0; --bit) {
      matrix[flip ^ 1].initMul(matrix[flip], transform[flip ^ 1][bit]);
      if (matrix[flip ^ 1].row[0].cardinality() > 0) {
        flip ^= 1;
        res |= 1L << bit;
      }
    }
    return res > INF ? -1 : res;
  }
}
