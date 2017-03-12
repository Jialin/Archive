package main;

import template.array.CharArrayUtils;
import template.collections.deque.CharArrayDeque;
import template.io.QuickScanner;
import template.io.QuickWriter;

public class TaskE {
  int n;
  char[] s;
  long[] answer;
  Storage lq, rq;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    n = in.nextInt();
    s = new char[n];
    in.next(s);
    answer = new long[n];
    lq = new Storage(n, 'U');
    rq = new Storage(n, 'D');
    calc(false);
    for (int i = 0; i < n; ++i) {
      s[i] ^= 'U' ^ 'D';
    }
    for (int i = 0, j = n - 1; i < j; ++i, --j) {
      CharArrayUtils.swap(s, i, j);
    }
    calc(true);
    out.println(answer);
  }

  void calc(boolean reversed) {
    lq.init();
    rq.init();
    int pnt = 0;
    for (int i = 0; i < n; ++i) {
      lq.addFirst(s[i]);
      rq.pollFirst();
      pnt = Math.max(pnt, i + 1);
      for (pnt = Math.max(pnt, i + 1); pnt < n && lq.patternCnt > rq.patternCnt; ++pnt) {
        rq.addLast(s[pnt]);
      }
      if (lq.patternCnt == rq.patternCnt) {
        answer[reversed ? n - 1 - i : i] = lq.cost + rq.cost + i + 1;
      }
    }
  }

  class Storage {

    char pattern;
    CharArrayDeque q;
    int patternCnt;
    long cost;

    Storage(int capacity, char pattern) {
      this.pattern = pattern;
      q = new CharArrayDeque(capacity);
      init();
    }

    void init() {
      q.clear();
      patternCnt = 0;
      cost = 0;
    }

    void addFirst(char c) {
      cost += patternCnt << 1;
      if (c == pattern) {
        ++cost;
        ++patternCnt;
      }
      q.addFirst(c);
    }

    void addLast(char c) {
      if (c == pattern) {
        cost += (q.size() << 1) | 1;
        ++patternCnt;
      }
      q.addLast(c);
    }

    void pollFirst() {
      if (q.isEmpty()) return;
      char c = q.pollFirst();
      if (c == pattern) {
        --cost;
        --patternCnt;
      }
      cost -= patternCnt << 1;
    }
  }
}
