package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

public class TaskE {
  static int MAXB = 10;
  static int MAXL = 64;
  static int MAXMASK = 1 << MAXB;

  long[][][] anyCnt;
  long[][] positiveCnt;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    init();
    for (int remQ = in.nextInt(); remQ > 0; --remQ) {
      int b = in.nextInt();
      long l = in.nextLong();
      long r = in.nextLong();
      out.println(calc(b, r) - calc(b, l - 1));
    }
  }

  void init() {
    anyCnt = new long[MAXB + 1][MAXL][MAXMASK];
    for (int b = 2; b <= MAXB; ++b) {
      anyCnt[b][0][0] = 1;
      for (int length = 1; length < MAXL; ++length) for (int mask = (1 << b) - 1; mask >= 0; --mask) {
        long sum = 0;
        for (int digit = 0; digit < b; ++digit) {
          sum += anyCnt[b][length - 1][mask ^ (1 << digit)];
        }
        anyCnt[b][length][mask] = sum;
      }
    }
    positiveCnt = new long[MAXB + 1][MAXL];
    for (int b = 2; b <= MAXB; ++b) for (int length = 1; length < MAXL; ++length) {
      long sum = 0;
      for (int digit = 1; digit < b; ++digit) {
        sum += anyCnt[b][length - 1][1 << digit];
      }
      positiveCnt[b][length] = sum;
    }
  }

  int totalLength;
  int[] digits;

  long calc(int base, long x) {
    if (x == 0) return 0;
    if (digits == null) {
      digits = new int[MAXL];
    }
    for (totalLength = 0 ; x > 0; x /= base) {
      digits[totalLength++] = (int) (x % base);
    }
    long res = 0;
    for (int length = 1; length < totalLength; ++length) {
      res += positiveCnt[base][length];
    }
    int mask = 0;
    for (int i = totalLength - 1; i >= 0; --i) {
      for (int digit = i + 1 == totalLength ? 1 : 0; digit < digits[i]; ++digit) {
        res += anyCnt[base][i][mask ^ (1 << digit)];
      }
      mask ^= 1 << digits[i];
    }
    return res + (mask == 0 ? 1 : 0);
  }
}
