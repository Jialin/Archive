package main;

import template.array.CharArrayUtils;
import template.concurrent.Scheduler;
import template.concurrent.Task;
import template.concurrent.TaskFactory;
import template.io.QuickScanner;
import template.io.QuickWriter;
import template.numbertheory.number.IntUtils;

import java.util.Arrays;

public class TaskA {
  public void solve(int testNumber, QuickScanner in, QuickWriter out) {
    new Scheduler(in, out, createTaskFactory(), 4 /* threadNumber */);
  }

  static int MAXN = 1000;

  TaskFactory createTaskFactory() {
    return () -> new Task() {
      int n;
      char[] s;
      char[] orderS;

      @Override
      public void input(QuickScanner in, int taskIdx) {
        s = new char[MAXN];
        n = in.next(s);
      }

      int res;

      @Override
      public void process(int taskIdx) {
        orderS = new char[3];
        res = 1;
        for (int i = 0; i < n; ++i) {
          int startIdx = i > 0 ? i - 1 : i;
          int endIdx = i + 1 < n ? i + 1 : i;
          int length = endIdx - startIdx + 1;
          System.arraycopy(s, startIdx, orderS, 0, length);
          Arrays.sort(orderS, 0, length);
          res = IntUtils.mul(res, CharArrayUtils.unique(orderS, 0, length));
        }
      }

      @Override
      public void output(QuickWriter out, int taskIdx) {
        out.printf("Case #%d: %d\n", taskIdx, res);
      }
    };
  }
}
