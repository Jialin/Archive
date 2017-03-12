package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

import java.util.NavigableSet;
import java.util.SortedSet;
import java.util.TreeSet;

public class big {
  QuickScanner in;
  int n;
  int[] x, h;
  Pole[] poles;

  public void solve(int taskIdx, QuickScanner in, QuickWriter out) {
    System.err.printf("Handling #%d\n", taskIdx);
    this.in = in;
    n = in.nextInt();
    x = new int[n];
    h = new int[n];
    init(x);
    init(h);
    poles = new Pole[n];
    for (int i = 0; i < n; ++i) {
      poles[i] = new Pole(x[i], h[i]);
    }
    out.printf("Case #%d: %f\n", taskIdx, calc());
  }

  NavigableSet<Pole> set;

  double calc() {
    double res = 0, curS = 0;
    set = new TreeSet<>();
    for (Pole pole : poles) {
      if (beCovered(pole)) {
        res += curS;
        continue;
      }
      {
        // front
        SortedSet<Pole> frontPoles = set.headSet(pole);
        Pole frontPole;
        while (true) {
          frontPole = frontPoles.isEmpty() ? null : frontPoles.last();
          if (frontPole == null || !pole.cover(frontPole)) break;
          curS -= frontPole.totalS();
          frontPoles.remove(frontPole);
        }
        if (frontPole != null) {
          curS -= frontPole.rightS;
          Pole.merge(frontPole, pole);
          curS += frontPole.rightS;
        } else {
          pole.leftS = pole.h / 2. * pole.h;
        }
      }
      {
        // tail
        SortedSet<Pole> tailPoles = set.tailSet(pole);
        Pole tailPole;
        while (true) {
          tailPole = tailPoles.isEmpty() ? null : tailPoles.first();
          if (tailPole == null || !pole.cover(tailPole)) break;
          curS -= tailPole.totalS();
          tailPoles.remove(tailPole);
        }
        if (tailPole != null) {
          curS -= tailPole.leftS;
          Pole.merge(pole, tailPole);
          curS += tailPole.leftS;
        } else {
          pole.rightS = pole.h / 2. * pole.h;
        }
      }
      set.add(pole);
      curS += pole.totalS();
      res += curS;
    }
    return res;
  }

  boolean beCovered(Pole pole) {
    Pole front = set.floor(pole);
    Pole tail = set.ceiling(pole);
    return (front != null && front.cover(pole))
        || (tail != null && tail.cover(pole));
  }

  void init(int[] x) {
    x[0] = in.nextInt();
    long a = in.nextInt();
    long b = in.nextInt();
    long c = in.nextInt();
    for (int i = 1; i < n; ++i) {
      x[i] = (int) ((a * x[i - 1] + b) % c + 1);
    }
  }

  static class Pole implements Comparable<Pole> {
    final int x, h;
    double leftS, rightS;

    public Pole(int x, int h) {
      this.x = x;
      this.h = h;
    }

    double totalS() {
      return leftS + rightS;
    }

    boolean cover(Pole o) {
      return x - h <= o.x - o.h && o.x + o.h <= x + h;
    }

    @Override
    public int compareTo(Pole o) {
      return x - o.x;
    }

    @Override
    public String toString() {
      return String.format("@%d:%d", x, h);
    }

    static void merge(Pole l, Pole r) {
      if (l.x >= r.x) throw new IllegalArgumentException();
      if (l.x + l.h < r.x - r.h) {
        l.rightS = l.h / 2. * l.h;
        r.leftS = r.h / 2. * r.h;
      } else {
        double mergeH = (l.h + r.h - (r.x - l.x)) / 2.;
        double mergeH2 = mergeH * mergeH / 2;
        l.rightS = l.h / 2. * l.h - mergeH2;
        r.leftS = r.h / 2. * r.h - mergeH2;
      }
    }
  }
}
