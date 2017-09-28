package main;

import template.array.IntArrayUtils;
import template.collections.priorityqueue.IntPriorityQueue;
import template.io.QuickScanner;
import template.io.QuickWriter;

public class TaskD {
  int n;
  int[] a;
  int[] cnt;

  IntPriorityQueue missingQ;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    n = in.nextInt();
    a = in.nextInt(n);
    IntArrayUtils.update(a, -1);
    out.println(init());
    update();
    IntArrayUtils.update(a, 1);
    out.println(a);
  }

  int init() {
    cnt = new int[n];
    for (int x : a) ++cnt[x];
    missingQ = new IntPriorityQueue(n);
    for (int i = 0; i < n; ++i) if (cnt[i] == 0) {
      missingQ.add(i);
    }
    return missingQ.size;
  }

  void update() {
    boolean[] in = new boolean[n];
    for (int i = 0; i < n; ++i) {
      int x = a[i];
      if (in[x]) {
        a[i] = missingQ.poll();
      } if (cnt[x] == 1) {
      } else if (x < missingQ.peek()) {
      } else {
        a[i] = missingQ.poll();
      }
      --cnt[x];
      in[a[i]] = true;
    }
  }
}
