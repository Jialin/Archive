package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

public class TaskB {
  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    int n = in.nextInt();
    int m = in.nextInt();
    int[] c = new int[n];
    in.nextInt(n, c);
    boolean[] isCapital = new boolean[n];
    for (int i = 0; i < m; ++i) {
      isCapital[in.nextInt() - 1] = true;
    }
    long sumCapital = 0, sumCapital2 = 0;
    long sumNonCapital = 0;
    long res = 0;
    for (int i = 0, j = 1; i < n; ++i, j = j == n - 1 ? 0 : j + 1) {
      if (isCapital[i]) {
        sumCapital += c[i];
        sumCapital2 += c[i] * c[i];
      } else {
        sumNonCapital += c[i];
        if (!isCapital[j]) res += c[i] * c[j];
      }
    }
    res += sumCapital * sumNonCapital;
    res += (sumCapital * sumCapital - sumCapital2) >> 1;
    out.println(res);
  }
}
