package main;

import template.array.LongArrayUtils;
import template.io.QuickScanner;
import template.io.QuickWriter;
import template.numbertheory.combination.BellModular;
import template.numbertheory.number.IntModular;

public class TaskE {
  IntModular MOD;
  BellModular BELL;

  int m, n;
  long[] mask;
  int[] cnt;
  int setCnt;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    m = in.nextInt();
    n = in.nextInt();
    mask = new long[m];
    cnt = new int[m];
    for (int i = 0; i < n; ++i) for (int j = 0; j < m; ++j) if (in.nextNonSpaceChar() == '1') {
      mask[j] |= 1L << i;
    }
    LongArrayUtils.sort(mask);
    setCnt = LongArrayUtils.unique(mask, cnt);
    MOD = new IntModular();
    BELL = new BellModular(1000, MOD);
    int res = 1;
    for (int i = 0; i < setCnt; ++i) {
      res = MOD.mul(res, BELL.get(cnt[i]));
    }
    out.println(res);
  }
}
