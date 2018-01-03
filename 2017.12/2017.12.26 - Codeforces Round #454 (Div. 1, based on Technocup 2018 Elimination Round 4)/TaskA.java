package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

public class TaskA {
  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    int mask = (1 << 26) - 1;
    int res = 0;
    for (int remQuery = in.nextInt(); remQuery > 0; --remQuery) {
      char op = (char) in.nextNonSpaceChar();
      int curMask = getMask(in);
      if (Integer.bitCount(mask) == 1) {
        switch (op) {
          case '!':
            ++res;
            break;
          case '.':
            break;
          case '?':
            if (curMask != mask) ++res;
            break;
        }
      } else {
        switch (op) {
          case '!':
            mask &= curMask;
            break;
          case '.':
          case '?':
            mask &= ~curMask;
            break;
        }
      }
    }
    out.println(res);
  }

  static int MAXL = 100000;

  char[] s;

  int getMask(QuickScanner in) {
    if (s == null) {
      s = new char[MAXL];
    }
    int length = in.next(s);
    int mask = 0;
    for (int i = 0; i < length; ++i) {
      mask |= 1 << (s[i] - 'a');
    }
    return mask;
  }
}
