package main;

import template.collections.list.IntArrayList;
import template.io.QuickScanner;
import template.io.QuickWriter;

public class TaskD {
  static int MAXMASK = 1 << 14;

  int n, m;
  int[] cnt;
  IntArrayList masks;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
System.out.println(System.getProperty("java.home"));
    n = in.nextInt();
    m = in.nextInt();
    cnt = new int[MAXMASK];
    masks = new IntArrayList(MAXMASK);
    for (int mask = MAXMASK - 1; mask >= 0; --mask) {
      if (Integer.bitCount(mask) == m) masks.add(mask);
    }
    for (int i = 0; i < n; ++i) ++cnt[in.nextInt()];
    out.println(calc());
  }

  long calc() {
    long res = 0;
    for (int i = 0; i < MAXMASK; ++i) if (cnt[i] > 0) {
      long sumCnt = 0;
      for (int j = masks.size - 1; j >= 0; --j) {
        sumCnt += cnt[i ^ masks.get(j)];
      }
//      for (int mask : masks) sumCnt += cnt[i ^ mask];
      res += cnt[i] * sumCnt;
    }
    if (m == 0) res -= n;
    return res >> 1;
  }
}
