package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

import java.util.Arrays;

public class UVA1339 {
  final int MAXL = 100;

  int n;
  int[] cntS, cntT;
  char[] s, t;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    s = new char[MAXL];
    t = new char[MAXL];
    cntS = new int[26];
    cntT = new int[26];
    while (true) {
      n = in.next(s);
      if (n == 0) {
        break;
      }
      Arrays.fill(cntS, 0);
      Arrays.fill(cntT, 0);
      in.next(t);
      for (int i = 0; i < n; ++i) {
        ++cntS[s[i] - 'A'];
        ++cntT[t[i] - 'A'];
      }
      Arrays.sort(cntS);
      Arrays.sort(cntT);
      boolean isValid = true;
      for (int i = 0; i < 26; ++i) {
        if (cntS[i] != cntT[i]) {
          isValid = false;
          break;
        }
      }
      out.println(isValid ? "YES" : "NO");
    }
  }
}
