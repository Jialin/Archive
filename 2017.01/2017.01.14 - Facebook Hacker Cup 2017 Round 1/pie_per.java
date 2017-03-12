package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

import java.util.Arrays;
import java.util.PriorityQueue;

public class pie_per {
  public void solve(int taskIdx, QuickScanner in, QuickWriter out) {
    System.err.printf("Handling #%d\n", taskIdx);
    int n = in.nextInt();
    int m = in.nextInt();
    int[] c = new int[m];
    PriorityQueue<Integer> queue = new PriorityQueue<>(n * m);
    int res = 0;
    for (int i = 0; i < n; ++i) {
      in.nextInt(m, c);
      Arrays.sort(c);
      for (int j = 0, delta = 1; j < m; ++j, delta += 2) {
        queue.add(c[j] + delta);
      }
      res += queue.poll();
    }
    out.printf("Case #%d: %d\n", taskIdx, res);
  }
}
