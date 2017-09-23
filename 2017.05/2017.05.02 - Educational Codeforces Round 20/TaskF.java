package main;

import template.io.QuickScanner;
import template.io.QuickWriter;
import template.numbertheory.number.IntModular;

public class TaskF {
  static int MAXN = 100000 + 1;

  int[] minP;
  boolean[] isSF;
  boolean[] sfEven;

  int n;
  int[] aCnt, cnt;

  IntModular mod;
  int[] pow2;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    initSF();
    aCnt = new int[MAXN];
    n = in.nextInt();
    for (int i = 0; i < n; ++i) {
      ++aCnt[in.nextInt()];
    }
    cnt = new int[MAXN];
    for (int i = 2; i < MAXN; ++i) {
      for (int j = i; j < MAXN; j += i) {
        cnt[i] += aCnt[j];
      }
    }
    mod = new IntModular();
    pow2 = new int[MAXN];
    pow2[0] = 1;
    for (int i = 1; i < MAXN; ++i) pow2[i] = mod.add(pow2[i - 1], pow2[i - 1]);
    out.println(calc());
  }

  void initSF() {
    minP = new int[MAXN];
    for (int i = 2; i < MAXN; ++i) {
      for (int j = i; j < MAXN; j += i) {
        if (minP[j] == 0) minP[j] = i;
      }
    }
    isSF = new boolean[MAXN];
    sfEven = new boolean[MAXN];
    isSF[1] = true;
    sfEven[1] = true;
    for (int i = 2; i < MAXN; ++i) {
      int j = i / minP[i];
      isSF[i] = isSF[j] && j % minP[i] > 0;
      sfEven[i] = !sfEven[j];
    }
  }

  int calc() {
    int res = mod.sub(pow2[n], 1);
    for (int i = 2; i < MAXN; ++i) if (isSF[i] && cnt[i] > 0) {
      int delta = mod.sub(pow2[cnt[i]], 1);
      if (sfEven[i]) {
        res = mod.add(res, delta);
      } else {
        res = mod.sub(res, delta);
      }
    }
    return res;
  }
}
