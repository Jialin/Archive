package main;

import template.array.IntArrayUtils;
import template.io.QuickScanner;
import template.io.QuickWriter;

import java.util.Arrays;

public class TaskB {
  int n;
  int[] n12;
  int[] a;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    n = in.nextInt();
    n12 = in.nextInt(2);
    Arrays.sort(n12);
    a = in.nextInt(n);
    Arrays.sort(a);
    IntArrayUtils.reverse(a);
    double sumA = 0, sumB = 0;
    for (int i = 0; i < n12[0]; ++i) {
      sumA += a[i];
    }
    for (int i = n12[0]; i < n12[0] + n12[1]; ++i) {
      sumB += a[i];
    }
    out.printf("%.8f\n", sumA / n12[0] + sumB / n12[1]);
  }
}
