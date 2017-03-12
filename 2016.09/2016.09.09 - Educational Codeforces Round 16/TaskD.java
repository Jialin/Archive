package main;

import template.io.QuickScanner;
import template.io.QuickWriter;
import template.numbertheory.LongUtils;

import static java.lang.Math.max;

public class TaskD {
  long a1, b1, a2, b2, L, R;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    a1 = in.nextInt();
    b1 = in.nextInt();
    a2 = in.nextInt();
    b2 = in.nextInt();
    L = in.nextInt();
    R = in.nextInt();
    L = max(L, max(b1, b2));
    out.println(calc());
  }

  long calc() {
    if (L > R) return 0;
    long[] xs = new long[2];
    long gcdA = LongUtils.extGcd(a1, a2, xs);
    long lcmA = a1 / gcdA * a2;
    long common;
    if (b1 == b2) {
      common = b1;
    } else {
      long b21 = b2 - b1;
      if (b21 % gcdA != 0) return 0;
      long x = xs[0] * (b21 / gcdA);
      long deltaA = a2 / gcdA;
      if (x >= 0) {
        x -= x / deltaA * deltaA;
      } else {
        x += (-x + deltaA - 1) / deltaA * deltaA;
      }
      common = x * a1 + b1;
    }
    if (L <= common) {
      common -= (common - L) / lcmA * lcmA;
    } else {
      common += (L - common + lcmA - 1) / lcmA * lcmA;
    }
    return common > R ? 0 : (R - common) / lcmA + 1;
  }
}
