package main;

import template.array.Char2DArrayUtils;
import template.array.CharArrayUtils;
import template.io.QuickScanner;
import template.io.QuickWriter;

/*
ID: ouyang.ji1
LANG: JAVA
TASK: transform
*/
public class transform_main {
  int n;
  char[][] s;
  char[][] t;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    n = in.nextInt();
    s = new char[n][n];
    t = new char[n][n];
    for (int i = 0; i < n; ++i) {
      in.next(s[i]);
    }
    for (int i = 0; i < n; ++i) {
      in.next(t[i]);
    }
    out.println(calc());
  }

  int calc() {
    Char2DArrayUtils.rotate90(n, s);
    for (int i = 1; i < 4; ++i) {
      if (Char2DArrayUtils.equals(n, s, t)) {
        return i;
      }
      Char2DArrayUtils.rotate90(n, s);
    }
    for (int i = 0; i < n; ++i) {
      CharArrayUtils.reverse(s[i]);
    }
    if (Char2DArrayUtils.equals(n, s, t)) {
      return 4;
    }
    Char2DArrayUtils.rotate90(n, s);
    for (int i = 1; i < 4; ++i) {
      if (Char2DArrayUtils.equals(n, s, t)) {
        return 5;
      }
      Char2DArrayUtils.rotate90(n, s);
    }
    return Char2DArrayUtils.equals(n, s, t) ? 6 : 7;
  }
}
