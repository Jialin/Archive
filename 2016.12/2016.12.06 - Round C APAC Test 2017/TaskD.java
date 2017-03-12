package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TaskD {
  int n;
  Soldier[] soldiers;
  List<Soldier> ad, da;
  boolean[] removed;

  public void solve(int taskIdx, QuickScanner in, QuickWriter out) {
    System.err.printf("Handling #%d\n", taskIdx);
    n = in.nextInt();
    soldiers = new Soldier[n];
    ad = new ArrayList<>(n);
    da = new ArrayList<>(n);
    for (int i = 0; i < n; ++i) {
      int a = in.nextInt();
      int d = in.nextInt();
      Soldier soldier = new Soldier(a, d, i);
      soldiers[i] = soldier;
      ad.add(soldier);
      da.add(soldier);
    }
    Collections.sort(ad, (x, y) -> x.a == y.a ? x.d - y.d : x.a - y.a);
    Collections.sort(da, (x, y) -> x.d == y.d ? x.a - y.a : x.d - y.d);
    removed = new boolean[n];
    out.printf("Case #%d: %s\n", taskIdx, win() ? "YES" : "NO");
  }

  boolean win() {
    for (int adPnt = n - 1, daPnt = n - 1; adPnt >= 0; ) {
      int maxA = ad.get(adPnt).a, maxD = da.get(daPnt).d;
      if (ad.get(adPnt).d == maxD) {
        return true;
      }
      for ( ; adPnt >= 0 && ad.get(adPnt).a == maxA; --adPnt) {
        removed[ad.get(adPnt).idx] = true;
      }
      for ( ; daPnt >= 0 && da.get(daPnt).d == maxD; --daPnt) {
        removed[da.get(daPnt).idx] = true;
      }
      for ( ; adPnt >= 0 && removed[ad.get(adPnt).idx]; --adPnt) {}
      for ( ; daPnt >= 0 && removed[da.get(daPnt).idx]; --daPnt) {}
    }
    return false;
  }

  class Soldier {
    final int a, d, idx;

    Soldier(int a, int d, int idx) {
      this.a = a;
      this.d = d;
      this.idx = idx;
    }
  }
}
