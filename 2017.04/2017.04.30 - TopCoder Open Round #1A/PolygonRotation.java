package main;

import template.geometry.Point;
import template.geometry.PointUtils;

public class PolygonRotation {
  private static double PI = Math.PI;

  int n, m;
  Point[] lp, rp;

  public double getVolume(int[] x, int[] y) {
    lp = new Point[x.length << 1];
    rp = new Point[x.length << 1];
    // lp
    n = 1;
    lp[0] = new Point(x[0], y[0]);
    for (int i = 1; i < x.length; ++i) {
      lp[n++] = new Point(x[i], y[i]);
      if (x[i] == 0) break;
    }
    if (n < 3) n = 0;
    // rp
    m = 1;
    rp[0] = new Point(x[0], y[0]);
    for (int i = x.length - 1; i > 0; --i) {
      rp[m++] = new Point(-x[i], y[i]);
      if (x[i] == 0) break;
    }
    if (m < 3) m = 0;
    // add V
    double res = calcV(n, lp) + calcV(m, rp);
    // cut lp
    if (m > 0) {
      Point side = new Point(0, (rp[0].y + rp[m - 1].y) / 2);
      PointUtils utils = new PointUtils();
      for (int i = 1; i < m; ++i) {
        n = utils.polygonCut(n, lp, rp[i - 1], rp[i], side);
      }
    } else {
      n = 0;
    }
    // sub V
    res -= calcV(n, lp);
    return PI * res / 3;
  }

  double calcV(int n, Point[] p) {
    if (n < 3) return 0;
    double res = calcV(p[0], p[n - 1]);
    for (int i = 1; i < n; ++i) {
      res += calcV(p[i - 1], p[i]);
    }
    return res;
  }

  double calcV(Point a, Point b) {
    double minX = Math.min(a.x, b.x);
    double maxX = Math.max(a.x, b.x);
    return Math.abs(a.y - b.y) * (minX * minX + maxX * maxX + minX * maxX);
  }
}
