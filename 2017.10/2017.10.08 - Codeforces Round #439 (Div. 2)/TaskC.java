package main;

import template.io.QuickScanner;
import template.io.QuickWriter;
import template.numbertheory.number.IntModular;

public class TaskC {
  static IntModular MOD = new IntModular(998244353);

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    int a = in.nextInt();
    int b = in.nextInt();
    int c = in.nextInt();
    out.println(MOD.mul(MOD.mul(
        calc(a, b),
        calc(a, c)),
        calc(b, c)));
  }

  int calc(int n, int m) {
    int res = 1, mul = 1;
    for (int a = n, b = m, k = 1; a > 0 && b > 0; --a, --b, ++k) {
      mul = MOD.mul(MOD.mul(MOD.mul(
          mul,
          a),
          b),
          MOD.inverse(k));
      res = MOD.add(res, mul);
    }
    return res;
  }
}
