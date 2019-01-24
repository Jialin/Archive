package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

/*
ID: ouyang.ji1
LANG: JAVA
TASK: crypt1
*/
public class crypt1_main {

  int n;
  int[] digits;
  boolean[] used;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    int n = in.nextInt();
    digits = in.nextInt(n);
    used = new boolean[10];
    for (int digit : digits) {
      used[digit] = true;
    }
    int res = 0;
    for (int a : digits) for (int b : digits) for (int c : digits) {
      int abc = a * 100 + b * 10 + c;
      for (int d : digits) for (int e : digits) {
        if (isValidMedium(abc * d) && isValidMedium(abc * e) && isValidResult(abc * (d * 10 + e))) {
          ++res;
        }
      }
    }
    out.println(res);
  }

  boolean isValidMedium(int x) {
    return x < 1000
        && used[x / 100 % 10]
        && used[x / 10 % 10]
        && used[x % 10];
  }

  boolean isValidResult(int x) {
    return x < 10000
        && used[x / 1000 % 10]
        && used[x / 100 % 10]
        && used[x / 10 % 10]
        && used[x % 10];
  }
}
