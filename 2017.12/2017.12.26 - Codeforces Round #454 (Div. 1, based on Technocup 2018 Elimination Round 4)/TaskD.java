package main;

import template.collections.list.IntArrayList;
import template.io.QuickScanner;
import template.io.QuickWriter;
import template.numbertheory.number.IntModular;
import template.numbertheory.number.IntUtils;

public class TaskD {
  int n;
  IntArrayList phis;
  int[] ws;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    n = in.nextInt();
    phis = calcPhis(in.nextInt());
    ws = in.nextInt(n);
    for (int remQuery = in.nextInt(); remQuery > 0; --remQuery) {
      int lower = in.nextInt() - 1;
      int upper = Math.min(in.nextInt(), lower + phis.size) - 1;
      out.println(calc(lower, upper));
    }
  }

  int calc(int lower, int upper) {
    int last = 1;
    for (int i = upper, j = upper - lower; i >= lower; --i, --j) {
      int phi = phis.get(j);
      last = IntModular.powPlus(ws[i], last, phi);
    }
    return last % phis.get(0);
  }

  IntArrayList calcPhis(int mod) {
    IntArrayList res = new IntArrayList(64);
    res.add(mod);
    while (res.peekLast() > 1) {
      res.add(IntUtils.phi(res.peekLast()));
    }
    return res;
  }
}
