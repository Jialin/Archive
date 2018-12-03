package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

public class UVA1588 {

  final int MAXN = 100;

  int n, m;
  char[] s, t;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    s = new char[MAXN];
    t = new char[MAXN];
    while (true) {
      n = in.next(s);
      if (n == 0) {
        break;
      }
      m = in.next(t);
      int res = n + m;
      for (int startJ = -n + 1; startJ < m; ++startJ) {
        boolean isValid = true;
        for (int i = 0, j = startJ; i < n; ++i, ++j) {
          if (0 <= j && j < m && s[i] == '2' && t[j] == '2') {
            isValid = false;
            break;
          }
        }
        if (isValid) {
          res = Math.min(res, Math.max(startJ, 0) + Math.max(n, m - startJ));
        }
      }
      out.println(res);
    }
  }
}
