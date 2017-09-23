package main;

import template.array.IntArrayUtils;
import template.io.QuickScanner;
import template.io.QuickWriter;

import java.util.Arrays;

public class TaskD {
  static int MAXL = 200000;

  int n, m;
  char[] s, t;
  int[] perm;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    s = new char[MAXL];
    t = new char[MAXL];
    n = in.next(s);
    m = in.next(t);
    perm = in.nextInt(n);
    IntArrayUtils.update(perm, -1);
    marked = new boolean[n];
    out.println(calc());
  }

  boolean[] marked;

  int calc() {
    int lower = 1, upper = n, res = 0;
    while (lower <= upper) {
      int medium = (lower + upper) >> 1;
      Arrays.fill(marked, false);
      for (int i = 0; i < medium; ++i) marked[perm[i]] = true;
      if (possible()) {
        res = medium;
        lower = medium + 1;
      } else {
        upper = medium - 1;
      }
    }
    return res;
  }

  boolean possible() {
    int pnt = 0;
    for (int i = 0; i < m; ++i) {
      for ( ; pnt < n && (marked[pnt] || s[pnt] != t[i]); ++pnt) {}
      if (pnt >= n) return false;
      ++pnt;
    }
    return true;
  }
}
