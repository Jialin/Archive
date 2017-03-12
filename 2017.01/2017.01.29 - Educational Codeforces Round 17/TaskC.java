package main;

import template.array.CharArrayUtils;
import template.array.IntArrayUtils;
import template.io.QuickScanner;
import template.io.QuickWriter;

public class TaskC {
  static int MAXN = 100000;

  int n, m;
  char[] a = new char[MAXN];
  char[] b = new char[MAXN];
  int[] forwardMatch, backwardMatch;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    n = in.next(a);
    m = in.next(b);
    forwardMatch = new int[n];
    backwardMatch = new int[n];
    match(forwardMatch);
    CharArrayUtils.reverse(a, 0, n);
    CharArrayUtils.reverse(b, 0, m);
    match(backwardMatch);
    IntArrayUtils.reverse(backwardMatch);
    calc();
    if (resLeft + resRight == 0) {
      out.println('-');
    } else {
      if (resLeft + resRight > m) {
        resLeft = m;
        resRight = 0;
      }
      CharArrayUtils.reverse(b, 0, m);
      for (int i = 0; i < resLeft; ++i) out.print(b[i]);
      for (int i = m - resRight; i < m; ++i) out.print(b[i]);
      out.println();
    }
  }

  void match(int[] match) {
    int pntB = 0;
    for (int i = 0; i < n; ++i) {
      if (pntB < m && a[i] == b[pntB]) ++pntB;
      match[i] = pntB;
    }
  }

  int resLeft, resRight;

  void calc() {
    resLeft = 0;
    resRight = backwardMatch[0];
    for (int i = 1; i < n; ++i) {
      if (resLeft + resRight < forwardMatch[i - 1] + backwardMatch[i]) {
        resLeft = forwardMatch[i - 1];
        resRight = backwardMatch[i];
      }
    }
    if (resLeft + resRight < forwardMatch[n - 1]) {
      resLeft = forwardMatch[n - 1];
      resRight = 0;
    }
  }
}
