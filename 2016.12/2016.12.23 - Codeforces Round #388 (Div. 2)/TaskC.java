package main;

import template.collections.queue.IntArrayQueue;
import template.io.QuickScanner;
import template.io.QuickWriter;

public class TaskC {
  IntArrayQueue rq, dq;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    int n = in.nextInt();
    char[] s = new char[n];
    in.next(s);
    rq = new IntArrayQueue(n);
    dq = new IntArrayQueue(n);
    for (int i = 0; i < n; ++i) {
      if (s[i] == 'R') {
        rq.add(i);
      } else {
        dq.add(i);
      }
    }
    while (!rq.isEmpty() && !dq.isEmpty()) {
      if (rq.peek() < dq.peek()) {
        rq.add(rq.poll() + n);
        dq.poll();
      } else {
        dq.add(dq.poll() + n);
        rq.poll();
      }
    }
    out.println(rq.isEmpty() ? 'D' : 'R');
  }
}
