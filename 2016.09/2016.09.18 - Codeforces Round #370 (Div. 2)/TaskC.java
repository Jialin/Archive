package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

import java.util.Arrays;

public class TaskC {
  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    int x = in.nextInt();
    int y = in.nextInt();
    int[] size = new int[3];
    size[0] = size[1] = size[2] = y;
    int res = 0;
    for ( ; size[0] != x; ++res) {
      size[0] = Math.min(x, size[1] + size[2] - 1);
      Arrays.sort(size);
    }
    out.println(res);
  }
}
