package main;

import template.collections.dancinglink.DancingLink;
import template.io.QuickScanner;
import template.io.QuickWriter;

import java.util.Arrays;

public class TaskE {
  static final int MAXNODE = 200000 * 20;

  int n, m;
  Element[] computers, sockets;
  DancingLink cDL, sDL;

  int c, u;
  int[] a, b;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    n = in.nextInt();
    m = in.nextInt();
    computers = new Element[n];
    for (int i = 0; i < n; ++i) computers[i] = new Element(in.nextInt(), i);
    Arrays.sort(computers);
    sockets = new Element[m];
    for (int i = 0; i < m; ++i) sockets[i] = new Element(in.nextInt(), i);
    Arrays.sort(sockets);

    a = new int[m];
    b = new int[n];
    c = u = 0;
    cDL = new DancingLink(n);
    sDL = new DancingLink(m);
    for (int adapter = 0; sDL.first >= 0; ++adapter) {
      for (int cIdx = cDL.first, sIdx = sDL.first; cIdx >= 0 && sIdx >= 0; ) {
        if (computers[cIdx].power < sockets[sIdx].power) {
          cIdx = cDL.next[cIdx];
        } else if (computers[cIdx].power > sockets[sIdx].power) {
          sIdx = sDL.next[sIdx];
        } else {
          a[sockets[sIdx].idx] = adapter;
          b[computers[cIdx].idx] = sockets[sIdx].idx + 1;
          ++c;
          u += adapter;
          cDL.cover(cIdx);
          sDL.cover(sIdx);
          cIdx = cDL.next[cIdx];
          sIdx = sDL.next[sIdx];
        }
      }
      for (int sIdx = sDL.first; sIdx >= 0; sIdx = sDL.next[sIdx]) {
        Element socket = sockets[sIdx];
        if (socket.power == 1) {
          sDL.cover(sIdx);
        } else {
          socket.power = (socket.power + 1) >> 1;
        }
      }
    }
    out.println(c, u);
    out.println(a);
    out.println(b);
  }

  class Element implements Comparable<Element> {
    int power, idx;

    public Element(int power, int idx) {
      this.power = power;
      this.idx = idx;
    }

    @Override
    public int compareTo(Element o) {
      return power - o.power;
    }
  }
}
