package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

public class TaskE {
  int n, m;
  int offset;
  boolean[][] valid;
  int[][] from;
  String s;
  QuickWriter out;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    n = in.nextInt();
    offset = in.nextInt();
    s = in.next();
    m = (offset << 1) | 1;
    calc();
    this.out = out;
    if (valid[n][0]) {
      print(n, 0);
    } else if (valid[n][m - 1]) {
      print(n, m - 1);
    } else {
      out.print("NO");
    }
    out.println();
  }

  void calc() {
    valid = new boolean[n + 1][m];
    from = new int[n + 1][m];
    valid[0][offset] = true;
    for (int i = 0; i < n; ++i) {
      char ch = s.charAt(i);
      for (int j = 1; j + 1 < m; ++j) if (valid[i][j]) {
        if (ch == 'W' || ch == '?') {
          valid[i + 1][j + 1] = true;
          from[i + 1][j + 1] = j;
        }
        if (ch == 'L' || ch == '?') {
          valid[i + 1][j - 1] = true;
          from[i + 1][j - 1] = j;
        }
        if (ch == 'D' || ch == '?') {
          valid[i + 1][j] = true;
          from[i + 1][j] = j;
        }
      }
    }
  }

  void print(int idx, int value) {
    if (idx == 0) {
      if (value != offset) throw new IllegalArgumentException();
      return;
    }
    int prevValue = from[idx][value];
    if (Math.abs(value - prevValue) > 1) throw new IllegalArgumentException();
    print(idx - 1, prevValue);
    if (value == prevValue) {
      out.print('D');
    } else if (prevValue < value) {
      out.print('W');
    } else {
      out.print('L');
    }
  }
}
