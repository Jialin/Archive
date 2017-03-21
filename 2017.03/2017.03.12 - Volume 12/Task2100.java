package main;

import template.array.CharArrayUtils;
import template.io.QuickScanner;
import template.io.QuickWriter;

public class Task2100 {
  int n;
  char[] token = new char[64];

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    n = in.nextInt();
    int res = n + 2;
    for (int i = 0; i < n; ++i) {
      int length = in.next(token);
      if (CharArrayUtils.find(token, 0, length, '+') != -1) {
        ++res;
      }
    }
    if (res == 13) ++res;
    out.println(res + "00");
  }
}
