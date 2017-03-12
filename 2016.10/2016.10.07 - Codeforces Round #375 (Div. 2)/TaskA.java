package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

import java.util.Arrays;

public class TaskA {
  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    int[] x = in.nextInt(3);
    Arrays.sort(x);
    out.println(x[2] - x[0]);
  }
}
