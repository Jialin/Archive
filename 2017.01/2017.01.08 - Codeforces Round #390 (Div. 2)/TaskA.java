package main;

import template.collections.list.IntArrayList;
import template.io.QuickScanner;
import template.io.QuickWriter;

public class TaskA {
  int n;
  int[] a;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    n = in.nextInt();
    a = in.nextInt(n);
    IntArrayList res = calc();
    if (res.isEmpty()) {
      out.println("NO");
    } else {
      out.println("YES");
      out.println(res.size - 1);
      for (int i = 1; i < res.size; ++i) {
        out.println(res.get(i - 1) + 1, res.get(i));
      }
    }
  }

  IntArrayList calc() {
    IntArrayList res = new IntArrayList(2);
    int sum = 0;
    for (int i = 0; i < n; ++i) {
      sum += a[i];
    }
    if (sum != 0) {
      res.add(0);
      res.add(n);
      return res;
    }
    int leftSum = 0;
    for (int i = 0; i < n; ++i) {
      leftSum += a[i];
      if (leftSum != 0) {
        res.add(0);
        res.add(i + 1);
        res.add(n);
        return res;
      }
    }
    return res;
  }
}
