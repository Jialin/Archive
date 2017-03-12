package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

import java.util.Arrays;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class TaskD {
  int n, n3;

  Cube[] cubes;
  int idx;
  int[] a, b, c, rIdx;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    n = in.nextInt();
    n3 = n * 3;
    idx = 0;
    cubes = new Cube[n3];
    int res = 0, resIdx1 = -1, resIdx2 = -1;
    for (int i = 0; i < n; ++i) {
      int a = in.nextInt();
      int b = in.nextInt();
      int c = in.nextInt();
      cubes[idx++] = new Cube(a, b, c, i);
      cubes[idx++] = new Cube(a, c, b, i);
      cubes[idx++] = new Cube(b, c, a, i);
      int curRes = min(min(a, b), c);
      if (res < curRes) {
        res = curRes;
        resIdx1 = i + 1;
      }
    }
    Arrays.sort(cubes);
    for (int i = 1; i < n3; ++i) if (cubes[i - 1].idx != cubes[i].idx && cubes[i - 1].a == cubes[i].a && cubes[i - 1].b == cubes[i].b) {
      int curRes = min(min(cubes[i].a, cubes[i].b), cubes[i - 1].c + cubes[i].c);
      if (res < curRes) {
        res = curRes;
        resIdx1 = cubes[i - 1].idx + 1;
        resIdx2 = cubes[i].idx + 1;
      }
    }
    if (resIdx2 < 0) {
      out.printf("1\n%d\n", resIdx1);
    } else {
      out.printf("2\n%d %d\n", resIdx1, resIdx2);
    }
  }

  class Cube implements Comparable<Cube> {
    int a;
    int b;
    int c;
    int idx;

    public Cube(int a, int b, int c, int idx) {
      this.a = min(a, b);
      this.b = max(a, b);
      this.c = c;
      this.idx = idx;
    }

    @Override
    public int compareTo(Cube o) {
      if (a != o.a) return a - o.a;
      if (b != o.b) return b - o.b;
      return c - o.c;
    }
  }
}
