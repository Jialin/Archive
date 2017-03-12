package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

public class TaskC {
  int n;
  char[] s;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    n = in.nextInt();
    s = new char[n];
    in.next(s);
    int res = 0;
    char lr = '!', du = '!';
    for (int i = 0; i < n; ++i) {
      char x = s[i];
      if (x == 'L' || x == 'R') {
        if (lr == ' ' || lr == x) {
          lr = x;
        } else {
          lr = x;
          du = ' ';
          ++res;
        }
      } else {
        if (du == ' ' || du == x) {
          du = x;
        } else {
          lr = ' ';
          du = x;
          ++res;
        }
      }
    }
    out.println(res);
  }
}
