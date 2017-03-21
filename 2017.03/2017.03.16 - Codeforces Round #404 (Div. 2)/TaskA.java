package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

public class TaskA {
  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    int n = in.nextInt();
    char[] token = new char[32];
    int res = 0;
    for (int i = 0; i < n; ++i) {
      in.next(token);
      switch (token[0]) {
        case 'T':
          res += 4;
          break;
        case 'C':
          res += 6;
          break;
        case 'O':
          res += 8;
          break;
        case 'D':
          res += 12;
          break;
        case 'I':
          res += 20;
          break;
      }
    }
    out.println(res);
  }
}
