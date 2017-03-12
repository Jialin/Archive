package main;

import template.array.IntArrayUtils;
import template.collections.bitset.BitSet;
import template.collections.list.IntArrayList;
import template.io.QuickScanner;
import template.io.QuickWriter;

public class TaskF {
  int n, k;
  int[] p;
  IntArrayList size;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    n = in.nextInt();
    k = in.nextInt();
    p = in.nextInt(n);
    IntArrayUtils.update(p, -1);
    calcSize();
    out.printf("%d %d\n", calcMin(), calcMax());
  }

  void calcSize() {
    size = new IntArrayList(n);
    boolean[] visited = new boolean[n];
    for (int i = 0; i < n; ++i) if (!visited[i]) {
      int curSize = 0;
      for (int pnt = i; !visited[pnt]; pnt = p[pnt]) {
        visited[pnt] = true;
        ++curSize;
      }
      size.add(curSize);
    }
  }

  static int BOUND = 177;
  static int INF = 1000000000;

  int calcMin() {
    int minK = Math.min(k, n - k);
    // >=BOUND
    BitSet bitSet = new BitSet(minK + 1);
    bitSet.set(0);
    for (int i = 0; i < size.size; ++i) if (size.get(i) >= BOUND) {
      bitSet.orShiftLeft(size.get(i));
    }
    // <BOUND
    int[] cnt = new int[BOUND];
    for (int i = 0; i < size.size; ++i) if (size.get(i) < BOUND) {
      ++cnt[size.get(i)];
    }
    boolean[] found = new boolean[minK + 1];
    found[0] = true;
    for (int step = 2; step < BOUND; ++step) if (cnt[step] > 0) {
      int delta = step * cnt[step];
      for (int start = 0; start < step; ++start) {
        int last = -INF;
        for (int i = start, j = start - delta; i <= minK; i += step, j += step) {
          if (found[i]) {
            last = i;
            continue;
          }
          found[i] = j <= last;
        }
      }
    }
    // Aggregate
    for (int i = 0; i <= minK; ++i) if (found[i] && bitSet.get(minK - i)) {
      return k;
    }
    return k + 1;
  }

  int calcMax() {
    int res = 0, rem = k;
    for (int i = 0; i < size.size && rem > 0; ++i) {
      int s = size.get(i);
      int cost = Math.min(rem, s >> 1);
      rem -= cost;
      res += cost << 1;
    }
    for (int i = 0; i < size.size && rem > 0; ++i) if ((size.get(i) & 1) > 0) {
      --rem;
      ++res;
    }
    return res;
  }
}
