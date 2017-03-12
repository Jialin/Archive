package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

public class TaskBsmall {
  public void solve(int taskIdx, QuickScanner in, QuickWriter out) {
    System.err.printf("Handling #%d\n", taskIdx);
    int limit = 27;
    for (int n = 1; n <= limit; ++n) for (int m = n; m <= limit; ++m) if (n * m <= limit) {
      System.out.printf("n:%d m:%d res:%d\n", n, m, calc(n, m));
    }
//    int n = in.nextInt();
//    int m = in.nextInt();
//    out.printf("Case #%d: %d\n", taskIdx, calc(n, m));
  }

  int calc(int n, int m) {
    int res = 0;
    for (int mask = (1 << (n * m)) - 1; mask >= 0; --mask) if (isValid(n, m, mask)) {
      res = Math.max(res, Integer.bitCount(mask));
    }
//    for (int mask = (1 << (n * m)) - 1; mask >= 0; --mask) if (isValid(n, m, mask) && Integer.bitCount(mask) == res) {
//      print(n, m, mask);
//    }
    return res;
  }

  boolean isValid(int n, int m, int mask) {
    for (int i = 0; i < n; ++i) {
      for (int j = 0; j < m; ++j) if (seated(n, m, mask, i, j)) {
        if (seated(n, m, mask, i - 1, j) && seated(n, m, mask, i + 1, j)) {
          return false;
        }
        if (seated(n, m, mask, i, j - 1) && seated(n, m, mask, i, j + 1)) {
          return false;
        }
      }
    }
    return true;
  }

  boolean seated(int n, int m, int mask, int x, int y) {
    if (x < 0 || x >= n || y < 0 || y >= m) return false;
    return ((mask >> (x * m + y)) & 1) > 0;
  }

  void print(int n, int m, int mask) {
    System.out.printf("=====n:%d m:%d=====\n", n, m);
    for (int i = 0; i < n; ++i) {
      for (int j = 0; j < m; ++j) {
        System.out.print(seated(n, m, mask, i, j) ? '#' : ".");
      }
      System.out.println();
    }
  }
}
