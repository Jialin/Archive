package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

public class TaskD {
  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    int n = in.nextInt();
  }

  class Server {
    final QuickScanner in;
    final QuickWriter out;

    Server(QuickScanner in, QuickWriter out) {
      this.in = in;
      this.out = out;
    }

    int query(char[] s) {
      out.print("? ");
      out.println(s);
      out.flush();
      return in.nextInt();
    }

    void answer(int zero, int one) {
      out.printf("! %d %d\n", zero, one);
      out.flush();
    }
  }
}

// len:         '000...000'       prevH -> zero: [prevH]  one: [len-prevH]
// len1, len2:  '11..11' '00.00'      H ->
//              z0 o0    z1 o1
// z0+z1=prevH
// o0+z1=H
// o0-z0=(len1-z0)-z0=H-prevH
// z0=(len1+prevH-H)/2
