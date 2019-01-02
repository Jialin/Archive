package main;

import template.collections.list.IntArrayList;
import template.io.QuickScanner;
import template.io.QuickWriter;

/*
ID: ouyang.ji1
LANG: JAVA
TASK: dualpal
*/
public class dualpal_main {
  IntArrayList digits;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    digits = new IntArrayList(32);
    int n = in.nextInt();
    for (int v = in.nextInt() + 1; n > 0; ++v) {
      if (isValid(v)) {
        out.println(v);
        --n;
      }
    }
  }

  boolean isValid(int n) {
    int cnt = 0;
    for (int base = 2; base <= 10; ++base) {
      digits.clear();
      for (int v = n; v > 0; v /= base) {
        digits.add(v % base);
      }
      if (digits.isPalindrome()) {
        ++cnt;
        if (cnt == 2) {
          break;
        }
      }
    }
    return cnt == 2;
  }
}
