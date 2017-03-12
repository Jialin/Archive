package main;

import template.concurrent.Scheduler;
import template.concurrent.Task;
import template.concurrent.TaskFactory;
import template.io.QuickScanner;
import template.io.QuickWriter;

public class TaskC {
  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    new Scheduler(in, out, createTaskFactory(), 4 /* threadNumber */);
  }

  TaskFactory createTaskFactory() {
    return () -> new Task() {
      int n;
      int[] c;

      @Override
      public void input(QuickScanner in, int taskIdx) {
        n = in.nextInt();
        c = in.nextInt(n + 1);
      }

      double res;

      @Override
      public void process(int taskIdx) {
        res = 2;
        double lower = 0, upper = 2;
        for (int loop = 0; loop < 1000; ++loop) {
          double medium = (lower + upper) / 2;
          double leftSum = 0, rightSum = calc(c[0], medium, n);
          for (int i = 1; i <= n; ++i) {
            leftSum += calc(c[i], medium, n - i);
          }
          if (leftSum <= rightSum) {
            res = medium;
            upper = medium;
          } else {
            lower = medium;
          }
        }
      }

      @Override
      public void output(QuickWriter out, int taskIdx) {
        out.printf("Case #%d: %.12f\n", taskIdx, res - 1);
      }

      double calc(double c, double r, int pow) {
        double res = c;
        for (int i = 0; i < pow; ++i) {
          res *= r;
        }
        return res;
      }
    };
  }
}
