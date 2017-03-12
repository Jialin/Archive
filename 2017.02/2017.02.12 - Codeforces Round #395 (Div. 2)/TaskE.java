package main;

import template.collections.list.IntArrayList;
import template.io.QuickScanner;
import template.io.QuickWriter;
import template.numbertheory.number.IntModular;

public class TaskE {
  IntModular MOD;

  int n, m;
  IntArrayList a;
  int resX, resD;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    m = in.nextInt();
    n = in.nextInt();
    MOD = new IntModular(m);
    boolean flip = false;
    if ((n << 1) > m) {
      flip = true;
      boolean[] used = new boolean[m];
      for (int i = 0; i < n; ++i) {
        used[in.nextInt()] = true;
      }
      n = m - n;
      a = new IntArrayList(n);
      for (int i = 0; i < m; ++i) if (!used[i]) {
        a.add(i);
      }
    } else {
      a = new IntArrayList(n);
      in.nextInt(n, a);
      a.sort();
    }
    if (calc()) {
      if (flip) {
        resX = MOD.add(resX, MOD.mul(resD, n));
      }
      out.println(resX, resD);
    } else {
      out.println(-1);
    }
  }

  int sumA;

  boolean calc() {
    if (n == 0) {
      resX = 0;
      resD = 1;
      return true;
    }
    if (n == 1) {
      resX = a.get(0);
      resD = 1;
      return true;
    }
    int diff = MOD.sub(a.get(1), a.get(0));
    int cnt = 0;
    for (int i = 0; i < n; ++i) {
      if (a.binarySearch(MOD.add(a.get(i), diff))) {
        ++cnt;
      }
    }
    cnt = n - cnt;
    sumA = 0;
    for (int i = 0; i < n; ++i) {
      sumA = MOD.add(sumA, a.get(i));
    }
    return valid(diff, cnt) || valid(m - diff, cnt);
  }

  boolean valid(int diff, int cnt) {
    diff = MOD.div(diff, cnt);
    int a0 = MOD.sub(
        sumA,
        MOD.mul(
            (n & 1) > 0 ? MOD.mul(n, (n - 1) >> 1) : MOD.mul(n >> 1, n - 1),
            diff));
    a0 = MOD.div(a0, n);
    boolean valid = true;
    for (int i = 0, x = a0; i < n; ++i, x = MOD.add(x, diff)) {
      if (!a.binarySearch(x)) {
        valid = false;
        break;
      }
    }
    if (valid) {
      resX = a0;
      resD = diff;
    }
    return valid;
  }
}
