package main;

import template.concurrent.Scheduler;
import template.concurrent.Task;
import template.concurrent.TaskFactory;
import template.io.QuickScanner;
import template.io.QuickWriter;

public class TaskA {
  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    new Scheduler(in, out, createTaskFactory(), 4 /* threadNumber */);
  }

  TaskFactory createTaskFactory() {
    return () -> new Task() {
      int l, r;

      @Override
      public void input(QuickScanner in, int taskIdx) {
        l = in.nextInt();
        r = in.nextInt();
      }

      long res;

      @Override
      public void process(int taskIdx) {
        long lr = Math.min(l, r);
        res = lr * (lr + 1) / 2;
      }

      @Override
      public void output(QuickWriter out, int taskIdx) {
        out.printf("Case #%d: %d\n", taskIdx, res);
      }
    };
  }
}
