package main;

import template.array.IntArrayUtils;
import template.concurrent.Scheduler;
import template.concurrent.Task;
import template.concurrent.TaskFactory;
import template.io.QuickScanner;
import template.io.QuickWriter;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

import static main.TaskA.MAXN;

public class TaskC {
  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    new Scheduler(in, out, createTaskFactory(), 4 /* threadNumber */);
  }

  TaskFactory createTaskFactory() {
    return () -> new Task() {
      int n, x, k;
      double a, b, c;
      double[][] p;

      @Override
      public void input(QuickScanner in, int taskIdx) {
        n = in.nextInt();
        x = in.nextInt();
        k = in.nextInt();
        a = in.nextInt() / 100.;
        b = in.nextInt() / 100.;
        c = in.nextInt() / 100.;
      }

      double res;

      @Override
      public void process(int taskIdx) {
        calcDisX();
        p = new double[2][disXCnt];
        int t = 0;
        Arrays.fill(p[t], 0, disXCnt, 0);
        p[t][IntArrayUtils.lowerBound(disX, 0, disXCnt, x)] = 1;
        for (int loop = 0; loop < n; ++loop) {
          Arrays.fill(p[t ^ 1], 0, disXCnt, 0);
          for (int i = 0; i < disXCnt; ++i) {
            update(t ^ 1, disX[i] & k, p[t][i] * a);
            update(t ^ 1, disX[i] | k, p[t][i] * b);
            update(t ^ 1, disX[i] ^ k, p[t][i] * c);
          }
          t ^= 1;
        }
        res = 0;
        for (int i = 0; i < disXCnt; ++i) {
          res += disX[i] * p[t][i];
        }
      }

      @Override
      public void output(QuickWriter out, int taskIdx) {
        out.printf("Case #%d: %.10f\n", taskIdx, res);
      }

      void update(int t, int value, double deltaP) {
        p[t][IntArrayUtils.lowerBound(disX, 0, disXCnt, value)] += deltaP;
      }

      Set<Integer> s;
      int[] disX;
      int disXCnt;

      void calcDisX() {
        s = new TreeSet<>();
        disX = new int[MAXN];
        disXCnt = 0;
        addDisX(x);
        for (int i = 0; i < disXCnt; ++i) {
          int value = disX[i];
          addDisX(value & k);
          addDisX(value | k);
          addDisX(value ^ k);
        }
        Arrays.sort(disX, 0, disXCnt);
      }

      void addDisX(int value) {
        if (!s.contains(value)) {
          s.add(value);
          disX[disXCnt++] = value;
        }
      }
    };
  }
}
