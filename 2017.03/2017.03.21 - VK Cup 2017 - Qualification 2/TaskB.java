package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

public class TaskB {
  long n;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    n = in.nextLong();
    out.println(calc());
  }

  long calc() {
    int resCnt = calcDigitSum(n);
    long res = n;
    for (long m = n / 10, pow = 10; m > 0; m /= 10, pow *= 10) {
      long curN = m * pow - 1;
      if (curN < 0 || n <= curN) continue;
      int curCnt = calcDigitSum(curN);
      if (resCnt < curCnt) {
        resCnt = curCnt;
        res = curN;
      }
    }
    return res;
  }

  int calcDigitSum(long value) {
    int res = 0;
    for ( ; value > 0; value /= 10) {
      res += value % 10;
    }
    return res;
  }
}
