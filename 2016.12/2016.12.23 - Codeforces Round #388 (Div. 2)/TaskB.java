package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

public class TaskB {
  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    int[] x = new int[3];
    int[] y = new int[3];
    for (int i = 0; i < 3; ++i) {
      x[i] = in.nextInt();
      y[i] = in.nextInt();
    }
    System.out.println(3);
    for (int i = 0; i < 3; ++i) {
      for (int j = i + 1; j < 3; ++j) {
        for (int k = 0; k < 3; ++k) if (i != k && j != k) {
          System.out.printf("%d %d\n", x[i] + x[j] - x[k], y[i] + y[j] - y[k]);
        }
      }
    }
  }
}
