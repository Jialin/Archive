package main;

import template.io.QuickScanner;
import template.io.QuickWriter;
import template.numbertheory.number.IntModular;

public class TaskD {
  static IntModular MOD = new IntModular();

  int n;
  char[] s;
  int[] fact, invFact;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    s = new char[200000];
    n = in.next(s);
    fact = new int[n + 1];
    invFact = new int[n + 1];
    fact[0] = 1;
    for (int i = 1; i <= n; ++i) {
      fact[i] = MOD.mul(fact[i - 1], i);
    }
    invFact[n] = MOD.inverse(fact[n]);
    for (int i = n - 1; i >= 0; --i) {
      invFact[i] = MOD.mul(invFact[i + 1], i + 1);
    }
    out.println(calc());
  }

  int calc() {
    int l = 0, r = 0;
    for (int i = 0; i < n; ++i) if (s[i] == ')') {
      ++r;
    }
    int res = 0;
    for (int i = 0; i < n; ++i) {
      if (s[i] == '(') {
        ++l;
        if (r > 0) {
          res = MOD.add(res, MOD.mul(MOD.mul(
              fact[l + r - 1],
              invFact[l]),
              invFact[r - 1]));
        }
      } else {
        --r;
      }
    }
    return res;
  }
}
