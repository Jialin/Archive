package main;

import template.array.CharArrayUtils;
import template.io.QuickScanner;
import template.io.QuickWriter;
import template.numbertheory.complex.Complex;
import template.numbertheory.fft.FastFourierTransformer;

public class TaskE {
  static double EPS = 1E-6;

  int n, m, r, c, size;
  char[][] table, pattern;

  Complex[][] a, b;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    n = in.nextInt();
    m = in.nextInt();
    table = new char[n][m];
    for (int i = 0; i < n; ++i) {
      in.next(table[i]);
    }
    r = in.nextInt();
    c = in.nextInt();
    int maxSize = Math.max(n + r - 1, m + c - 1);
    size = Integer.highestOneBit(maxSize);
    if (size < maxSize) size <<= 1;
    initAB();
    init(a, table, n + r - 1, m + c - 1, false);
    pattern = new char[r][c];
    int cnt = 0;
    for (int i = 0, j = r - 1; i < r; ++i, --j) {
      in.next(pattern[j]);
      CharArrayUtils.reverse(pattern[j]);
      for (int k = 0; k < c; ++k) if (pattern[j][k] != '?') {
        ++cnt;
      }
    }
    init(b, pattern, r, c, true);
    new FastFourierTransformer(size).mul(a, b, a);
    for (int i = 0, x = r - 1; i < n; ++i, ++x) {
      for (int j = 0, y = c - 1; j < m; ++j, ++y) {
        out.print(a[x][y].real + EPS > cnt ? '1' : '0');
      }
      out.println();
    }
  }

  void initAB() {
    a = new Complex[size][size];
    b = new Complex[size][size];
    for (int i = 0; i < size; ++i) for (int j = 0; j < size; ++j) {
      a[i][j] = Complex.zero();
      b[i][j] = Complex.zero();
    }
  }

  void init(Complex[][] a, char[][] table, int n, int m, boolean negative) {
    for (int i = 0, x = 0; i < n; ++i, x = x + 1 == table.length ? 0 : x + 1) {
      for (int j = 0, y = 0; j < m; ++j, y = y + 1 == table[0].length ? 0 : y + 1) {
        initComplex(a[i][j], table[x][y], negative);
      }
    }
  }

  void initComplex(Complex complex, char x, boolean negative) {
    if (x == '?') {
      complex.initZero();
    } else {
      double angle = (negative ? -1 : 1) * Math.PI / 13 * (x - 'a');
      complex.initPolar(1, angle);
    }
  }
}
