package main;

import template.io.QuickScanner;
import template.io.QuickWriter;
import template.numbertheory.number.IntModular;

/*
ID: ouyang.ji1
LANG: JAVA
TASK: ride
*/
public class ride_main {
  final IntModular MOD =  new IntModular(47);

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    out.println(compute(in.next()) == compute(in.next()) ? "GO" : "STAY");
  }

  int compute(String cs) {
    int res = 1;
    for (char c : cs.toCharArray()) {
      res = MOD.mul(res, c - 'A' + 1);
    }
    return res;
  }
}
