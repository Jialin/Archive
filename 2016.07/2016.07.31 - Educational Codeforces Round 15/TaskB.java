package main;

import template.collections.list.IntArrayList;
import template.io.QuickScanner;
import template.io.QuickWriter;

import java.util.Arrays;

public class TaskB {
  static final int MAX = 1000000000;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    int n = in.nextInt();
    int[] a = new int[n];
    for (int i = 0; i < n; ++i) {
      a[i] = in.nextInt();
    }
    Arrays.sort(a);
    IntArrayList value = new IntArrayList(n);
    IntArrayList valueCnt = new IntArrayList(n);
    for (int i = 0; i < n; ++i) {
      if (i == 0 || value.get(value.size() - 1) != a[i]) {
        value.add(a[i]);
        valueCnt.add(1);
      } else {
        int lastIdx = valueCnt.size() - 1;
        valueCnt.set(lastIdx, valueCnt.get(lastIdx) + 1);
      }
    }
    long res = 0;
    for (int i = 0; i < value.size(); ++i) {
      int v = value.get(i);
      if (Integer.bitCount(v) == 1) {
        int vc = valueCnt.get(i);
        res += vc * (vc - 1L) / 2;
      }
    }
    for (int sum = 4; ; sum <<= 1) {
      int j = value.size() - 1;
      for (int i = 0; i < j; ++i) {
        for ( ; i < j && value.get(i) + value.get(j) > sum; --j) ;
        if (i < j && value.get(i) + value.get(j) == sum) {
          res += (long) valueCnt.get(i) * valueCnt.get(j);
        }
      }
      if (sum == 1 << 30) break;
    }
    out.println(res);
  }
}
