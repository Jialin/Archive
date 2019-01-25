package main;

import template.collections.list.CharArrayList;
import template.io.QuickScanner;
import template.io.QuickWriter;

public class UVA213 {
  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    CharArrayList s = new CharArrayList(1024);
    while (true) {
      s.clear();
      if (in.nextLine(s) == 0) {
        break;
      }
      while (true) {
        int length = read(3, in);
        if (length == 0) {
          break;
        }
        int offset = (1 << length) - length - 1;
        while (true) {
          int idx = read(length, in);
          if (idx + 1 == (1 << length)) {
            break;
          }
          out.print(s.get(offset + idx));
        }
      }
      out.println();
      in.nextLine(s);
    }
  }

  int read(int length, QuickScanner in) {
    int res = 0;
    for (int i = 0; i < length; ++i) {
      res = (res << 1) | (in.nextNonSpaceChar() - '0');
    }
    return res;
  }
}
