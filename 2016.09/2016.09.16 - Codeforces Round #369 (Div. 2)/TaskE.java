package main;

import template.io.QuickScanner;
import template.io.QuickWriter;
import template.numbertheory.IntUtils;

import static template.numbertheory.IntUtils.fix;
import static template.numbertheory.IntUtils.modInverse;
import static template.numbertheory.IntUtils.mul;
import static template.numbertheory.IntUtils.pow;
import static template.numbertheory.IntUtils.sub;

public class TaskE {
  static final int MOD = 1000003;

  int inv2;
  int a, b;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    IntUtils.MOD = MOD;
    long n = in.nextLong();
    long k = in.nextLong();
    if (n <= 60 && (1L << n) < k) {
      out.println("1 1");
      return ;
    }
    a = 1;
    b = pow(
        2,
        mul(
            (int) (n % (MOD - 1)),
            (int) (k % (MOD - 1)),
            MOD - 1));
    --k;
    int pow2 = pow(2, n);
    inv2 = modInverse(2);
    for (long remN = n; ; --remN, k >>= 1) {
      if (k == 0) {
        b = mul(b, pow(inv2, remN));
        break;
      }
      if (a > 0) {
        long k2 = (k - 1) >> 1;
        if (k2 >= MOD) {
          a = 0;
        } else {
          for (int i = 1, j = 0; j <= k2; i += 2, ++j) {
            a = mul(a, sub(pow2, fix(i)));
          }
        }
      }
      pow2 = mul(pow2, inv2);
      b = mul(b, pow(inv2, (k >> 1) + 1));
    }
    out.printf("%d %d\n", fix(b - a), b);
  }
}
