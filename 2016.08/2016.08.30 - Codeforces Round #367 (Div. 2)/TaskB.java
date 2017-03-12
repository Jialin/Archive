package main;

import template.array.IntArrayUtils;
import template.io.QuickScanner;
import template.io.QuickWriter;

import java.util.Arrays;

public class TaskB {
  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    int n = in.nextInt();
    int[] x = in.nextInt(n);
    Arrays.sort(x);
    for (int i = in.nextInt(); i > 0; --i) {
      int y = in.nextInt();
      out.println(IntArrayUtils.upperBound(x, y));
    }
  }
}
