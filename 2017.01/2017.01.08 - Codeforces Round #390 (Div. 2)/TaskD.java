package main;

import template.collections.list.IntArrayList;
import template.io.QuickScanner;
import template.io.QuickWriter;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Deque;
import java.util.PriorityQueue;

public class TaskD {
  int n, k;
  Range[] lowerRanges, upperRanges;
  IntArrayList lst;
  TopK topK;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    n = in.nextInt();
    k = in.nextInt();
    lowerRanges = new Range[n];
    upperRanges = new Range[n];
    for (int i = 0; i < n; ++i) {
      int lower = in.nextInt();
      int upper = in.nextInt();
      Range range = new Range(lower, upper, i);
      lowerRanges[i] = upperRanges[i] = range;
    }
    Arrays.sort(lowerRanges, Range.LOWER);
    Arrays.sort(upperRanges, Range.UPPER);
    topK = new TopK();
    int res = calc(false, -1);
    out.println(res);
    lst = new IntArrayList(k);
    if (res == 0) {
      for (int i = 0; i < k; ++i) {
        lst.add(i);
      }
    } else {
      calc(true, res);
    }
    for (int i = 0; i < k; ++i) {
      if (i > 0) out.print(' ');
      out.print(lst.get(i) + 1);
    }
    out.println();
  }

  int calc(boolean getList, int resGap) {
    topK.init();
    int res = 0;
    int lowerPnt = 0, upperPnt = 0;
    for (int i = 0; i < n; ++i) {
      Range range = upperRanges[i];
      topK.bound = range.upper;
      for ( ; lowerPnt < n && lowerRanges[lowerPnt].lower <= range.upper; ++lowerPnt) {
        topK.add(lowerRanges[lowerPnt]);
      }
      for ( ; upperPnt < n && upperRanges[upperPnt].upper < range.upper; ++upperPnt) {
        topK.remove(upperRanges[upperPnt]);
      }
      Range kRange = topK.getK();
      if (kRange == null) continue;
      int curRes = range.upper - Math.max(kRange.lower, range.lower) + 1;
      if (getList) {
        if (resGap == curRes) {
          topK.update(lst, range.idx);
          break;
        }
      } else {
        res = Math.max(res, curRes);
      }
    }
    return res;
  }

  static class Range {
    static Comparator<Range> LOWER = Comparator.comparingInt(range -> range.lower);
    static Comparator<Range> UPPER = Comparator.comparingInt(range -> range.upper);
    static Comparator<Range> LOWER_REVERSE = Comparator.comparingInt(range -> -range.lower);

    final int lower, upper, idx;

    Range(int lower, int upper, int idx) {
      this.lower = lower;
      this.upper = upper;
      this.idx = idx;
    }

    @Override
    public String toString() {
      return String.format("[%d,%d]:%d", lower, upper, idx);
    }
  }

  class TopK {
    final SmartPriorityQueue topK;
    final SmartDeque appendix;

    int bound;

    TopK() {
      topK = new SmartPriorityQueue();
      appendix = new SmartDeque();
    }

    void init() {
      topK.init();
      appendix.init();
    }

    void add(Range range) {
      topK.add(range);
      if (topK.size() > k) {
        appendix.add(topK.last());
        topK.remove(topK.last());
      }
    }

    void remove(Range range) {
      if (topK.contains(range)) {
        topK.remove(range);
        if (!appendix.isEmpty()) {
          topK.add(appendix.first());
          appendix.remove(appendix.first());
        }
      } else {
        appendix.remove(range);
      }
    }

    Range getK() {
      return topK.size() == k ? topK.last() : null;
    }

    void update(IntArrayList lst, int idx) {
      topK.update(lst, idx);
    }
  }

  class SmartDeque {
    boolean[] inQueue;
    boolean[] inQueueActual;
    Deque<Range> queue;

    SmartDeque() {
      inQueue = new boolean[n];
      inQueueActual = new boolean[n];
      queue = new ArrayDeque<>(n);
    }

    void init() {
      Arrays.fill(inQueue, false);
      Arrays.fill(inQueueActual, false);
      queue.clear();
    }

    void add(Range range) {
      if (!inQueue[range.idx]) {
        if (!inQueueActual[range.idx]) {
          queue.addLast(range);
          inQueueActual[range.idx] = true;
        }
        inQueue[range.idx] = true;
      }
    }

    void remove(Range range) {
      inQueue[range.idx] = false;
    }

    boolean isEmpty() {
      adjust();
      return queue.isEmpty();
    }

    Range first() {
      adjust();
      return queue.getFirst();
    }

    void adjust() {
      while (!queue.isEmpty() && !inQueue[queue.getFirst().idx]) {
        inQueueActual[queue.pollFirst().idx] = false;
      }
    }
  }

  class SmartPriorityQueue {
    boolean[] inQueue;
    boolean[] inQueueActual;
    PriorityQueue<Range> queue;
    int size;

    SmartPriorityQueue() {
      inQueue = new boolean[n];
      inQueueActual = new boolean[n];
      queue = new PriorityQueue<>(k + 1, Range.LOWER_REVERSE);
    }

    void init() {
      Arrays.fill(inQueue, false);
      Arrays.fill(inQueueActual, false);
      queue.clear();
      size = 0;
    }

    void add(Range range) {
      if (!inQueue[range.idx]) {
        if (!inQueueActual[range.idx]) {
          queue.add(range);
          inQueueActual[range.idx] = true;
        }
        inQueue[range.idx] = true;
        ++size;
      }
    }

    boolean contains(Range range) {
      return inQueue[range.idx];
    }

    void remove(Range range) {
      if (inQueue[range.idx]) {
        inQueue[range.idx] = false;
        --size;
      }
    }

    int size() {
      return size;
    }

    Range last() {
      adjust();
      return queue.peek();
    }

    void update(IntArrayList lst, int idx) {
      lst.clear();
      boolean found = false;
      for (Range range : queue) if (inQueue[range.idx]) {
        lst.add(range.idx);
        if (range.idx == idx) {
          found = true;
        }
      }
      if (!found) {
        lst.pollLast();
        lst.add(idx);
      }
    }

    void adjust() {
      while (!queue.isEmpty() && !inQueue[queue.peek().idx]) {
        inQueueActual[queue.poll().idx] = false;
      }
    }
  }
}
