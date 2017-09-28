package main;

import template.io.QuickScanner;
import template.io.QuickWriter;
import template.numbertheory.matrix.LongMatrixUtils;

public class TaskC {

  static int N = 11;

  long[][][] bases;
  long[][] res;
  long k;
  int a, b;
  int[][] A, B;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    k = in.nextLong();
    a = in.nextInt() - 1;
    b = in.nextInt() - 1;
    A = getMatrix(in);
    B = getMatrix(in);
    initBase();
    res = new long[N][N];
    LongMatrixUtils.pow(res, bases, k);
    out.println(res[getIdx(a, b)][10], res[getIdx(a, b)][9]);
  }

  int[][] getMatrix(QuickScanner in) {
    int[][] res = new int[3][3];
    for (int i = 0; i < 3; ++i) for (int j = 0; j < 3; ++j) {
      res[i][j] = in.nextInt() - 1;
    }
    return res;
  }

  void initBase() {
    bases = new long[63][N][N];
    bases[0][getIdx(1, 2)][9] = 1;
    bases[0][getIdx(0, 1)][9] = 1;
    bases[0][getIdx(2, 0)][9] = 1;
    bases[0][getIdx(2, 1)][10] = 1;
    bases[0][getIdx(1, 0)][10] = 1;
    bases[0][getIdx(0, 2)][10] = 1;
    bases[0][9][9] = 1;
    bases[0][10][10] = 1;
    for (int a = 0; a < 3; ++a) for (int b = 0; b < 3; ++b) {
      bases[0][getIdx(a, b)][getIdx(A[a][b], B[a][b])] = 1;
    }
    for (int idx = 1; idx < bases.length; ++idx) {
      LongMatrixUtils.mul(bases[idx], bases[idx - 1], bases[idx - 1]);
    }
  }

  int getIdx(int a, int b) {
    return a * 3 + b;
  }
}
