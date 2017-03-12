package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

import static java.lang.Math.max;

public class TaskA {
  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    char[] s = new char[100];
    int n = in.next(s);
    int res = 0;
    int last = -1;
    for (int i = 0; i < n; ++i) if (isVowel(s[i])) {
      res = max(res, i - last);
      last = i;
    }
    out.println(max(res, n - last));
  }

  boolean isVowel(char ch) {
    return ch == 'A' || ch == 'E' || ch == 'I' || ch == 'O' || ch == 'U' || ch == 'Y';
  }
}
