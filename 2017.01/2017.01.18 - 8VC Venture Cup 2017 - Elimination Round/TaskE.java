package main;

import template.io.QuickScanner;
import template.io.QuickWriter;

public class TaskE {
  int n, m;
  QuickWriter out;

  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    this.out = out;
    n = in.nextInt();
    m = in.nextInt();
    calc();
//    test(2);
//    test(3);
//    test(4);
//    test(5);
//    test(6);
//    test(7);
  }

  void calc() {
    if (n < 4) {
      noPlan();
    } else if (n == 4) {
      if (m == 3) {
        plan1();
      } else {
        noPlan();
      }
    } else {
      if (m == 2) {
        plan1();
      } else if (m == 3) {
        plan2();
      } else {
        noPlan();
      }
    }
  }

  void plan1() {
    out.println(n - 1);
    for (int i = 1; i < n; ++i) {
      out.print(i);
      out.print(' ');
      out.println(i + 1);
    }
  }

  void plan2() {
    out.println(n - 1);
    for (int i = 3; i <= n; ++i) {
      out.print(1);
      out.print(' ');
      out.println(i);
    }
    out.println("2 3");
  }

  void noPlan() {
    out.println(-1);
  }

//  int INF = 1000000000;
//
//  IntArrayList a, b;
//  int[][] dist;
//
//  void test(int n) {
//    this.n = n;
//    m = n * (n - 1) / 2;
//    dist = new int[n][n];
//    a = new IntArrayList(m);
//    b = new IntArrayList(m);
//    for (int i = 0; i < n; ++i) for (int j = i + 1; j < n; ++j) {
//      a.add(i);
//      b.add(j);
//    }
//    Set<Integer> dSet = new TreeSet<>();
//    for (int mask = 0; mask < 1 << m; ++mask) {
//      int d = Math.min(
//          calcSlow(((1 << m) - 1) ^ mask),
//          calcSlow(mask));
//      dSet.add(d);
////if (d == 3) {
////  for (int i = 0; i < m; ++i) if ((mask & (1 << i)) > 0) {
////    System.out.printf("[%d->%d] ", a.get(i), b.get(i));
////  }
////  System.out.println();
////  break;
////}
//    }
//    System.out.printf("n:%d dSet:%s\n", n, dSet);
//  }
//
//  int calcSlow(int mask) {
//    for (int i = 0; i < n; ++i) {
//      Arrays.fill(dist[i], INF);
//      dist[i][i] = 0;
//    }
//    for (int i = 0; i < m; ++i) if ((mask & (1 << i)) > 0) {
//      dist[a.get(i)][b.get(i)] = 1;
//      dist[b.get(i)][a.get(i)] = 1;
//    }
//    for (int k = 0; k < n; ++k) for (int i = 0; i < n; ++i) for (int j = 0; j < n; ++j) {
//      dist[i][j] = Math.min(dist[i][j], dist[i][k] + dist[k][j]);
//    }
//    int res = 0;
//    for (int i = 0; i < n; ++i) {
//      res = Math.max(res, IntArrayUtils.max(dist[i]));
//    }
//    return res == INF ? -1 : res;
//  }
}
