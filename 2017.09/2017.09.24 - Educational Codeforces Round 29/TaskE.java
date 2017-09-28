package main;

import template.collections.deque.IntArrayDeque;
import template.collections.priorityqueue.IntPriorityQueue;
import template.io.QuickScanner;
import template.io.QuickWriter;

public class TaskE {
  int n;
  int[] lower, upper;
  IntPriorityQueue lowerPQ, upperPQ;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    n = in.nextInt();
    lower = new int[n];
    upper = new int[n];
    lowerPQ = new IntPriorityQueue(n, (x, y) -> lower[x] - lower[y]);
    upperPQ = new IntPriorityQueue(n, (x, y) -> upper[x] - upper[y]);
    for (int i = 0; i < n; ++i) {
      lower[i] = in.nextInt();
      upper[i] = in.nextInt() + 1;
      lowerPQ.add(i);
      upperPQ.add(i);
    }
    out.println(calc());
  }

  boolean[] isIn;
  IntArrayDeque inQ;
  boolean[] valid;

  int calc() {
    isIn = new boolean[n];
    valid = new boolean[n];
    inQ = new IntArrayDeque(n);
    while (lowerPQ.isNotEmpty() || upperPQ.isNotEmpty()) {
      int ts = lowerPQ.isEmpty()
          ? upper[upperPQ.peek()]
          : Math.min(lower[lowerPQ.peek()], upper[upperPQ.peek()]);
      while (lowerPQ.isNotEmpty() && lower[lowerPQ.peek()] == ts) {
        int idx = lowerPQ.poll();
        isIn[idx] = true;
        inQ.addLast(idx);
      }
      while (upperPQ.isNotEmpty() && upper[upperPQ.peek()] == ts) {
        int idx = upperPQ.poll();
        isIn[idx] = false;
      }
      for ( ; inQ.isNotEmpty() && !isIn[inQ.peekFirst()]; inQ.pollFirst()) {}
      for ( ; inQ.isNotEmpty() && !isIn[inQ.peekLast()]; inQ.pollLast()) {}
      if (inQ.size() == 1) valid[inQ.peekLast()] = true;
    }
    for (int i = 0; i < n; ++i) if (!valid[i]) {
      return i + 1;
    }
    return -1;
  }
}
