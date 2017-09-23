package main;

import template.collections.disjointset.DisjointSetSupportPoll;
import template.collections.list.IntArrayList;
import template.collections.list.LongArrayList;
import template.io.QuickScanner;
import template.io.QuickWriter;

import java.util.Arrays;

public class TaskF {
  int n, q;
  int[] x, y;
  long[] hash;
  LongArrayList disHashs;
  IntArrayList open, close;
  boolean[] answer;
  DisjointSetSupportPoll dset;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    n = in.nextInt();
    q = in.nextInt();
    x = new int[q];
    y = new int[q];
    hash = new long[q];
    for (int i = 0; i < q; ++i) {
      int l = in.nextInt() - 1;
      int r = in.nextInt() - 1;
      x[i] = Math.min(l, r);
      y[i] = Math.max(l, r);
      hash[i] = (long) x[i] * n + y[i];
    }
    initEvents();
    answer = new boolean[q];
    dset = new DisjointSetSupportPoll(n);
    calc(open.size, 0, q - 1);
    for (int i = 0; i < q; ++i) {
      out.println(answer[i] ? "YES" : "NO");
    }
  }

  void initEvents() {
    disHashs = new LongArrayList(q);
    disHashs.addAll(hash);
    disHashs.sortAndUnique();
    open = new IntArrayList(q);
    close = new IntArrayList(q);
    int m = disHashs.size;
    int[] pos = new int[m];
    Arrays.fill(pos, -1);
    for (int i = 0; i < q; ++i) {
      int hashIdx = disHashs.lowerBound(hash[i]);
      if (pos[hashIdx] >= 0) {
        open.add(pos[hashIdx]);
        close.add(i - 1);
        pos[hashIdx] = -1;
      } else {
        pos[hashIdx] = i;
      }
    }
    for (int i = 0; i < m; ++i) if (pos[i] >= 0) {
      open.add(pos[i]);
      close.add(q - 1);
    }
  }

  void calc(int size, int lower, int upper) {
    int pnt = 0;
    int cnt = 0;
    boolean valid = true;
    for (int i = 0; i < size; ++i) {
      int left = open.get(i);
      int right = close.get(i);
      if (right < lower || upper < left) continue;
      if (left <= lower && upper <= right) {
        if (dset.isFriend(x[left], y[left])) {
//System.out.println(dset.toDisplay());
//System.out.printf("*** dist(%d):%d dist(%d):%d\n", x[left], dset.calcDistance(x[left]), y[left], dset.calcDistance(y[left]));
          if ((Math.abs(dset.calcDistance(x[left]) - dset.calcDistance(y[left])) & 1) == 0) {
            valid = false;
          }
        } else {
//System.out.printf("calc(%d, %d): + %d -- %d\n", lower, upper, x[left], y[left]);
          dset.setFriend(x[left], y[left]);
//System.out.println(dset.toDisplay());
          ++cnt;
        }
        continue;
      }
      open.swap(pnt, i);
      close.swap(pnt, i);
      ++pnt;
    }
    if (size == 0 || lower == upper || !valid) {
      for (int i = lower; i <= upper; ++i) {
//System.out.printf("\tcalc(%d):%b\n", i, valid);
        answer[i] = valid;
      }
    } else {
      int medium = (lower + upper) >> 1;
      calc(pnt, lower, medium);
      calc(pnt, medium + 1, upper);
    }
//System.out.printf("\ncalc(%d, %d): - %d\n", lower, upper, cnt);
    for (int i = 0; i < cnt; ++i) {
      dset.poll();
    }
//System.out.printf("====\n%s\n====\n\n", dset.toDisplay());
  }
}
