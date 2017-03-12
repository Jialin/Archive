package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

import java.util.Arrays;

import static java.lang.Math.max;

public class TaskC {
  int n;
  Point[] points;
  double w, v, u;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    n = in.nextInt();
    w = in.nextInt();
    v = in.nextInt();
    u = in.nextInt();
    points = new Point[n];
    for (int i = 0; i < n; ++i) {
      int x = in.nextInt();
      int y = in.nextInt();
      points[i] = new Point(x, y);
    }
    Arrays.sort(points);
    out.printf("%.10f\n", calc());
  }

  double calc() {
    if (leaveBefore()) return w / u;
    return calcAfter();
  }

  boolean leaveBefore() {
    for (int i = 0; i < n; ++i) {
      Point point = points[i];
      if (point.x < 0
          || (long) point.y * v > (long) point.x * u) {
        return false;
      }
    }
    return true;
  }

  double calcAfter() {
    double res = 0;
    double lastY = 0;
    for (int i = 0; i < n; ++i) {
      Point point = points[i];
      res = max(res + (point.y - lastY) / u, point.timeToLine);
      lastY = point.y;
    }
    return res + (w - lastY) / u;
  }

  class Point implements Comparable<Point> {
    int x, y;
    double timeToLine;

    Point(int x, int y) {
      this.x = x;
      this.y = y;
      timeToLine = max(x / v, 0);
    }

    @Override
    public int compareTo(Point o) {
      return y != o.y ? y - o.y : x - o.x;
    }
  }
}
