package main;

import template.collections.list.IntArrayList;
import template.io.QuickScanner;
import template.io.QuickWriter;

/*
ID: ouyang.ji1
LANG: JAVA
TASK: palsquare
*/
public class palsquare_main {
  QuickWriter out;
  int base;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    this.out = out;
    IntArrayList digits = new IntArrayList(30);
    IntArrayList squareDigits = new IntArrayList(30);
    base = in.nextInt();
    for (int n = 1; n <= 300; ++n) {
      convert(n * n, squareDigits);
      if (squareDigits.isPalindrome()) {
        convert(n, digits);
        output(digits);
        out.print(' ');
        output(squareDigits);
        out.println();
      }
    }
  }

  void convert(int value, IntArrayList res) {
    res.clear();
    for ( ; value > 0; value /= base) {
      res.add(value % base);
    }
  }

  void output(IntArrayList lst) {
    for (int i = lst.size - 1; i >= 0; --i) {
      int digit = lst.get(i);
      if (digit < 10) {
        out.print(digit);
      } else {
        out.print((char) ('A' + digit - 10));
      }
    }
  }
}
