package main;

import template.collections.deque.IntArrayDeque;
import template.io.QuickScanner;
import template.io.QuickWriter;

import java.util.Arrays;

public class TaskD {

  static final int MAX = 100000 + 10;
  static final int BLOCK = 317;

  int n;
  int[] S;
  int m;
  Query[] queries;
  int[] answer;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    n = in.nextInt();
    S = new int[n];
    for (int i = 0; i < n; ++i) {
      S[i] = in.nextInt();
    }
    m = in.nextInt();
    queries = new Query[m];
    answer = new int[m];
    for (int i = 0; i < m; ++i) {
      int l = in.nextInt() - 1;
      int r = in.nextInt();
      queries[i] = new Query(l, r, i);
    }
    Arrays.sort(queries);
    FreqQueue q = new FreqQueue();
    HuffmanCode hc = new HuffmanCode();
    int l = 0, r = 0;
    for (int i = 0; i < m; ++i) {
      Query query = queries[i];
      for ( ; query.r < r; --r) q.decFreq(S[r - 1]);
      for ( ; r < query.r; ++r) q.incFreq(S[r]);
      for ( ; l < query.l; ++l) q.decFreq(S[l]);
      for ( ; query.l < l; --l) q.incFreq(S[l - 1]);
      hc.init(q);
      answer[query.idx] = hc.calc();
    }
    out.print(answer, "\n");
  }

  class Query implements Comparable<Query> {
    int l, r, idx;

    public Query(int l, int r, int idx) {
      this.l = l;
      this.r = r;
      this.idx = idx;
    }

    @Override
    public int compareTo(Query o) {
      return l / BLOCK != o.l / BLOCK ? l - o.l : r - o.r;
    }
  }

  class HuffmanCode {

    IntArrayDeque freq1, freq2;
    IntArrayDeque freqCnt1, freqCnt2;

    public HuffmanCode() {
      freq1 = new IntArrayDeque(BLOCK << 1);
      freqCnt1 = new IntArrayDeque(BLOCK << 1);
      freq2 = new IntArrayDeque(BLOCK << 1);
      freqCnt2 = new IntArrayDeque(BLOCK << 1);
    }

    void init(FreqQueue q) {
      freq1.clear(); freqCnt1.clear();
      freq2.clear(); freqCnt2.clear();
      for (int i = q.next[0]; i < MAX - 1; i = q.next[i]) {
        freq1.add(i);
        freqCnt1.add(q.freqCnt[i]);
      }
    }

    int calc() {
      int res = 0;
      while (true) {
        if (!freq1.isEmpty() && !freq2.isEmpty() && freq1.peek() == freq2.peek()) {
          freq1.poll();
          int fc = freqCnt1.poll() + freqCnt2.poll();
          freqCnt2.addFirst(fc);
        }
        int f1;
        if (freq1.isEmpty() || (!freq2.isEmpty() && freq2.peek() < freq1.peek())) {
          if (freqCnt2.peek() > 1) {
            res += simplePoll(freq2, freqCnt2);
            continue;
          } else {
            f1 = freq2.poll();
            freqCnt2.poll();
          }
        } else {
          if (freqCnt1.peek() > 1) {
            res += simplePoll(freq1, freqCnt1);
            continue;
          } else {
            f1 = freq1.poll();
            freqCnt1.poll();
          }
        }
        if (freq1.isEmpty() && freq2.isEmpty()) break;
        int f2;
        if (freq1.isEmpty() || (!freq2.isEmpty() && freq2.peek() < freq1.peek())) {
          f2 = freq2.peek();
          poll(freq2, freqCnt2);
        } else {
          f2 = freq1.peek();
          poll(freq1, freqCnt1);
        }
        res += f1 + f2;
        freq2.add(f1 + f2);
        freqCnt2.add(1);
      }
      return res;
    }

    int simplePoll(IntArrayDeque freq, IntArrayDeque freqCnt) {
      int f = freq.poll();
      int fc = freqCnt.poll();
      freq2.add(f << 1);
      freqCnt2.add(fc >> 1);
      if ((fc & 1) == 1) {
        freq.addFirst(f);
        freqCnt.addFirst(1);
      }
      return f * (fc >> 1 << 1);
    }

    void poll(IntArrayDeque freq, IntArrayDeque freqCnt) {
      int cnt = freqCnt.poll();
      if (cnt > 1) {
        freqCnt.addFirst(cnt - 1);
      } else {
        freq.poll();
      }
    }
  }

  class FreqQueue {

    int[] next, prev;
    int[] freq, freqCnt;

    public FreqQueue() {
      next = new int[MAX];
      prev = new int[MAX];
      freq = new int[MAX];
      freqCnt = new int[MAX];
      next[0] = MAX - 1;
      prev[0] = 0;
      next[MAX - 1] = MAX - 1;
      prev[MAX - 1] = 0;
      freqCnt[0] = MAX;
    }

    public void incFreq(int idx) {
      int freq = this.freq[idx];
      ++this.freq[idx];
      --freqCnt[freq];
      ++freqCnt[freq + 1];
      if (freqCnt[freq + 1] == 1) {
        addPoint(freq + 1, freq, next[freq]);
      }
      if (freqCnt[freq] == 0) {
        removePoint(freq);
      }
    }

    public void decFreq(int idx) {
      int freq = this.freq[idx];
      --this.freq[idx];
      --freqCnt[freq];
      ++freqCnt[freq - 1];
      if (freqCnt[freq - 1] == 1) {
        addPoint(freq - 1, prev[freq], freq);
      }
      if (freqCnt[freq] == 0) {
        removePoint(freq);
      }
    }

    private void addPoint(int currP, int prevP, int nextP) {
      next[currP] = next[prevP];
      next[prevP] = currP;
      prev[nextP] = currP;
      prev[currP] = prevP;
    }

    private void removePoint(int currP) {
      next[prev[currP]] = next[currP];
      prev[next[currP]] = prev[currP];
    }
  }
}
