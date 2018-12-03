package main;

import template.io.QuickScanner;
import template.io.QuickWriter;
import template.string.KMP;

public class UVA455 {
  final int MAXL = 80;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    KMP kmp = new KMP(MAXL);
    char[] s = new char[MAXL];
    for (int remTask = in.nextInt(); remTask > 0; --remTask) {
      int n = in.next(s);
      kmp.initPattern(s, 0, n);
      int res = n;
      for (int pos = kmp.fail[n - 1]; pos >= 0; pos = kmp.fail[pos]) {
        int subRes = n - 1 - pos;
        if (n % subRes == 0) {
          res = subRes;
          break;
        }
      }
      out.println(res);
      if (remTask > 1) {
        out.println();
      }
    }
  }
}
