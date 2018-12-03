package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

public class UVA1585 {
  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    for (int remTask = in.nextInt(); remTask > 0; --remTask) {
      int res = 0;
      int cnt = 0;
      for (char c : in.next().toCharArray()) {
        if (c == 'X') {
          cnt = 0;
        } else {
          res += ++cnt;
        }
      }
      out.println(res);
    }
  }
}
