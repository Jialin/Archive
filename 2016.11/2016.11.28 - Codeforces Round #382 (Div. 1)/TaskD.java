package main;

import template.io.QuickScanner;
import template.io.QuickWriter;
import template.numbertheory.matrix.Matrix01Utils;

import java.util.BitSet;

public class TaskD {
  int n, m;
  BitSet[] matrix;
  int[] row, column;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    n = in.nextInt();
    m = in.nextInt();
    matrix = new BitSet[n];
    for (int i = 0; i < n; ++i) {
      matrix[i] = new BitSet(n);
    }
    row = new int[m];
    column = new int[m];
    for (int i = 0; i < m; ++i) {
      row[i] = in.nextInt() - 1;
      column[i] = in.nextInt() - 1;
      matrix[row[i]].set(column[i]);
    }
    Matrix01Utils.inverse(n, matrix, matrix);
    for (int i = 0; i < m; ++i) {
      out.println(matrix[column[i]].get(row[i]) ? "NO" : "YES");
    }
  }
}
