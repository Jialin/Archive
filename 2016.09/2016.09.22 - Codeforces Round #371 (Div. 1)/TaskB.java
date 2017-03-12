package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

import java.util.Random;

public class TaskB {
  static boolean LOCAL = false;
  static int TEST_CASE = 10000;

  QuickScanner in;
  QuickWriter out;
  int n;
  Server server;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    this.in = in;
    this.out = out;
    n = LOCAL ? 1 << 16 : in.nextInt();
    server = new Server();
    for (int remCases = LOCAL ? TEST_CASE : 1; remCases > 0; --remCases) {
      server.init(n);
      Rect[] rects = split(0);
      if (rects == null) {
        rects = split(1);
      }
      rects[0] = shrink(rects[0]);
      rects[1] = shrink(rects[1]);
      server.answer(rects[0], rects[1]);
    }
  }

  Rect[] split(int dim) {
    int lower = 1, upper = n - 1, res = 0;
    Rect fullRect = new Rect(1, 1, n, n);
    while (lower <= upper) {
      int medium = (lower + upper) >> 1;
      if (server.ask(fullRect.update(1, dim, medium)) == 0) {
        res = medium;
        lower = medium + 1;
      } else {
        upper = medium - 1;
      }
    }
    Rect[] rects = new Rect[]{
      fullRect.update(1, dim, res + 1),
      fullRect.update(0, dim, res + 2)};
    return server.ask(rects[0]) == 1
        && server.ask(rects[1]) == 1
        ? rects : null;
  }

  Rect shrink(Rect rect) {
    rect = shrink(rect, 0);
    rect = shrink(rect, 1);
    return rect;
  }

  Rect shrink(Rect rect, int dim) {
    int lower, upper, res;
    // lower
    lower = rect.getValue(0, dim) + 1;
    upper = rect.getValue(1, dim);
    res = lower - 1;
    while (lower <= upper) {
      int medium = (lower + upper) >> 1;
      if (server.ask(rect.update(0, dim, medium)) == 1) {
        res = medium;
        lower = medium + 1;
      } else {
        upper = medium - 1;
      }
    }
    rect = rect.update(0, dim, res);
    // upper
    lower = rect.getValue(0, dim);
    upper = rect.getValue(1, dim) - 1;
    res = upper + 1;
    while (lower <= upper) {
      int medium = (lower + upper) >> 1;
      if (server.ask(rect.update(1, dim, medium)) == 1) {
        res = medium;
        upper = medium - 1;
      } else {
        lower = medium + 1;
      }
    }
    return rect.update(1, dim, res);
  }

  class Server {
    Rect rect1, rect2;

    Server() {
      rect1 = new Rect();
      rect2 = new Rect();
    }

    void init(int n) {
      if (LOCAL) {
        do {
          rect1.initRandom(n);
          rect2.initRandom(n);
        } while (!rect1.valid(rect2));
        //rect1 = new Rect(2, 2, 2, 2);
        //rect2 = new Rect(3, 4, 3, 5);
      }
    }

    int ask(Rect rect) {
      out.print("? ");
      rect.print();
      out.println();
      out.flush();
      if (LOCAL) {
        return (rect1.in(rect) ? 1 : 0)
            + (rect2.in(rect) ? 1 : 0);
      } else {
        return in.nextInt();
      }
    }

    void answer(Rect rect1, Rect rect2) {
      out.print("! ");
      rect1.print();
      out.print(' ');
      rect2.print();
      out.println();
      out.flush();
      if (LOCAL) {
        if ((rect1.equals(this.rect1) && rect2.equals(this.rect2))
            || (rect2.equals(this.rect1) && rect1.equals(this.rect2))) {
          System.out.println("AC!");
        } else {
          System.out.println("WA!");
          throw new IllegalArgumentException();
        }
      }
    }
  }

  class Rect {

    final Random random = new Random();

    int x1, y1, x2, y2;

    Rect() {}

    Rect(int x1, int y1, int x2, int y2) {
      this.x1 = x1;
      this.y1 = y1;
      this.x2 = x2;
      this.y2 = y2;
    }

    void initRandom(int n) {
      x1 = random.nextInt(n);
      x2 = random.nextInt(n - x1) + x1 + 1;
      ++x1;
      y1 = random.nextInt(n);
      y2 = random.nextInt(n - y1) + y1 + 1;
      ++y1;
    }

    int getValue(int idx1, int idx2) {
      switch ((idx1 << 1) | idx2) {
        case 0:
          return x1;
        case 1:
          return y1;
        case 2:
          return x2;
        case 3:
          return y2;
      }
      throw new IllegalArgumentException();
    }

    Rect update(int idx1, int idx2, int value) {
      switch ((idx1 << 1) | idx2) {
        case 0:
          return new Rect(value, y1, x2, y2);
        case 1:
          return new Rect(x1, value, x2, y2);
        case 2:
          return new Rect(x1, y1, value, y2);
        case 3:
          return new Rect(x1, y1, x2, value);
      }
      return null;
    }

    boolean valid(Rect o) {
      if (x2 < o.x1) return true;
      if (y2 < o.y1) return true;
      if (o.x2 < x1) return true;
      if (o.y2 < y1) return true;
      return false;
    }

    boolean in(Rect o) {
      return o.x1 <= x1 && x2 <= o.x2
          && o.y1 <= y1 && y2 <= o.y2;
    }

    boolean equals(Rect o) {
      return x1 == o.x1 && y1 == o.y1
          && x2 == o.x2 && y2 == o.y2;
    }

    void print() {
      out.printf("%d %d %d %d", x1, y1, x2, y2);
    }
  }
}
