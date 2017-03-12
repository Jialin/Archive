package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

public class TaskB {
  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    int n = in.nextInt();
    int m = in.nextInt();
    boolean[] row = new boolean[n];
    boolean[] column = new boolean[n];
    int rowCnt = 0, columnCnt = 0;
    for (int i = 0; i < m; ++i) {
      int x = in.nextInt() - 1;
      int y = in.nextInt() - 1;
      if (!row[x]) {
        row[x] = true;
        ++rowCnt;
      }
      if (!column[y]) {
        column[y] = true;
        ++columnCnt;
      }
      out.print((long) n * n - (long) (rowCnt + columnCnt) * n + (long) rowCnt * columnCnt);
      out.print(i < m - 1 ? ' ' : '\n');
    }
  }
}
