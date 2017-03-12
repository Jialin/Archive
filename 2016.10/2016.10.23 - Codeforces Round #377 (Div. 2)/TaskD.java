package main;

import template.collections.deque.IntArrayDeque;
import template.io.QuickScanner;
import template.io.QuickWriter;

import java.util.Arrays;

public class TaskD {
  int n, m;
  int[] d, a;

  IntArrayDeque q;
  boolean[] found;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    n = in.nextInt();
    m = in.nextInt();
    d = in.nextInt(n);
    a = in.nextInt(m);
    q = new IntArrayDeque(m);
    found = new boolean[m];
    int res = -1, lower = 1, upper = n;
    while (lower <= upper) {
      int medium = (lower + upper) >> 1;
      if (isPossible(medium)) {
        res = medium;
        upper = medium - 1;
      } else {
        lower = medium + 1;
      }
    }
    out.println(res);
  }

  boolean isPossible(int n) {
    Arrays.fill(found, false);
    q.clear();
    int cnt = 0;
    for (int day = n - 1; day >= 0; --day) {
      int idx = d[day] - 1;
      if (idx >= 0 && !found[idx]) {
        found[idx] = true;
        q.addLast(a[idx]);
        ++cnt;
        continue;
      }
      if (!q.isEmpty()) {
        int rem = q.pollFirst();
        if (rem > 1) {
          q.addFirst(rem - 1);
        }
      }
    }
    return cnt == m && q.isEmpty();
  }
}
